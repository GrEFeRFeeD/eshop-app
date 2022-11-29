package com.eshop.app.model.user;

import com.eshop.app.exceptions.UserException;
import com.eshop.app.exceptions.UserException.UserExceptionProfile;
import com.eshop.app.model.product.ProductCategory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
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

  public User addNewManager(String email, ProductCategory productCategory) {

    //TODO: add name
    User newUser = new User(null, email, email, UserRole.MANAGER, productCategory);
    return userRepository.save(newUser);
  }

  public User addNewAdmin(String email) {

    //TODO: add name
    User newUser = new User(null, email, email, UserRole.ADMIN, null);
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
}
