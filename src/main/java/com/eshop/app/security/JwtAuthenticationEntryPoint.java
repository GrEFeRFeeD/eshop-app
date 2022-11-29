package com.eshop.app.security;

import com.eshop.app.exceptions.SecurityException;
import com.eshop.app.exceptions.SecurityException.SecurityExceptionProfile;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Entry point for handling unauthorized operations.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  private final HandlerExceptionResolver resolver;

  @Autowired
  public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver")
      HandlerExceptionResolver resolver) {

    this.resolver = resolver;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {

    resolver.resolveException(request, response, null,
        new SecurityException(SecurityExceptionProfile.UNAUTHENTICATED));
  }
}