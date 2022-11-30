package com.eshop.app.model.characteristic;

import com.eshop.app.controllers.dtos.CharacteristicForm;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicService {

  private final CharacteristicRepository characteristicRepository;

  @Autowired
  public CharacteristicService(CharacteristicRepository characteristicRepository) {
    this.characteristicRepository = characteristicRepository;
  }

  public void deleteAll(List<Characteristic> characteristics) {
    characteristicRepository.deleteAll(characteristics);
  }

  public Characteristic createCharacteristic(CharacteristicForm characteristicForm) {

    Characteristic characteristic = new Characteristic();
    characteristic.setCharacteristic(characteristicForm.getCharacteristic());
    characteristic.setValue(characteristicForm.getValue());
    return save(characteristic);
  }

  public Characteristic save(Characteristic characteristic) {
    return characteristicRepository.save(characteristic);
  }
}
