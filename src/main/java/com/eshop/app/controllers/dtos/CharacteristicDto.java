package com.eshop.app.controllers.dtos;

import com.eshop.app.model.characteristic.Characteristic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CharacteristicDto {

  private String characteristic;
  private String value;

  public CharacteristicDto(Characteristic characteristic) {
    this.characteristic = characteristic.getCharacteristic();
    this.value = characteristic.getValue();
  }
}
