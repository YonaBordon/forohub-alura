package com.alura.forohub.forohub.infra.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final List<String> PUBLIC_ENDPOINT_PATTERNS = List.of(
        "^/api/auth/login$",       // Endpoint de login
        "^/api/auth/register$",    // Endpoint de registro
        "^/api/v3/api-docs.*$",        // Documentación de Swagger
        "^/api/swagger-ui.*$",         // Interfaz de Swagger UI
        "^/api/swagger-ui.html$"       // Página principal de Swagger
    );

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        logger.debug("Request URI: {}", requestURI);

        // Verificar si el endpoint es público
        if (isPublicEndpoint(requestURI)) {
            logger.info("Public endpoint accessed: {}", requestURI);
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            handleErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado.");
            return;
        }

        String token = header.substring(7);

        try {
            String username = tokenService.validateToken(token);
            if (username != null) {
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Token válido para el usuario: {}", username);
            }
        } catch (Exception e) {
            logger.error("Error al validar el token: {}", e.getMessage());
            handleErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado.");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String uri) {
        return PUBLIC_ENDPOINT_PATTERNS.stream().anyMatch(uri::matches);
    }

    private void handleErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        ApiResponse<String> apiResponse = ApiResponse.error(status, message);
        response.setStatus(status);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
