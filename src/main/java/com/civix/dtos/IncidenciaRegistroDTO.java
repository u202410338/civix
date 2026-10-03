package com.civix.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidenciaRegistroDTO {
    private Long idCategoria;
    private String titulo;
    private String descripcion;
    private String direccionReferencia;
    private Double latitud;
    private Double longitud;
    private String prioridad;
}