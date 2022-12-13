package com.eshop.app.controllers.dtos;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EditManagerDto {

  @NotNull(message = "This field can not be null.")
  private Long category;
}
