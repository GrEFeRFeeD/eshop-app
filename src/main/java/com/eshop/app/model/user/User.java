package com.eshop.app.model.user;

import com.eshop.app.model.manager_category.ManagerCategory;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  private String name;

  @Column(unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private ManagerCategory category;
}
