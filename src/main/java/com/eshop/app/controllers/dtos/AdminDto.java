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
public class AdminDto {

  private Long id;
  private String name;
  private String email;
  private Long image;

  public AdminDto(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.image = user.getImage().getId();
  }
}
