package com.civix.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalificacion;
    private String comentario;
    private LocalDateTime fechaCalificacion;
    private Integer puntuacion;
    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;
}