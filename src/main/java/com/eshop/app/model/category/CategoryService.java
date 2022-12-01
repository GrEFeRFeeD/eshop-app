package com.eshop.app.model.category;

import com.eshop.app.controllers.forms.CategoryForm;
import com.eshop.app.exceptions.CategoryException;
import com.eshop.app.exceptions.CategoryException.CategoryExceptionProfile;
import com.eshop.app.model.product.Product;
import com.eshop.app.model.product.ProductService;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final ProductService productService;
  private final UserService userService;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository, ProductService productService,
      UserService userService) {
    this.categoryRepository = categoryRepository;
    this.productService = productService;
    this.userService = userService;
  }

  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  public Category findById(Long id) throws CategoryException {
    return categoryRepository.findById(id).orElseThrow(() -> new CategoryException(
        CategoryExceptionProfile.CATEGORY_NOT_FOUND));
  }

  public List<Category> findAll() {
    return (List<Category>) categoryRepository.findAll();
  }

  public Category obtainFrom(CategoryForm categoryForm) {

    Category category = new Category();
    category.setName(categoryForm.getName());
    category.setBasicCharacteristics(new ArrayList<>(categoryForm.getBasicCharacteristics()));
    return save(category);
  }

  public void deleteIfNotInUse(Category category) throws CategoryException {

    List<Product> products = productService.findByCategory(category);
    List<User> users = userService.findByCategory(category);

    if (products.isEmpty() && users.isEmpty()) {
      categoryRepository.delete(category);
    }

    throw new CategoryException(CategoryExceptionProfile.CATEGORY_IS_IN_USE);
  }
}
