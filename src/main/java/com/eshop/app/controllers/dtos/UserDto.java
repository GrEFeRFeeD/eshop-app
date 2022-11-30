package com.eshop.app.controllers.dtos;

import com.eshop.app.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

  private Long id;
  private String name;
  private String role;

  public UserDto(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.role = user.getRole().toString();
  }
}
