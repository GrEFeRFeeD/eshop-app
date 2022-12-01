package com.eshop.app.controllers.forms;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryForm {

  private String name;
  private List<String> basicCharacteristics;
}
