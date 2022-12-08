package com.eshop.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class for representing all possible authentication
 * and authorization (security) exceptions.
 */
@Getter
public class SecurityException extends RuntimeException {

  /**
   * Enum that describes type of exception.
   */
  @AllArgsConstructor
  public enum SecurityExceptionProfile {

    UNAUTHENTICATED("not_authenticated",
        "You are not authenticated to perform this action.", HttpStatus.UNAUTHORIZED),

    BAD_FACEBOOK_TOKEN("bad_facebook_token",
        "Invalid OAuth access token - cannot parse access token.", HttpStatus.UNAUTHORIZED),

    BAD_TOKEN("bad_token",
        "Token does not start with 'Bearer'.", HttpStatus.UNAUTHORIZED),

    EXPIRED_TOKEN("expired_token",
        "Token has expired.", HttpStatus.UNAUTHORIZED),

    BAD_TOKEN_SIGNATURE("bad_token_signature", "Given JWT"
        + " signature does not match locally computed signature.", HttpStatus.UNAUTHORIZED),

    MALFORMED_TOKEN("malformed_token",
        "Unable to read JWT JSON value.", HttpStatus.UNAUTHORIZED),

    UNSUPPORTED_TOKEN("unsupported_token", "JWT in a particular"
        + " format/configuration that does not match the format expected by the application.",
        HttpStatus.UNAUTHORIZED),

    BAD_CREDENTIALS("bad_credentials",
        "Incorrect credentials.", HttpStatus.UNAUTHORIZED),

    ACCESS_DENIED("access_denied",
        "You are not allowed to perform this action.", HttpStatus.FORBIDDEN);

    private final String exceptionName;
    private final String exceptionMessage;
    private final HttpStatus responseStatus;

  }

  private final SecurityExceptionProfile securityExceptionProfile;

  public SecurityException(SecurityExceptionProfile exceptionProfile) {
    super(exceptionProfile.exceptionMessage);
    this.securityExceptionProfile = exceptionProfile;
  }

  public String getName() {
    return securityExceptionProfile.exceptionName;
  }

  public HttpStatus getResponseStatus() {
    return securityExceptionProfile.responseStatus;
  }
}
