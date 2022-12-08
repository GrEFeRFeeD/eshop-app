package com.eshop.app.exceptions.handlers;

import com.eshop.app.exceptions.ImageException;
import com.eshop.app.exceptions.ProductException;
import com.eshop.app.exceptions.handlers.dto.ExceptionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ImageExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = ImageException.class)
  public ResponseEntity<Object> handleImageException(ImageException exception,
      WebRequest webRequest) {

    var exceptionBody = new ExceptionResponse(exception.getName(), exception.getMessage());

    return handleExceptionInternal(exception, exceptionBody, new HttpHeaders(),
        exception.getResponseStatus(), webRequest);
  }
}
