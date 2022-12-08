package com.eshop.app.utils;

import com.eshop.app.security.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Util class for work with JWT.
 */
@Component
public class JwtUtil implements Serializable {

  @Value("${jwt.validity}")
  private Long jwtValidity;

  @Value("${jwt.secret}")
  private String secret;

  /**
   * Extracts email from token. Email meant to be subject of token.
   *
   * @param token token to get email from
   * @return email
   */
  public String getEmailFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * Extracts expiration date from token.
   *
   * @param token token to get expiration date from
   * @return expiration date of given token
   */
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * Extracts name from token.
   *
   * @param token token to get name from
   * @return name
   */
  public String getNameFromToken(String token) {
    return getClaimFromToken(token, (claims -> claims.get("name", String.class)));
  }

  /**
   * Extracts certain claim from token by given function.
   *
   * @param token          token to get claim from
   * @param claimsResolver function describing method of extracting claim from token
   * @param <T>            claim type
   * @return extracted claim from token by resolver function
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();

    return claimsResolver.apply(claims);
  }

  /**
   * Checks if the token has expired.
   *
   * @param token token to check
   * @return true if token is expired or else false
   */
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * Generates token for given UserDetails user.
   * Username, name, authorities, issued date and expiration date values will be signed with secret.
   *
   * @param jwtUserDetails user object to get email from
   * @return generated JSON Web Token
   */
  public String generateToken(JwtUserDetails jwtUserDetails) {

    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", jwtUserDetails.getAuthorities());
    claims.put("name", jwtUserDetails.getName());

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(jwtUserDetails.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtValidity * 1000))
        .signWith(SignatureAlgorithm.HS512, secret).compact();
  }

  /**
   * Validates JWT by userDetails username. Subject of token should be equal to username of
   * userDetails. Token must not been expired.
   *
   * @param token       token to validate
   * @param jwtUserDetails user object to get email from
   * @return true if token correct or else false
   */
  public Boolean validateToken(String token, JwtUserDetails jwtUserDetails) {
    final String email = getEmailFromToken(token);
    return (email.equals(jwtUserDetails.getEmail()) && !isTokenExpired(token));
  }
}