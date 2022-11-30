package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.CategoryListDto;
import com.eshop.app.controllers.dtos.ProductListDto;
import com.eshop.app.controllers.dtos.ReviewListDto;
import com.eshop.app.controllers.dtos.SingleCategoryDto;
import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.ProductException.ProductExceptionProfile;
import com.eshop.app.model.product.Product;
import com.eshop.app.model.product.ProductCategory;
import com.eshop.app.model.product.ProductService;
import com.eshop.app.model.report.Report;
import com.eshop.app.model.report.ReportService;
import com.eshop.app.model.report.ReportType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

  private final ProductService productService;
  private final ReportService reportService;

  @Autowired
  public ProductController(ProductService productService, ReportService reportService) {
    this.productService = productService;
    this.reportService = reportService;
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

  @GetMapping("/products/{product-id}/questions")
  public ResponseEntity<ReviewListDto> getQuestionsByProduct(@PathVariable("product-id") Long id)
      throws ProductException {

    Product product = productService.findById(id);
    List<Report> reviews = reportService.findByProductAndType(product, ReportType.QUESTION);
    return ResponseEntity.ok(new ReviewListDto(reviews));
  }
}
