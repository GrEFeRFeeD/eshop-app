package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.CategoryDto;
import com.eshop.app.controllers.dtos.CategoryListDto;
import com.eshop.app.controllers.forms.CategoryForm;
import com.eshop.app.exceptions.CategoryException;
import com.eshop.app.exceptions.CategoryException.CategoryExceptionProfile;
import com.eshop.app.model.category.Category;
import com.eshop.app.model.category.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/categories")
  public ResponseEntity<CategoryListDto> getCategoriesList() {

    List<Category> categories = categoryService.findAll();
    return ResponseEntity.ok(new CategoryListDto(categories));
  }

  @PostMapping("/categories")
  public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryForm categoryForm) {

    Category category = categoryService.obtainFrom(categoryForm);
    return ResponseEntity.ok(new CategoryDto(category));
  }

  @GetMapping("/categories/{category-id}")
  public ResponseEntity<CategoryDto> getCategoryCharacteristics(@PathVariable("category-id") Long id)
      throws CategoryException {

    Category category = categoryService.findById(id);
    return ResponseEntity.ok(new CategoryDto(category));
  }

  @DeleteMapping("/categories/{category-id}")
  public ResponseEntity<CategoryDto> deleteCategory(@PathVariable("category-id") Long id)
      throws CategoryException {

    Category category = categoryService.findById(id);
    categoryService.deleteIfNotInUse(category);
    return ResponseEntity.ok(new CategoryDto(category));
  }
}
