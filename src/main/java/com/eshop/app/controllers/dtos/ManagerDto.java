package com.eshop.app.controllers.dtos;

import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagerDto {

  private Long id;
  private String email;
  private String name;
  private UserRole role;

  public ManagerDto(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.role = user.getRole();
  }
}
