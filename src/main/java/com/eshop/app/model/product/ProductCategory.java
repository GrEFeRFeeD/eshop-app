package com.eshop.app.model.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ProductCategory {

  CLOTHES,
  ELECTRONICS,
  PERSONAL_CARE,
  HOBBIES,
  EQUIPMENT,
  FOOD_PRODUCTS,
  AGRICULTURE;

  private static Map<ProductCategory, List<String>> basicCharacteristics = new HashMap<>(
      Map.of(CLOTHES,       new ArrayList<>(List.of("Size", "Material", "Brand")),
             ELECTRONICS,   new ArrayList<>(List.of("Type", "Manufacturer")),
             PERSONAL_CARE, new ArrayList<>(List.of("Type", "Body part", "Ingredients")),
             HOBBIES,       new ArrayList<>(List.of("Field")),
             EQUIPMENT,     new ArrayList<>(List.of("Type")),
             FOOD_PRODUCTS, new ArrayList<>(List.of("GMO", "Lactose contains", "Peanut contains")),
             AGRICULTURE,   new ArrayList<>(List.of("Genus", "Modifications")))
  );

  public List<String> getBasicCharacteristics() {
    return basicCharacteristics.get(this);
  }
}
