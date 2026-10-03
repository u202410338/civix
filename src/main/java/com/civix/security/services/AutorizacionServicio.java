package com.civix.security.services;

import com.civix.repositorios.UsuarioRepositorio;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("autorizacion")
public class AutorizacionServicio {

    private final UsuarioRepositorio usuarioRepositorio;

    public AutorizacionServicio(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // ¿El id recibido pertenece al usuario autenticado?
    public boolean esUsuarioActual(Long idUsuario) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (idUsuario == null || auth == null || !auth.isAuthenticated()) {
            return false;
        }
        return usuarioRepositorio.findByCorreo(auth.getName())
                .map(u -> idUsuario.equals(u.getIdUsuario()))
                .orElse(false);
    }

    // ¿El correo recibido es el del usuario autenticado?
    public boolean esCorreoActual(String correo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return correo != null && auth != null && correo.equalsIgnoreCase(auth.getName());
    }
}