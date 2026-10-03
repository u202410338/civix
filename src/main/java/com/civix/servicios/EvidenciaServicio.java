package com.civix.servicios;

import com.civix.dtos.EvidenciaDTO;

import java.util.List;

public interface EvidenciaServicio {
    EvidenciaDTO registrar(EvidenciaDTO evidenciaDTO);
    List<EvidenciaDTO> listar();
    List<EvidenciaDTO> buscarPorIncidencia(Long idIncidencia);
    void eliminar(Long id);
}