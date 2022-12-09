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
public class ProfileDto {

  private Long id;
  private String name;
  private String email;
  private Long image;
  private String role;

  public ProfileDto(User user) {
    this.id = user.getId();
    this.name = user.getName();
    this.email = user.getEmail();
    this.image = user.getImage().getId();
    this.role = user.getRole().toString();
  }
}
