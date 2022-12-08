package com.eshop.app.controllers.forms;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacteristicForm {

  @NotNull(message = "Field can not be null.")
  private String characteristic;

  @NotNull(message = "Field can not be null.")
  private String value;
}
