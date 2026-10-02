package com.civix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialIncidenciaDTO {
    private Long idHistorial;
    private String estadoAnterior;
    private String estadoNuevo;
    private String comentario;
    private LocalDateTime fechaCambio;
    private Long idIncidencia;
    private Long idUsuario;
}