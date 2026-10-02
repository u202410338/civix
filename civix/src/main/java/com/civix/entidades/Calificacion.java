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
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalificacion;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime fechaCalificacion;

    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;
}