package com.eshop.app.controllers.dtos;

import com.eshop.app.model.bascet.Basket;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BasketListDto {

  private List<BasketDto> basket;
  
  public BasketListDto(List<Basket> basket) {
    this.basket = basket.stream().map(BasketDto::new).collect(Collectors.toList());
  }
}
