package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.AdminDto;
import com.eshop.app.controllers.dtos.CustomerDto;
import com.eshop.app.controllers.dtos.ManagerDto;
import com.eshop.app.controllers.dtos.QuestionListDto;
import com.eshop.app.controllers.dtos.ReviewListDto;
import com.eshop.app.controllers.dtos.UserDto;
import com.eshop.app.exceptions.UserException;
import com.eshop.app.model.report.Report;
import com.eshop.app.model.report.ReportService;
import com.eshop.app.model.report.ReportType;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRole;
import com.eshop.app.model.user.UserService;
import com.eshop.app.security.JwtUserDetails;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;
  private final ReportService reportService;

  @Autowired
  public UserController(UserService userService, ReportService reportService) {
    this.userService = userService;
    this.reportService = reportService;
  }

  @GetMapping("/me")
  public ResponseEntity<?> getMyself(Authentication authentication) throws UserException {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
    User user = userService.findByEmail(jwtUserDetails.getEmail());

    if (user.getRole() == UserRole.MANAGER) {
      return ResponseEntity.ok(new ManagerDto(user));
    }

    return ResponseEntity.ok(new UserDto(user));
  }

  @GetMapping("/users/{user-id}")
  public ResponseEntity<UserDto> getUser(@PathVariable("user-id") Long id) throws UserException {

    User user = userService.findById(id);
    return ResponseEntity.ok(new UserDto(user));
  }

  @GetMapping("/users/{user-id}/reviews")
  public ResponseEntity<ReviewListDto> getReviewsByUser(@PathVariable("user-id") Long id)
      throws UserException {

    User user = userService.findById(id);
    List<Report> reviews = reportService.findByUserAndType(user, ReportType.REVIEW);
    return ResponseEntity.ok(new ReviewListDto(reviews));
  }

  @GetMapping("/users/{user-id}/questions")
  public ResponseEntity<QuestionListDto> getQuestionsByUser(@PathVariable("user-id") Long id)
      throws UserException {

    User user = userService.findById(id);
    List<Report> questions = reportService.findByUserAndType(user, ReportType.QUESTION);
    return ResponseEntity.ok(new QuestionListDto(questions));
  }
}
