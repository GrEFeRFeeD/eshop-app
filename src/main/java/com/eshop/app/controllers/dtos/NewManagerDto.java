package com.eshop.app.controllers.dtos;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewManagerDto {

  @NotNull(message = "Field can not be null.")
  private String email;

  @NotNull(message = "Field can not be null.")
  private Long category;
}
