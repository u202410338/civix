package com.civix.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String dni;
    private String telefono;
    private String passwordHash;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
}