package com.eshop.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ImageException extends Exception {

  @AllArgsConstructor
  public enum ImageExceptionProfile {

    IMAGE_NOT_FOUND("image_not_found",
        "Image by given id was not found.", HttpStatus.NOT_FOUND),

    TEMPORARY_STORE_FAILED("temporary_store_failed",
        "Access error - temporary store has failed.", HttpStatus.BAD_REQUEST);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final ImageExceptionProfile imageExceptionProfile;

  public ImageException(ImageExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.imageExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return imageExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return imageExceptionProfile.responseStatus;
  }
}
