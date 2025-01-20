package com.alura.forohub.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
  @NotBlank(message = "El nombre de usuario es obligatorio")
  private String username;

  @NotBlank(message = "La contraseña es obligatoria")
  private String password;

  @NotBlank(message = "El email es obligatorio")
  private String email;

}
