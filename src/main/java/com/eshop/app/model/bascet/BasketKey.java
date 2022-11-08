package com.eshop.app.model.bascet;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BasketKey implements Serializable {

  private Long userId;

  private Long productId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BasketKey basketKey = (BasketKey) o;
    return userId.equals(basketKey.userId) && productId.equals(basketKey.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, productId);
  }
}
