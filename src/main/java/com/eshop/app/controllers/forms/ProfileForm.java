package com.eshop.app.controllers.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProfileForm {

  @NotBlank(message = "Field can not be null or blank.")
  private String name;

  @NotNull(message = "Field can not be null.")
  private Long image;
}
