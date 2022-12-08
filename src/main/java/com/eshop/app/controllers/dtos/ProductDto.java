package com.eshop.app.controllers.dtos;

import com.eshop.app.model.product.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {

  private Long id;
  private Long image;
  private String name;
  private String description;
  private Double price;
  private List<CharacteristicDto> characteristics;

  public ProductDto(Product product) {
    this.id = product.getId();
    this.image = product.getImage().getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.characteristics = product.getCharacteristics().stream().map(CharacteristicDto::new).collect(
        Collectors.toList());
  }
}
