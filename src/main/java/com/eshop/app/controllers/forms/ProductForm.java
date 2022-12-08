package com.eshop.app.controllers.forms;

import java.util.List;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductForm {

  @NotNull(message = "Field can not be null.")
  private String name;

  @NotNull(message = "Field can not be null.")
  private String description;

  @NotNull(message = "Field can not be null.")
  @Min(value = 1, message = "Price can not be lower than 1.")
  private Double price;

  @NotNull(message = "Field can not be null.")
  private Long image;

  private List<CharacteristicForm> characteristics;
}
