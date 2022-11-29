package com.eshop.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends Exception{

  @AllArgsConstructor
  @Getter
  public enum UserExceptionProfile {

    USER_NOT_FOUND("user_not_found",
        "User by given identifier was not found.", HttpStatus.NOT_FOUND),

    USER_HAS_ANOTHER_ROLE("user_has_another_role",
        "User by given identifier already has another role.", HttpStatus.BAD_REQUEST);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;
  }

  private final String exceptionName;
  private final HttpStatus responseStatus;

  public UserException(UserExceptionProfile profile) {
    super(profile.getExceptionMessage());
    this.exceptionName = profile.getExceptionName();
    this.responseStatus = profile.getResponseStatus();
  }
}
