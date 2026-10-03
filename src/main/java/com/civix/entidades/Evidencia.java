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
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidencia;
    private String urlArchivo;
    private String tipoArchivo;
    private String fase;
    private LocalDateTime fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;
}