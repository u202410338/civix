package com.civix.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    private String nombreRol;
    private String descripcion;
}
