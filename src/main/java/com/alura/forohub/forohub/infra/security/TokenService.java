package com.alura.forohub.forohub.infra.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


@Service
public class TokenService {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private long expiration;

  public String generateToken(String username) {
    return JWT.create()
        .withSubject(username)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
        .sign(Algorithm.HMAC256(secret));
  }

  public String validateToken(String token) {

    try {
      return JWT.require(Algorithm.HMAC256(secret))
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException e) {
      throw new RuntimeException("Token inválido o expirado.");
    }
  }
}