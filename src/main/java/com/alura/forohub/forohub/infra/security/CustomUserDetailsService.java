package com.alura.forohub.forohub.infra.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alura.forohub.forohub.domain.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return userRepository.findByUsername(username)
          .or(() -> userRepository.findByEmail(username))
          .map(user -> org.springframework.security.core.userdetails.User
              .withUsername(user.getUsername())
              .password(user.getPassword())
              .roles("USER")
              .build())
          .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
  }
}
