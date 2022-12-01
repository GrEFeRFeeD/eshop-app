package com.eshop.app.model.user;

import com.eshop.app.model.category.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByRole(UserRole userRole);
  User findByEmail(String email);
  List<User> findByCategory(Category category);
}
