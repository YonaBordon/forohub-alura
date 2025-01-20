package com.alura.forohub.forohub.exception;

public class TokenExpiredException extends RuntimeException {
  public TokenExpiredException(String message) {
      super(message);
  }
}