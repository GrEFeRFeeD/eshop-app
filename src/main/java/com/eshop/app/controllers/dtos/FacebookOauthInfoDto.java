package com.eshop.app.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for facebook client id.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacebookOauthInfoDto {

  private String clientId;
  private String redirectUri;
  private String tokenRequestUrl;

  public FacebookOauthInfoDto(String clientId, String redirectUri) {
    this.clientId = clientId;
    this.redirectUri = redirectUri;
  }
}
