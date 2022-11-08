package com.eshop.app.model.manager_category;

import com.eshop.app.model.product.ProductCategory;
import com.eshop.app.model.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "managers_categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ManagerCategory {

  @Id
  @Column(name = "user_id")
  private Long id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;

  @Enumerated(EnumType.STRING)
  private ProductCategory category;
}
