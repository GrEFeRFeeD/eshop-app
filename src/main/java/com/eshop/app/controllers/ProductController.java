package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.CategoryListDto;
import com.eshop.app.controllers.dtos.CommentForm;
import com.eshop.app.controllers.dtos.ProductForm;
import com.eshop.app.controllers.dtos.ProductListDto;
import com.eshop.app.controllers.dtos.QuestionDto;
import com.eshop.app.controllers.dtos.QuestionForm;
import com.eshop.app.controllers.dtos.ReviewDto;
import com.eshop.app.controllers.dtos.ReviewForm;
import com.eshop.app.controllers.dtos.ReviewListDto;
import com.eshop.app.controllers.dtos.SingleCategoryDto;
import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.ProductException.ProductExceptionProfile;
import com.eshop.app.exceptions.ReportException;
import com.eshop.app.exceptions.ReportException.ReportExceptionProfile;
import com.eshop.app.exceptions.UserException;
import com.eshop.app.exceptions.UserException.UserExceptionProfile;
import com.eshop.app.model.comment.Comment;
import com.eshop.app.model.comment.CommentService;
import com.eshop.app.model.product.Product;
import com.eshop.app.model.product.ProductCategory;
import com.eshop.app.model.product.ProductService;
import com.eshop.app.model.report.Report;
import com.eshop.app.model.report.ReportService;
import com.eshop.app.model.report.ReportType;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserService;
import com.eshop.app.security.JwtUserDetails;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  private final ProductService productService;
  private final ReportService reportService;
  private final UserService userService;
  private final CommentService commentService;

  @Autowired
  public ProductController(ProductService productService, ReportService reportService,
      UserService userService, CommentService commentService) {
    this.productService = productService;
    this.reportService = reportService;
    this.userService = userService;
    this.commentService = commentService;
  }

  @GetMapping("/categories")
  public ResponseEntity<CategoryListDto> getCategoriesList() {

    List<String> categories = new ArrayList<>(List.of(Arrays.toString(ProductCategory.values())));
    Map<String, List<String>> characteristics = Arrays.stream(ProductCategory.values())
        .collect(Collectors.toMap(Enum::toString, ProductCategory::getBasicCharacteristics));

    return ResponseEntity.ok(new CategoryListDto(categories, characteristics));
  }

  @GetMapping("/categories/{category-name}")
  public ResponseEntity<SingleCategoryDto> getCategoryCharacteristics(@PathVariable("category-name") String categoryName)
      throws ProductException {

    ProductCategory category;
    try {
      category = ProductCategory.valueOf(categoryName);
    } catch (IllegalArgumentException e) {
      throw new ProductException(ProductExceptionProfile.WRONG_CATEGORY);
    }

    return ResponseEntity.ok(new SingleCategoryDto(category.toString(), category.getBasicCharacteristics()));
  }

  @GetMapping("/products")
  public ResponseEntity<ProductListDto> getAllProducts() {

    List<Product> products = productService.findAll();
    return ResponseEntity.ok(new ProductListDto(products));
  }

  @GetMapping(value = "/products", params = {"category"})
  public ResponseEntity<ProductListDto> getProductsByCategory(@RequestParam("category") String categoryName)
      throws ProductException {

    ProductCategory category;
    try {
      category = ProductCategory.valueOf(categoryName);
    } catch (IllegalArgumentException e) {
      throw new ProductException(ProductExceptionProfile.WRONG_CATEGORY);
    }

    List<Product> products = productService.findByCategory(category);
    return ResponseEntity.ok(new ProductListDto(products));
  }

  @GetMapping("/products/{product-id}")
  public ResponseEntity<Product> getProductById(@RequestParam("product-id") Long id)
      throws ProductException {

    Product product = productService.findById(id);
    return ResponseEntity.ok(product);
  }

  @GetMapping("/products/{product-id}/reviews")
  public ResponseEntity<ReviewListDto> getReviewsByProduct(@PathVariable("product-id") Long id)
      throws ProductException {

    Product product = productService.findById(id);
    List<Report> reviews = reportService.findByProductAndType(product, ReportType.REVIEW);
    return ResponseEntity.ok(new ReviewListDto(reviews));
  }

  @PostMapping("/products/{product-id}/reviews")
  public ResponseEntity<ReviewDto> addReviewToProduct(@PathVariable("product-id") Long id,
      @RequestBody ReviewForm reviewForm, Authentication authentication)
      throws ProductException, UserException {

    Product product = productService.findById(id);

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    Report review = reportService.obtainFrom(reviewForm);
    review.setProduct(product);
    review.setUser(user);
    review = reportService.save(review);

    return ResponseEntity.ok(new ReviewDto(review));
  }

  @PostMapping("/products/{product-id}/reviews/{review-id}/comments")
  public ResponseEntity<ReviewDto> applyToReview(
      @PathVariable("product-id") Long productId,
      @PathVariable("review-id") Long reviewId,
      @RequestBody CommentForm commentForm,
      Authentication authentication) throws ProductException, ReportException, UserException {

    Product product = productService.findById(productId);
    Report review = reportService.findReviewById(reviewId);

    if (!Objects.equals(review.getProduct().getId(), product.getId())) {
      throw new ReportException(ReportExceptionProfile.INCOMPATIBLE_REVIEW);
    }

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    Comment comment = commentService.obtainFrom(commentForm);
    comment.setUser(user);
    comment.setReport(review);
    comment = commentService.save(comment);

    review = reportService.findReviewById(comment.getId());

    return ResponseEntity.ok(new ReviewDto(review));
  }

  @GetMapping("/products/{product-id}/questions")
  public ResponseEntity<ReviewListDto> getQuestionsByProduct(@PathVariable("product-id") Long id)
      throws ProductException {

    Product product = productService.findById(id);
    List<Report> reviews = reportService.findByProductAndType(product, ReportType.QUESTION);
    return ResponseEntity.ok(new ReviewListDto(reviews));
  }

  @PostMapping("/products/{product-id}/questions")
  public ResponseEntity<QuestionDto> addQuestionToProduct(@PathVariable("product-id") Long id,
      @RequestBody QuestionForm questionForm, Authentication authentication)
      throws ProductException, UserException {

    Product product = productService.findById(id);

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    Report question = reportService.obtainFrom(questionForm);
    question.setProduct(product);
    question.setUser(user);
    question = reportService.save(question);

    return ResponseEntity.ok(new QuestionDto(question));
  }

  @PostMapping("/products/{product-id}/questions/{question-id}/comments")
  public ResponseEntity<ReviewDto> applyToQuestion(
      @PathVariable("product-id") Long productId,
      @PathVariable("question-id") Long questionId,
      @RequestBody CommentForm commentForm,
      Authentication authentication) throws ProductException, ReportException, UserException {

    Product product = productService.findById(productId);
    Report question = reportService.findQuestionById(questionId);

    if (!Objects.equals(question.getProduct().getId(), product.getId())) {
      throw new ReportException(ReportExceptionProfile.INCOMPATIBLE_QUESTION);
    }

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    Comment comment = commentService.obtainFrom(commentForm);
    comment.setUser(user);
    comment.setReport(question);
    comment = commentService.save(comment);

    question = reportService.findQuestionById(comment.getId());

    return ResponseEntity.ok(new ReviewDto(question));
  }

  @PostMapping("/products")
  public ResponseEntity<Product> addProduct(@RequestBody ProductForm productForm,
      Authentication authentication) throws UserException {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    Product product = productService.obtainFrom(productForm);

    if (user.getCategory() != null) {
      product.setCategory(user.getCategory());
    } else {
      product.setCategory(ProductCategory.HOBBIES);
    }

    product = productService.save(product);
    return ResponseEntity.ok(product);
  }

  @PostMapping("/products/{product-id}")
  public ResponseEntity<Product> editProduct(
      @PathVariable("product-id") Long id,
      @RequestBody ProductForm productForm,
      Authentication authentication) throws UserException, ProductException {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    Product product = productService.findById(id);
    product = productService.obtainFromFormToObject(product, productForm);

    if (user.getCategory() != null && user.getCategory() != product.getCategory()) {
      throw new UserException(UserExceptionProfile.FOREIGN_CATEGORY);
    }

    product = productService.save(product);
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/products/{product-id}")
  public ResponseEntity<Product> deleteProduct(@PathVariable("product-id") Long id,
      Authentication authentication) throws ProductException, UserException {

    Product product = productService.findById(id);

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    if (user.getCategory() != null && user.getCategory() != product.getCategory()) {
      throw new UserException(UserExceptionProfile.FOREIGN_CATEGORY);
    }

    productService.delete(product);
    return ResponseEntity.ok(product);
  }
}
