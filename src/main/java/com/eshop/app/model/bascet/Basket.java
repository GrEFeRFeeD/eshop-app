package com.eshop.app.model.bascet;

import com.eshop.app.model.product.Product;
import com.eshop.app.model.user.User;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "basket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Basket {

  @EmbeddedId
  private BasketKey basketKey;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @MapsId("userId")
  private User user;

  @ManyToOne
  @JoinColumn(name = "product_id")
  @MapsId("productId")
  private Product product;

  private int count;
}
