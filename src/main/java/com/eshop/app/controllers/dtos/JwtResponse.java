package com.eshop.app.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DAO for JWT.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtResponse {

  private String token;
}
