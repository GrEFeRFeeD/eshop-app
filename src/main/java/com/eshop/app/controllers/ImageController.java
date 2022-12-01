package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.ImageDto;
import com.eshop.app.exceptions.ImageException;
import com.eshop.app.model.image.Image;
import com.eshop.app.model.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

  private final ImageService imageService;

  @Autowired
  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @PostMapping("/images")
  public ResponseEntity<ImageDto> uploadImage(@RequestBody MultipartFile multipartImage)
      throws ImageException {

    Image image = imageService.save(multipartImage);
    return ResponseEntity.ok(new ImageDto(image));
  }

  @GetMapping("/images/{image-id}")
  public ResponseEntity<Resource> downloadImage(@PathVariable("image-id") Long id)
      throws ImageException {

    return ResponseEntity.ok(imageService.findResourcesById(id));
  }
}
