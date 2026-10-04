package com.civix.servicios;

import com.civix.dtos.IncidenciaActualizarDTO;
import com.civix.dtos.IncidenciaDTO;
import com.civix.dtos.IncidenciaRegistroDTO;

import java.util.List;

public interface IncidenciaServicio {
    IncidenciaDTO registrar(IncidenciaRegistroDTO incidenciaRegistroDTO);
    IncidenciaDTO registrar(IncidenciaRegistroDTO incidenciaRegistroDTO, String correoUsuario);
    List<IncidenciaDTO> listar();
    List<IncidenciaDTO> listarMisReportes(String correoUsuario);
    IncidenciaDTO buscarPorId(Long id);
    List<IncidenciaDTO> buscarPorEstadoAtencion(String estadoAtencion);
    IncidenciaDTO actualizar(IncidenciaActualizarDTO incidenciaActualizarDTO, String correoUsuario);
    void eliminar(Long id);
}