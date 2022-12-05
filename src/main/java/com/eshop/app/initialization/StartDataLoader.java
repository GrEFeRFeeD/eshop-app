package com.eshop.app.initialization;

import com.eshop.app.model.category.Category;
import com.eshop.app.model.category.CategoryRepository;
import com.eshop.app.model.image.Image;
import com.eshop.app.model.image.ImageRepository;
import com.eshop.app.model.image.ImageService;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRepository;
import com.eshop.app.model.user.UserRole;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartDataLoader implements ApplicationRunner {

  private final UserRepository userRepository;
  private final ImageService imageService;
  private final CategoryRepository categoryRepository;

  @Value("${first-admin-email}")
  private String email;

  @Autowired
  public StartDataLoader(UserRepository userRepository, ImageService imageService,
      CategoryRepository categoryRepository) {
    this.userRepository = userRepository;
    this.imageService = imageService;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Image defaultProfileImage = imageService.getDefaultProfilePicture();
    User firstAdmin = new User(null, email, email, UserRole.ADMIN, null, defaultProfileImage);
    firstAdmin = userRepository.save(firstAdmin);

    Category category = new Category(null, "cat1", new ArrayList<>(List.of("be", "bebe")));
    category = categoryRepository.save(category);

    User firstManager = new User(null, "be", "zhurzhakaterina@gmail.com", UserRole.MANAGER, category, defaultProfileImage);
    firstManager = userRepository.save(firstManager);

    System.out.println("Added first user: " + firstAdmin);
    System.out.println("Added second user: " + firstManager);
    System.out.println("Added category: " + category);
  }
}
