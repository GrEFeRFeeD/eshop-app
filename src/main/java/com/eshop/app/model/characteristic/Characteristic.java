package com.eshop.app.model.characteristic;

import com.eshop.app.model.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "characteristics")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Characteristic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "characteristic_id")
  @JsonIgnore
  private Long id;

  private String characteristic;

  @Column(name = "characteristic_value")
  private String value;

  @ManyToOne
  @JoinColumn(name = "product_id")
  @JsonIgnore
  private Product product;
}
