package com.eshop.app.controllers.dtos;

import com.eshop.app.model.category.Category;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

  private Long id;
  private String name;
  private List<String> basicCharacteristics;

  public CategoryDto(Category category) {
    this.id = category.getId();
    this.name = category.getName();
    this.basicCharacteristics = new ArrayList<>(category.getBasicCharacteristics());
  }
}
