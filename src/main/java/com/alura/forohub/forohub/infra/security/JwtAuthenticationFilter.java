package com.alura.forohub.forohub.infra.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alura.forohub.forohub.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  public JwtAuthenticationFilter(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    // Omitir validación de token en los endpoints de login y registro
    if (request.getRequestURI().equals("/api/auth/login") || request.getRequestURI().equals("/api/auth/register")) {
      chain.doFilter(request, response); // Continuar con el siguiente filtro
      return;
    }

    String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      // Genera un objeto ApiResponse para el error
      ApiResponse<String> apiResponse = ApiResponse.error(HttpServletResponse.SC_UNAUTHORIZED,
          "Token no proporcionado.");

      // Establece el estado HTTP
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      // Convierte la respuesta a JSON y la escribe en el cuerpo de la respuesta
      ObjectMapper objectMapper = new ObjectMapper();
      response.setContentType("application/json");
      response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
      return;
    }
    String token = header.substring(7);
    String username = tokenService.validateToken(token);

    if (username != null) {
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null,
          new ArrayList<>());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(request, response); 
  }
}
