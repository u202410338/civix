package com.civix.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Evidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidencia;
    private String fase;
    private String tipoArchivo;
    private String urlArchivo;
    @ManyToOne
    @JoinColumn(name = "id_incidencia", nullable = false)
    private Incidencia incidencia;
}