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
public class CalificacionDTO {
    private Long idCalificacion;
    private String comentario;
    private LocalDateTime fechaCalificacion;
    private Integer puntuacion;
    private Long idIncidencia;
}