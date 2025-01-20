package com.alura.forohub.forohub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alura.forohub.forohub.dto.AuthResponse;
import com.alura.forohub.forohub.dto.LoginRequest;
import com.alura.forohub.forohub.dto.RegisterRequest;
import com.alura.forohub.forohub.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    AuthResponse response = userService.login(
        loginRequest.getUsername(),
        loginRequest.getPassword());
    return ResponseEntity.ok(response); // Devuelve el objeto AuthResponse con el token
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
    AuthResponse response = userService.register(
        registerRequest.getUsername(),
        registerRequest.getPassword(),
        registerRequest.getEmail());
    return ResponseEntity.ok(response); // Devuelve el objeto AuthResponse con el token
  }
}
