package com.eshop.app.controllers.dtos;

import com.eshop.app.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {

  private Long id;
  private String email;
  private String name;
  private String role;

  public CustomerDto(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.role = user.getRole().toString();
  }
}
