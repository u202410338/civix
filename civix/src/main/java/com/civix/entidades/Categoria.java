package com.civix.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;
    private String nombre;
    private String descripcion;
    private String estado;
    private Integer horasEstimadas;
    private String icono;
}