package com.eshop.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CategoryException extends Exception {

  @AllArgsConstructor
  public enum CategoryExceptionProfile {

    CATEGORY_NOT_FOUND("category_not_found",
        "Category by given id was not found.", HttpStatus.NOT_FOUND),

    CATEGORY_IS_IN_USE("category_is_in_use",
        "There are entities in the system that use the category.", HttpStatus.BAD_REQUEST);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final CategoryExceptionProfile categoryExceptionProfile;

  public CategoryException(CategoryExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.categoryExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return categoryExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return categoryExceptionProfile.responseStatus;
  }
}
