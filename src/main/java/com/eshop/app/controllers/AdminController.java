package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.AdminDto;
import com.eshop.app.controllers.dtos.AdminListDto;
import com.eshop.app.controllers.dtos.ManagerDto;
import com.eshop.app.controllers.dtos.ManagerListDto;
import com.eshop.app.controllers.dtos.NewAdminDto;
import com.eshop.app.controllers.dtos.NewManagerDto;
import com.eshop.app.exceptions.CategoryException;
import com.eshop.app.exceptions.ImageException;
import com.eshop.app.exceptions.UserException;
import com.eshop.app.exceptions.UserException.UserExceptionProfile;
import com.eshop.app.model.category.Category;
import com.eshop.app.model.category.CategoryService;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRole;
import com.eshop.app.model.user.UserService;
import com.eshop.app.security.JwtUserDetails;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

  private final UserService userService;
  private final CategoryService categoryService;

  @Autowired
  public AdminController(UserService userService, CategoryService categoryService) {
    this.userService = userService;
    this.categoryService = categoryService;
  }

  @GetMapping("/users/managers")
  public ResponseEntity<ManagerListDto> getManagerList() {

    List<User> users = userService.findAllByRole(UserRole.MANAGER);
    ManagerListDto managerList = new ManagerListDto(users);
    return ResponseEntity.ok(managerList);
  }

  @PostMapping("/users/managers")
  public ResponseEntity<ManagerDto> addNewManager(@Valid @RequestBody NewManagerDto newManagerDto)
      throws UserException, CategoryException, ImageException {

    Category category = categoryService.findById(newManagerDto.getCategory());

    User user;
    try {
      user = userService.findByEmail(newManagerDto.getEmail());

      if (user.getRole() != UserRole.MANAGER) {
        throw new UserException(UserExceptionProfile.USER_HAS_ANOTHER_ROLE);
      }

      user.setCategory(category);
      user = userService.save(user);
    } catch (UserException e) {

      user = userService.addNewManager(newManagerDto.getEmail(), category);
    }

    return ResponseEntity.ok(new ManagerDto(user));
  }

  @DeleteMapping("/users/managers/{manager-id}")
  public ResponseEntity<ManagerDto> deleteManager(@PathVariable("manager-id") Long managerId)
      throws UserException {

    User userToDelete = userService.findById(managerId);

    if (userToDelete.getRole() != UserRole.MANAGER) {
      throw new UserException(UserExceptionProfile.USER_HAS_ANOTHER_ROLE);
    }

    userService.delete(userToDelete);
    return ResponseEntity.ok(new ManagerDto(userToDelete));
  }

  @GetMapping("/users/admins")
  public ResponseEntity<AdminListDto> getAdminList() {

    List<User> users = userService.findAllByRole(UserRole.ADMIN);
    AdminListDto adminList = new AdminListDto(users);
    return ResponseEntity.ok(adminList);
  }

  @PostMapping("/users/admins")
  public ResponseEntity<AdminDto> addNewAdmin(@Valid @RequestBody NewAdminDto adminDto)
      throws UserException, ImageException {

    User user;
    try {
      user = userService.findByEmail(adminDto.getEmail());
    } catch (UserException e) {

      user = userService.addNewAdmin(adminDto.getEmail());
      return ResponseEntity.ok(new AdminDto(user));
    }

    throw new UserException(UserExceptionProfile.USER_HAS_ANOTHER_ROLE);
  }

  @DeleteMapping("/users/admins/{admin-id}")
  public ResponseEntity<AdminDto> deleteAdmin(@PathVariable("admin-id") Long adminId,
      Authentication authentication)
      throws UserException {

    User userToDelete = userService.findById(adminId);

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    if (Objects.equals(userToDelete.getEmail(), jwtUserDetails.getEmail())) {
      throw new UserException(UserExceptionProfile.USER_SELF_REVOKING);
    }

    if (userToDelete.getRole() != UserRole.ADMIN) {
      throw new UserException(UserExceptionProfile.USER_HAS_ANOTHER_ROLE);
    }

    userService.delete(userToDelete);
    return ResponseEntity.ok(new AdminDto(userToDelete));
  }
}
