package org.ironhack.coursequest.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ironhack.coursequest.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
// (Simple Logging Facade for Java) una fachada para hacer logs más profesionales que SOUT, permite usar logs (log.info, log.error, etc)
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    /**
     * Constructor for CustomAuthenticationFilter
     *
     * @param authenticationManager
     */
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Attempts to authenticate the user with given credentials
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Authentication object if successful, otherwise throws an exception
     * @throws AuthenticationException
     */
    @Override
    // Procesa el login. Lee username y password del request.
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Cogiendo de la request y casteandolo a mi LoginRequest para poder usarlo más fácilmente
            ObjectMapper mapper = new ObjectMapper();
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);

            // Crea un Authentication token con el username y password recibidos --> NO EL DE RESPUESTA
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            // Porque estamos aprendiendo hacemos esto, normalmente no mostramos la password
            log.info("Username is: {}", loginRequest.getUsername());
            log.info("Password is: {}", loginRequest.getPassword());

            // Attempting to authenticate the user with the given credentials
            // Intento de autenticación del usuario con el token generado
            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method is called if the user is successfully authenticated
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param chain          FilterChain
     * @param authentication Authentication
     * @throws IOException      if there is an Input/Output error
     * @throws ServletException if there is a servlet related error
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // Obtiene el usuario (DE SPRING SECURITY) autenticado casteando el auth principal al objeto User de SPRING SECURITY no de mi app
        User user = (User) authentication.getPrincipal();

        // Creaa un HMAC256 (Hash-based Message Authentication Code que usa el algoritmo SHA-512)
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        // Genera token JWT con username y roles
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // <---- si quiero que este token dure más tiempo
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        // Un mapa que contiene el token generado
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);

        // Seteamos la respuesta al tipo application/json
        response.setContentType(APPLICATION_JSON_VALUE);

        // Escribimos el token en la respuesta
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

}