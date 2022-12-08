package com.eshop.app.model.user;

import com.eshop.app.exceptions.ImageException;
import com.eshop.app.exceptions.UserException;
import com.eshop.app.exceptions.UserException.UserExceptionProfile;
import com.eshop.app.model.category.Category;
import com.eshop.app.model.image.Image;
import com.eshop.app.model.image.ImageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ImageService imageService;

  @Autowired
  public UserService(UserRepository userRepository, ImageService imageService) {
    this.userRepository = userRepository;
    this.imageService = imageService;
  }

  public List<User> findAllByRole(UserRole userRole) {
    return userRepository.findByRole(userRole);
  }

  public User findByEmail(String email) throws UserException {

   User user = userRepository.findByEmail(email);
   if (user == null) {
     throw new UserException(UserExceptionProfile.USER_NOT_FOUND);
   }

    return user;
  }

  public User addNewManager(String email, Category category) throws ImageException {

    Image image = imageService.getDefaultProfilePicture();
    User newUser = new User(null, email, email, UserRole.MANAGER, category, image);
    return userRepository.save(newUser);
  }

  public User addNewAdmin(String email) throws ImageException {

    Image image = imageService.getDefaultProfilePicture();
    User newUser = new User(null, email, email, UserRole.ADMIN, null, image);
    return userRepository.save(newUser);
  }

  public User findById(Long id) throws UserException {

    User user = userRepository.findById(id).orElseThrow(() -> new UserException(
        UserExceptionProfile.USER_NOT_FOUND));

    return user;
  }

  public void delete(User user) {
    userRepository.delete(user);
  }

  public List<User> findByCategory(Category category) {
    return userRepository.findByCategory(category);
  }

  public User save(User user) {
    return userRepository.save(user);
  }
}
