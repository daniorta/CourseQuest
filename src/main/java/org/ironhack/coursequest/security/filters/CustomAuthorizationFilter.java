package org.ironhack.coursequest.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * CustomAuthorizationFilter is an implementation of OncePerRequestFilter to handle
 * authorization of a user to access the API endpoints.
 */
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    /**
     * The method doFilterInternal will handle the authorization of a user to access the API endpoints.
     *
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param filterChain FilterChain
     * @throws ServletException if there is a servlet related error
     * @throws IOException      if there is an Input/Output error
     */
    // Para el manejo del acceso de los usarios a los endpoints
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Si la ruta solicitada es /api/login, no aplicamos autorización. Dejamos que el siguiente filtro maneje el login
        if (request.getServletPath().equals("/api/login")) { // <---- si quiero que mi login sea otro lo modifico aquí TAMBIÉN
            filterChain.doFilter(request, response);
        } else {
            // Para cualquier otra ruta, revisamos si hay un header Authorization con un token válido
            String authorizationHeader = request.getHeader(AUTHORIZATION);

            // Verificamos si el header empieza por "Bearer ", lo cual indica que contiene un token JWT
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    // Extraemos el token JWT quitando el prefijo "Bearer "
                    String token = authorizationHeader.substring("Bearer ".length());

                    // Creamos el algoritmo para verificar el JWT (clave secreta)
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();

                    // Validamos la firma del token. Si es inválida, se lanzará una excepción
                    DecodedJWT decodedJWT = verifier.verify(token);

                    // Obtenemos el nombre de usuario y los roles del token decodificado
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                    // Convertimos los roles en autoridades de Spring Security
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    // Creamos un objeto de autenticación con usuario y roles y lo registramos en el contexto de seguridad
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    // Continuamos con la cadena de filtros una vez autenticado el usuario
                    filterChain.doFilter(request, response);

                } catch (Exception exception) {
                    log.error("Error logging in: {}", exception.getMessage());

                    // Si hubo un error (token inválido, expirado, etc.), devolvemos estado 403 Forbidden
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());

                    // Respondemos con un mensaje de error en formato JSON
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }

            } else {
                // Si no hay token o no empieza por "Bearer", simplemente dejamos pasar la petición (puede ser un endpoint público)
                filterChain.doFilter(request, response);
            }
        }
    }
}