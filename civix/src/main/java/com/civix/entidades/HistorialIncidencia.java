package com.civix.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class HistorialIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;
    private String areaAsignada;
    private String comentario;
    private String estadoAnterior;
    private String estadoNuevo;
    private LocalDateTime fechaCambio;
    private String usuarioAccion;
    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}