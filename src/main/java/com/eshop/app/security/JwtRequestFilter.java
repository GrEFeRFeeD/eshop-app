package com.eshop.app.security;

import com.eshop.app.exceptions.SecurityException;
import com.eshop.app.exceptions.SecurityException.SecurityExceptionProfile;
import com.eshop.app.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Custom RequestFilter for handling JWT.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUserDetailsService jwtUserDetailsService;
  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain)
      throws ServletException, IOException {

    final String requestTokenHeader = request.getHeader("Authorization");

    String email = null;
    String jwtToken = null;
    String name = null;
    // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
    if (requestTokenHeader != null) {
      if (!requestTokenHeader.startsWith("Bearer ")) {
        throw new SecurityException(SecurityExceptionProfile.BAD_TOKEN);
      }

      jwtToken = requestTokenHeader.substring(7);
      try {
        email = jwtUtil.getEmailFromToken(jwtToken);
        name = jwtUtil.getNameFromToken(jwtToken);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get JWT Token");
      } catch (UnsupportedJwtException e) {
        throw new SecurityException(SecurityExceptionProfile.UNSUPPORTED_TOKEN);
      } catch (MalformedJwtException e) {
        throw new SecurityException(SecurityExceptionProfile.MALFORMED_TOKEN);
      } catch (SignatureException e) {
        throw new SecurityException(SecurityExceptionProfile.BAD_TOKEN_SIGNATURE);
      } catch (ExpiredJwtException e) {
        throw new SecurityException(SecurityExceptionProfile.EXPIRED_TOKEN);
      }
    }

    // Once we get the token validate it.
    if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      JwtUserDetails userDetails = (JwtUserDetails)
          this.jwtUserDetailsService.loadUserByEmailAndName(email, name);

      // If token is valid configure Spring Security to manually set authentication.
      if (jwtUtil.validateToken(jwtToken, userDetails)) {

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    chain.doFilter(request, response);
  }

}