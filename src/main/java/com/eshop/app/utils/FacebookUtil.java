package com.eshop.app.utils;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Util class for work with Facebook Token.
 */
@Service
public class FacebookUtil {

  private static final String markerUri = "https://graph.facebook.com/oauth/access_token?"
      + "client_id=%s&client_secret=%s&grant_type=client_credentials";
  private static final String checkMarkerUri = "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s";
  private static final String userUri = "https://graph.facebook.com/%s?fields=name,email&access_token=%s";

  public static final String userFacebookTokenUrlV15 = "https://www.facebook.com/v15.0/dialog/oauth?client_id=%s&redirect_uri=%s&response_type=token";

  /**
   * DTO object for Facebook Marker.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  static class FbMarker {

    @JsonAlias("access_token")
    private String accessToken;

    @JsonAlias("token_type")
    private String tokenType;
  }

  /**
   * Inner DTO class for facebook check token information.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  static class TokenData {

    @JsonAlias("app_id")
    private String appId;
    private String type;
    private String application;

    @JsonAlias("data_access_expires_at")
    private long dataAccessExpiresAt;

    @JsonAlias("expires_at")
    private long expiresAt;

    @JsonAlias("is_valid")
    private boolean isValid;
    private String[] scopes;

    @JsonAlias("user_id")
    private String userId;
  }

  /**
   * DTO object for facebook check token information.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  static class FbTokenInfo {

    private TokenData data;
  }

  /**
   * DTO object for Facebook User Information.
   */
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  static class FbUserInfo {

    private String email;
    private String name;

    @JsonAlias("user_id")
    private String userId;
  }

  @Value("${spring.security.oauth2.client.registration.facebook.clientId}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.facebook.clientSecret}")
  private String clientSecret;

  /**
   * Gets application marker from Facebook API.
   *
   * @return application marker
   */
  private String getMarker() {

    String uri = String.format(markerUri, clientId, clientSecret);
    RestTemplate restTemplate = new RestTemplate();
    FbMarker marker = restTemplate.getForObject(uri, FbMarker.class);

    return marker == null ? "null" : marker.getAccessToken();
  }

  /**
   * Gets user information object from Facebook by given Facebook User Token.
   *
   * @param token user facebook token
   * @return user information object
   */
  public FbTokenInfo getFbTokenInfo(String token) {

    String uri = String.format(checkMarkerUri, token, getMarker());
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(uri, FbTokenInfo.class);
  }

  /**
   * Gets username and email from Facebook by Facebook User Token.
   *
   * @param token user facebook token
   * @return user email
   */
  public Map<FacebookScopes, String> getScope(String token) {

    FbTokenInfo fbTokenInfo = getFbTokenInfo(token);
    String userId = fbTokenInfo.getData().getUserId();

    String uri = String.format(userUri, userId, token);
    RestTemplate restTemplate = new RestTemplate();
    FbUserInfo fbUserInfo = restTemplate.getForObject(uri, FbUserInfo.class);

    Map<FacebookScopes, String> scopes = new HashMap<>();
    if (fbUserInfo != null) {
      scopes.put(FacebookScopes.EMAIL, fbUserInfo.getEmail());
      scopes.put(FacebookScopes.NAME, fbUserInfo.getName());
    }

    return scopes;
  }

  /**
   * Enum for application facebook scopes.
   */
  public enum FacebookScopes {
    EMAIL, NAME
  }
}
