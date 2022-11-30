package com.eshop.app.controllers.dtos;

import com.eshop.app.model.product.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductListDto {

  private List<Product> products;
}
