package com.civix.servicios;

import com.civix.dtos.HistorialIncidenciaDTO;

import java.util.List;

public interface HistorialIncidenciaServicio {
    HistorialIncidenciaDTO registrar(HistorialIncidenciaDTO historialIncidenciaDTO);
    List<HistorialIncidenciaDTO> listar();
    List<HistorialIncidenciaDTO> buscarPorIncidenciaOrdenadaRecientes(Long incidenciaId);
    List<HistorialIncidenciaDTO> buscarPorUsuario(Long idUsuario);
}