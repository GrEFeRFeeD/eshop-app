package com.eshop.app.exceptions.handlers;

import com.eshop.app.exceptions.UserException;
import com.eshop.app.exceptions.handlers.dto.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = UserException.class)
  public ResponseEntity<Object> handleBookingException(UserException exception,
      WebRequest webRequest) {

    var exceptionBody = new ExceptionResponse(exception.getExceptionName(), exception.getMessage());

    return handleExceptionInternal(exception, exceptionBody, new HttpHeaders(),
        exception.getResponseStatus(), webRequest);
  }
}
