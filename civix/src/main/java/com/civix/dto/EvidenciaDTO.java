package com.civix.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class EvidenciaDTO {
    private Long idEvidencia;
    private String fase;
    private String tipoArchivo;
    private String urlArchivo;
    private Long idIncidencia;
}