package com.civix.security.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class AuthResponseDTO {
    private String jwt;
    private Long idUsuario;
    private String correo;
    private Set<String> roles;
}