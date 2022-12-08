package com.eshop.app.controllers.forms;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryForm {

  @NotNull(message = "Field can not be null.")
  private String name;
  private List<String> basicCharacteristics;
}
