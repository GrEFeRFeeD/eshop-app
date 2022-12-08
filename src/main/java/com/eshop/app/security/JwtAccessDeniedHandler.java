package com.eshop.app.security;

import com.eshop.app.exceptions.SecurityException;
import com.eshop.app.exceptions.SecurityException.SecurityExceptionProfile;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Custom denied access handler that resolving errors through
 * delegating handling to controller level exception handler.
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private final HandlerExceptionResolver resolver;

  @Autowired
  public JwtAccessDeniedHandler(@Qualifier("handlerExceptionResolver")
      HandlerExceptionResolver resolver) {

    this.resolver = resolver;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    resolver.resolveException(request, response, null, new SecurityException(
        SecurityExceptionProfile.ACCESS_DENIED));
  }
}
