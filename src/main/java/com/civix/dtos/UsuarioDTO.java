package com.civix.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String dni;
    private String telefono;
    private Boolean estado;
    private LocalDateTime fechaRegistro;
    private RolDTO rol;
}