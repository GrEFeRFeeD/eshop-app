package com.eshop.app.controllers.dtos;

import com.eshop.app.model.image.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageDto {

  private Long id;
  private String name;

  public ImageDto(Image image) {
    this.id = image.getId();
    this.name = image.getName();
  }
}
