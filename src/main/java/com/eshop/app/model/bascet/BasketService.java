package com.eshop.app.model.bascet;

import com.eshop.app.model.product.Product;
import com.eshop.app.model.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

  private final BasketRepository basketRepository;

  @Autowired
  public BasketService(BasketRepository basketRepository) {
    this.basketRepository = basketRepository;
  }

  public List<Basket> findByUser(User user) {
    return basketRepository.findByUser(user);
  }

  public Basket findById(User user, Product product) {

    Basket basket = basketRepository.findById(new BasketKey(user.getId(), product.getId()))
        .orElse(getEmptySavedBasket(user, product));

    return basket;
  }

  private Basket getEmptySavedBasket(User user, Product product) {
    return basketRepository.save(new Basket(null, user, product, 0));
  }

  public Basket save(Basket basket) {
    return basketRepository.save(basket);
  }

  public void delete(Basket basket) {
    basketRepository.delete(basket);
  }
}
