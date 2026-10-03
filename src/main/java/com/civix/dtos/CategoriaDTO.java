package com.civix.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long idCategoria;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private Integer horasEstimadas;
    private String icono;
}