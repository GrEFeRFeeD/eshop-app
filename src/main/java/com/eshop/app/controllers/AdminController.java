package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.AdminDto;
import com.eshop.app.controllers.dtos.AdminListDto;
import com.eshop.app.controllers.dtos.ManagerDto;
import com.eshop.app.controllers.dtos.ManagerListDto;
import com.eshop.app.controllers.dtos.NewAdminDto;
import com.eshop.app.controllers.dtos.NewManagerDto;
import com.eshop.app.exceptions.UserException;
import com.eshop.app.exceptions.UserException.UserExceptionProfile;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRole;
import com.eshop.app.model.user.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

  private final UserService userService;

  @Autowired
  public AdminController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/managers")
  public ResponseEntity<ManagerListDto> getManagerList() {

    List<User> users = userService.findAllByRole(UserRole.MANAGER);
    ManagerListDto managerList = new ManagerListDto(users);
    return ResponseEntity.ok(managerList);
  }

  @PostMapping("/users/managers")
  public ResponseEntity<ManagerDto> addNewManager(@RequestBody NewManagerDto newManagerDto)
      throws UserException {

    User user;
    try {
      user = userService.findByEmail(newManagerDto.getEmail());
    } catch (UserException e) {

      user = userService.addNewManager(newManagerDto.getEmail(), newManagerDto.getCategory());
      return ResponseEntity.ok(new ManagerDto(user));
    }

    throw new UserException(UserExceptionProfile.USER_HAS_ANOTHER_ROLE);
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
  public ResponseEntity<AdminDto> addNewAdmin(@RequestBody NewAdminDto adminDto)
      throws UserException {

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
  public ResponseEntity<AdminDto> deleteAdmin(@PathVariable("admin-id") Long adminId)
      throws UserException {

    User userToDelete = userService.findById(adminId);

    //TODO: add self revoking
    if (userToDelete.getRole() != UserRole.ADMIN) {
      throw new UserException(UserExceptionProfile.USER_HAS_ANOTHER_ROLE);
    }

    userService.delete(userToDelete);
    return ResponseEntity.ok(new AdminDto(userToDelete));
  }
}
