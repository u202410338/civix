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
public class HistorialIncidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorial;
    private String estadoAnterior;
    private String estadoNuevo;
    private String comentario;
    private String areaAsignada;
    private String usuarioAccion;
    private LocalDateTime fechaCambio;

    @ManyToOne
    @JoinColumn(name = "id_incidencia")
    private Incidencia incidencia;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}