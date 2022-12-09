package com.eshop.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductException extends Exception {

  @AllArgsConstructor
  public enum ProductExceptionProfile {

    PRODUCT_NOT_FOUND("product_not_found",
        "Product by given identifier was not found.", HttpStatus.NOT_FOUND),

    BASIC_CHARACTERISTICS_NOT_COVERED("basic_characteristics_not_covered",
        "Product characteristic list should contain at least basic category characteristics.", HttpStatus.BAD_REQUEST);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final ProductExceptionProfile productExceptionProfile;

  public ProductException(ProductExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.productExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return productExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return productExceptionProfile.responseStatus;
  }
}
