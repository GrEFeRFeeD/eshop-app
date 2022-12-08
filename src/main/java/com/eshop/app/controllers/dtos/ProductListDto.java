package com.eshop.app.controllers.dtos;

import com.eshop.app.model.product.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductListDto {

  private List<ProductDto> products;

  public ProductListDto(List<Product> products) {
    this.products = products.stream().map(ProductDto::new).collect(Collectors.toList());
  }
}
