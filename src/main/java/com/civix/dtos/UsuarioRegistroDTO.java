package com.civix.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroDTO {
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String dni;
    private String telefono;
}