package com.civix.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaActualizarDTO {
    private Long idIncidencia;
    private String titulo;
    private String descripcion;
    private String estadoAtencion;
    private String prioridad;
    private Long idCategoria;
    private String comentario;
    private String areaAsignada;
}