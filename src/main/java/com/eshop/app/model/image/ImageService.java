package com.eshop.app.model.image;

import com.eshop.app.exceptions.ImageException;
import com.eshop.app.exceptions.ImageException.ImageExceptionProfile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

  private final ImageRepository imageRepository;

  @Autowired
  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public Image save(MultipartFile multipartImage) throws ImageException {

    Image image = new Image();
    image.setName(multipartImage.getName());

    try {
      image.setContent(multipartImage.getBytes());
    } catch (IOException e) {
      throw new ImageException(ImageExceptionProfile.TEMPORARY_STORE_FAILED);
    }

    return imageRepository.save(image);
  }

  public Image save(Image image) {
    return imageRepository.save(image);
  }

  public Image findById(Long id) throws ImageException {
    return imageRepository.findById(id).orElseThrow(() ->
        new ImageException(ImageExceptionProfile.IMAGE_NOT_FOUND));
  }

  public Resource findResourcesById(Long id) throws ImageException {

    Image image = findById(id);
    return new ByteArrayResource(image.getContent());
  }

  public void deleteIfNoUsage(Image image) {
  }

  public Image getDefaultProfilePicture() throws ImageException {

    File file = new File("./pictures/profile_picture.jpg");
    FileInputStream input = null;
    try {
      input = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    MultipartFile multipartFile;
    try {
      multipartFile = new MockMultipartFile("default profile picture",
          file.getName(), "text/plain", org.apache.commons.io.IOUtils.toByteArray(input));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return save(multipartFile);
  }
}
