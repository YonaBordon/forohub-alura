package com.alura.forohub.forohub.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TopicRequestDto {
  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "El mensaje es obligatorio.")
  private String message;

  @NotBlank(message = "El autor es obligatorio.")
  private String author;

  @NotBlank(message = "El curso es obligatorio.")
  private String course;

}
