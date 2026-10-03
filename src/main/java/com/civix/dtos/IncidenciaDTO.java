package com.civix.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaDTO {
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
    private CategoriaDTO categoria;
}