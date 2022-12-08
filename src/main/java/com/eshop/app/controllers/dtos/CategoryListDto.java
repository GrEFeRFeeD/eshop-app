package com.eshop.app.controllers.dtos;

import com.eshop.app.model.category.Category;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryListDto {

  private List<CategoryDto> categories;

  public CategoryListDto(List<Category> categories) {
    this.categories = categories.stream().map(CategoryDto::new).collect(Collectors.toList());
  }
}
