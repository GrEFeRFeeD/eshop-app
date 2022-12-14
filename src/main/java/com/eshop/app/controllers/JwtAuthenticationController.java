package com.eshop.app.controllers;

import com.eshop.app.controllers.dtos.FacebookOauthInfoDto;
import com.eshop.app.controllers.dtos.JwtRequestDto;
import com.eshop.app.controllers.dtos.JwtResponseDto;
import com.eshop.app.exceptions.SecurityException;
import com.eshop.app.exceptions.SecurityException.SecurityExceptionProfile;
import com.eshop.app.model.user.UserService;
import com.eshop.app.security.JwtUserDetails;
import com.eshop.app.security.JwtUserDetailsService;
import com.eshop.app.utils.FacebookUtil;
import com.eshop.app.utils.FacebookUtil.FacebookScopes;
import com.eshop.app.utils.JwtUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 * Controller for authentication and authenticated requests.
 */
@CrossOrigin
@RestController
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final JwtUserDetailsService userDetailsService;
  private final FacebookUtil facebookUtil;
  private final UserService userService;

  @Autowired
  public JwtAuthenticationController(AuthenticationManager authenticationManager,
      JwtUtil jwtUtil, JwtUserDetailsService userDetailsService,
      FacebookUtil facebookUtil, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.facebookUtil = facebookUtil;
    this.userService = userService;
  }

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(
      @RequestBody JwtRequestDto jwtRequestDto) {

    Map<FacebookScopes, String> userScopes;
    try {
      userScopes = facebookUtil
          .getScope(jwtRequestDto.getFacebookToken());
    } catch (RestClientException e) {
      throw new SecurityException(SecurityExceptionProfile.BAD_FACEBOOK_TOKEN);
    }

    String email = userScopes.get(FacebookScopes.EMAIL);
    String name = userScopes.get(FacebookScopes.NAME);

    authenticate(email);

    final JwtUserDetails userDetails = (JwtUserDetails) userDetailsService
        .loadUserByEmailAndName(email, name);

    JwtResponseDto jwtResponseDto = new JwtResponseDto(jwtUtil.generateToken(userDetails));

    return ResponseEntity.ok(jwtResponseDto);
  }

  private void authenticate(String username) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, username));
    } catch (BadCredentialsException e) {
      throw new SecurityException(SecurityExceptionProfile.BAD_CREDENTIALS);
    }
  }

  @GetMapping("/oauth2/facebook/v15.0")
  public FacebookOauthInfoDto getFacebookClientId(
      @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
      String facebookClientId,
      @Value("${spring.security.oauth2.client.registration.facebook.redirectUri}")
      String redirectUri) {

    String requestUrl = String.format(FacebookUtil.userFacebookTokenUrlV15,
        facebookClientId, redirectUri) + "&scope=public_profile,email";

    return new FacebookOauthInfoDto(facebookClientId, redirectUri, requestUrl);
  }
}