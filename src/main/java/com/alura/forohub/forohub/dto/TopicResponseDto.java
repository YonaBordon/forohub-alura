package com.alura.forohub.forohub.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TopicResponseDto {
  private Long id;
  private String title;
  private String message;
  private String author;
  private String course;
  private LocalDateTime creationDate;
  private String status;
}