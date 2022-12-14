package com.eshop.app.exceptions.handlers;

import com.eshop.app.exceptions.handlers.dto.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException exception) {

    System.out.println("ALL ERRORS = " + exception.getBindingResult().getAllErrors());
    ObjectError objectError = exception.getBindingResult().getAllErrors().get(0);
    String message = "'" + ((FieldError) objectError).getField() + "': " + objectError.getDefaultMessage();

    return ResponseEntity.badRequest().body(new ExceptionResponse("validation_error", message));
  }
}
