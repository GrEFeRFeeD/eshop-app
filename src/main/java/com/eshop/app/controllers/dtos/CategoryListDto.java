package com.eshop.app.controllers.dtos;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryListDto {

  private List<String> categories;
  private Map<String, List<String>> characteristics;
}
