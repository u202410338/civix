package com.civix.security.services;

import com.civix.entidades.Usuario;
import com.civix.repositorios.UsuarioRepositorio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public CustomUserDetailsService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<GrantedAuthority> authorities = new HashSet<>();
        if (usuario.getRol() != null) {
            // En la BD el rol es CIUDADANO / ADMINISTRADOR; Spring espera el prefijo ROLE_
            authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombreRol()));
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getCorreo())
                .password(usuario.getPasswordHash())
                .authorities(authorities)
                .disabled(Boolean.FALSE.equals(usuario.getEstado())) // baja lógica (US15)
                .build();
    }
}