package com.eshop.app.controllers.dtos;

import com.eshop.app.model.bascet.Basket;
import com.fasterxml.jackson.annotation.JsonAlias;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BasketDto {

  @NotNull(message = "Field can not be null.")
  private Long productId;

  @Min(value = 0L, message = "Value can not be less than or equal to zero.")
  private Integer count;

  public BasketDto(Basket basket) {
    this.productId = basket.getProduct().getId();
    this.count = basket.getCount();
  }
}
