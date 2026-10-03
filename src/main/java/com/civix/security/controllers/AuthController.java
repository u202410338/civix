package com.civix.security.controllers;

import com.civix.dtos.UsuarioDTO;
import com.civix.security.dtos.AuthRequestDTO;
import com.civix.security.dtos.AuthResponseDTO;
import com.civix.security.services.CustomUserDetailsService;
import com.civix.security.util.JwtUtil;
import com.civix.servicios.UsuarioServicio;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UsuarioServicio usuarioServicio;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          CustomUserDetailsService userDetailsService, UsuarioServicio usuarioServicio) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.usuarioServicio = usuarioServicio;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        // Si las credenciales son incorrectas lanza AuthenticationException (el handler responde 401)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getCorreo(), authRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getCorreo());
        String token = jwtUtil.generateToken(userDetails);
        UsuarioDTO  usuario = usuarioServicio.buscarPorCorreo(authRequest.getCorreo());

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        AuthResponseDTO body = new AuthResponseDTO();
        body.setJwt(token);
        body.setIdUsuario(usuario.getIdUsuario());
        body.setCorreo(usuario.getCorreo());
        body.setRoles(roles);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(body);
    }
}