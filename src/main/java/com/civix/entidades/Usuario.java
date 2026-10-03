package com.civix.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;
    private String telefono;
    private String passwordHash;
    private Boolean estado;
    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
}