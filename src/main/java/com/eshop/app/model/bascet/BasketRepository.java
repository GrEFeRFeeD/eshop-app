package com.eshop.app.model.bascet;

import com.eshop.app.model.product.Product;
import com.eshop.app.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface BasketRepository extends CrudRepository<Basket, BasketKey> {

  List<Basket> findByUser(User user);

  @Query("SELECT b FROM Basket b WHERE b.user = :user AND b.product = :product")
  Basket findByUserAndProduct(User user, Product product);
}
