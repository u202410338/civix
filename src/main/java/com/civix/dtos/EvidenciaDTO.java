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
public class EvidenciaDTO {
    private Long idEvidencia;
    private String fase;
    private String tipoArchivo;
    private String urlArchivo;
    private LocalDateTime fechaRegistro;
    private Long idIncidencia;
}