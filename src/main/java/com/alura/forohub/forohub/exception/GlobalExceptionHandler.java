package com.alura.forohub.forohub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alura.forohub.forohub.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ApiResponse<String>> handleTokenExpiredException(TokenExpiredException ex) {
    ApiResponse<String> response = ApiResponse.error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ApiResponse<String>> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
    ApiResponse<String> response = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
    ApiResponse<String> response = ApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
    ApiResponse<String> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Error interno del servidor.");
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}