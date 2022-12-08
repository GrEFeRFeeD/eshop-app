package com.eshop.app.controllers.dtos;

import com.eshop.app.model.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ManagerListDto {

  private List<ManagerDto> managers;

  public ManagerListDto(List<User> users) {
    managers = users.stream().map(ManagerDto::new).collect(Collectors.toList());
  }
}
