package com.eshop.app.controllers.dtos;

import com.eshop.app.model.product.ProductCategory;
import com.eshop.app.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewManagerDto {

  private String email;
  private ProductCategory category;
}
