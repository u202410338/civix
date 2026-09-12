package com.civix.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIncidencia;
    private String codigoIncidencia;
    private String titulo;
    private String descripcion;
    private String direccionReferencia;
    private Boolean esSugeridoIa;
    private String estadoAtencion;
    private LocalDateTime fechaLimiteEstimada;
    private LocalDateTime fechaRegistro;
    private Double latitud;
    private Double longitud;
    private String prioridad;
    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;
}