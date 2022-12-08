package com.eshop.app.controllers.dtos;

import com.eshop.app.model.user.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdminListDto {

  private List<AdminDto> admins;

  public AdminListDto(List<User> users) {
    admins = users.stream().map(AdminDto::new).collect(Collectors.toList());
  }
}
