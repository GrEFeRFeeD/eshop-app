package com.eshop.app.initialization;

import com.eshop.app.model.image.Image;
import com.eshop.app.model.image.ImageRepository;
import com.eshop.app.model.image.ImageService;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRepository;
import com.eshop.app.model.user.UserRole;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartDataLoader implements ApplicationRunner {

  private final UserRepository userRepository;
  private final ImageService imageService;

  @Value("${first-admin-email}")
  private String email;

  @Autowired
  public StartDataLoader(UserRepository userRepository, ImageService imageService) {
    this.userRepository = userRepository;
    this.imageService = imageService;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Image defaultProfileImage = imageService.getDefaultProfilePicture();
    User firstAdmin = new User(null, email, email, UserRole.ADMIN, null, defaultProfileImage);
    firstAdmin = userRepository.save(firstAdmin);
    System.out.println("Added first user: " + firstAdmin);
  }
}
