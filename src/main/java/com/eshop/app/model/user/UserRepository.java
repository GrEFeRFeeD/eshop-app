package com.eshop.app.model.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByRole(UserRole userRole);
  //Optional<User> findByEmail(String email);
  User findByEmail(String email);
}
