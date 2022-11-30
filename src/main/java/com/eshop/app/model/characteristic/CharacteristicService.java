package com.eshop.app.model.characteristic;

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
}
