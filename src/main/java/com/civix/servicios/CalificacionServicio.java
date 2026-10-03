package com.civix.servicios;

import com.civix.dtos.CalificacionDTO;

import java.util.List;

public interface CalificacionServicio {
    CalificacionDTO registrar(CalificacionDTO calificacionDTO);
    List<CalificacionDTO> listar();
    CalificacionDTO buscarPorId(Long id);
}