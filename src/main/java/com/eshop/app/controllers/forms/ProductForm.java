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
public class ProductForm {

  private String name;
  private String description;
  private Double price;
  private Long image;
  private Long category;
  private List<CharacteristicForm> characteristics;
}
