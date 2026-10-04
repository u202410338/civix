package com.civix.servicios;

import com.civix.dtos.CalificacionDTO;

import java.util.List;

public interface CalificacionServicio {
    CalificacionDTO registrar(CalificacionDTO calificacionDTO);
    CalificacionDTO registrar(CalificacionDTO calificacionDTO, String correoUsuario);
    List<CalificacionDTO> listar();
    CalificacionDTO buscarPorId(Long id);
    CalificacionDTO buscarPorIncidencia(Long idIncidencia);
}