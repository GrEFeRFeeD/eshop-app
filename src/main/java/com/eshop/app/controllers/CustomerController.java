package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.BasketDto;
import com.eshop.app.controllers.dtos.BasketListDto;
import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.UserException;
import com.eshop.app.model.bascet.Basket;
import com.eshop.app.model.bascet.BasketService;
import com.eshop.app.model.product.Product;
import com.eshop.app.model.product.ProductService;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserService;
import com.eshop.app.security.JwtUserDetails;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {


  private final BasketService basketService;
  private final UserService userService;
  private final ProductService productService;

  @Autowired
  public CustomerController(BasketService basketService, UserService userService,
      ProductService productService) {
    this.basketService = basketService;
    this.userService = userService;
    this.productService = productService;
  }

  @GetMapping("/basket")
  public ResponseEntity<BasketListDto> getBasket(Authentication authentication)
      throws UserException {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());
    List<Basket> basket = basketService.findByUser(user);

    return ResponseEntity.ok(new BasketListDto(basket));
  }

  @PostMapping("/basket")
  public ResponseEntity<BasketDto> addItemToBasket(
      @Valid @RequestBody BasketDto basketDto,
      Authentication authentication)
      throws ProductException, UserException {

    Basket basket = getBasketByDto(basketDto, authentication);
    basket.setCount(basket.getCount() + basketDto.getCount());

    basket = basketService.save(basket);
    return ResponseEntity.ok(new BasketDto(basket));
  }

  @DeleteMapping("/basket")
  public ResponseEntity<BasketDto> deleteItemFromBasket(@Valid @RequestBody BasketDto basketDto,
      Authentication authentication) throws UserException, ProductException {

    Basket basket = getBasketByDto(basketDto, authentication);

    basket.setCount(Math.max(0, basket.getCount() - basketDto.getCount()));
    basket = basketService.save(basket);

    if (basket.getCount() == 0) {
      basketService.delete(basket);
    }

    return ResponseEntity.ok(new BasketDto(basket));
  }

  private Basket getBasketByDto(BasketDto basketDto, Authentication authentication)
      throws UserException, ProductException {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    Product product = productService.findById(basketDto.getProductId());

    return basketService.findById(user, product);
  }
}
