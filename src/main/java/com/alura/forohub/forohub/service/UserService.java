package com.alura.forohub.forohub.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alura.forohub.forohub.domain.entity.User;
import com.alura.forohub.forohub.domain.repository.UserRepository;
import com.alura.forohub.forohub.dto.AuthResponse;
import com.alura.forohub.forohub.infra.security.TokenService;

@Service
public class UserService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public UserService(AuthenticationManager authenticationManager,
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  public AuthResponse login(String username, String password) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    String token = tokenService.generateToken(username);

    return new AuthResponse("Inicio de sesión exitoso", token);
  }

  public AuthResponse register(String username, String password, String email) {
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("El nombre de usuario ya está en uso.");
    }

    if (userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("El correo electrónico ya está en uso.");
    }

    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);

    String token = tokenService.generateToken(username);

    return new AuthResponse("Usuario registrado con éxito", token);
  }
}
