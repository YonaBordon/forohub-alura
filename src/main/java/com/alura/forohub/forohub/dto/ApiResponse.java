package com.alura.forohub.forohub.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
  private Boolean Ok;
  private int status;
  private String message;
  private List<T> data;

  public static <T> ApiResponse<T> success(int statusCode, String message, List<T> elements) {
    return new ApiResponse<>(true, statusCode, message, elements);
  }

  public static <T> ApiResponse<T> error(int statusCode, String message) {
    return new ApiResponse<>(false, statusCode, message, null);
  }
}
