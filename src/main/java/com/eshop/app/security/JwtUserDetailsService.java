package com.eshop.app.security;

import com.eshop.app.exceptions.ImageException;
import com.eshop.app.model.image.Image;
import com.eshop.app.model.image.ImageRepository;
import com.eshop.app.model.image.ImageService;
import com.eshop.app.model.user.User;
import com.eshop.app.model.user.UserRepository;
import com.eshop.app.model.user.UserRole;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Custom UserDetailsService that implement work with JWT.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ImageService imageService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Wrapper method of loadUserByUsername for loading user by email and name.
   *
   * @param email email of certain user
   * @param name name of certain user
   * @return standard loaded UserDetails object
   */
  public UserDetails loadUserByEmailAndName(String email, String name) {

    JwtUserDetails jwtUserDetails = (JwtUserDetails) loadUserByUsername(email);
    jwtUserDetails.setName(name);

    return jwtUserDetails;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    User user = userRepository.findByEmail(email);

    Set<GrantedAuthority> authorities = new HashSet<>();

    if (user == null) {
      Image image = null;
      try {
        image = imageService.getDefaultProfilePicture();
      } catch (ImageException e) {
        System.out.println("Catch image exception while loading the user " + email + "! " + e);
      }
      user = new User(null, email, email, UserRole.CUSTOMER, null, image);
      user = userRepository.save(user);
    }

    if (user.getRole() != null) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));
    }

    return new JwtUserDetails(email, null, passwordEncoder().encode(email), authorities);
  }
}