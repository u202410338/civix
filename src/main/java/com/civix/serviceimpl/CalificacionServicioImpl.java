package com.civix.serviceimpl;

import com.civix.dtos.CalificacionDTO;
import com.civix.entidades.Calificacion;
import com.civix.entidades.Incidencia;
import com.civix.exceptions.BusinessRuleException;
import com.civix.exceptions.DuplicateResourceException;
import com.civix.exceptions.ResourceNotFoundException;
import com.civix.repositorios.CalificacionRepositorio;
import com.civix.repositorios.IncidenciaRepositorio;
import com.civix.servicios.CalificacionServicio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalificacionServicioImpl implements CalificacionServicio {
    @Autowired
    private CalificacionRepositorio calificacionRepositorio;
    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public CalificacionDTO registrar(CalificacionDTO calificacionDTO) {
        if (calificacionDTO.getIdIncidencia() == null) {
            throw new BusinessRuleException("El id de la incidencia es obligatorio");
        }
        Integer puntuacion = calificacionDTO.getPuntuacion();
        if (puntuacion == null || puntuacion < 1 || puntuacion > 5) {
            throw new BusinessRuleException("La puntuación debe ser un número entero entre 1 y 5");
        }
        Incidencia incidencia = incidenciaRepositorio.findById(calificacionDTO.getIdIncidencia())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la incidencia con el id: " + calificacionDTO.getIdIncidencia()));
        if (!"ATENDIDO".equals(incidencia.getEstadoAtencion())) {
            throw new BusinessRuleException("Solo se puede calificar una incidencia en estado ATENDIDO");
        }
        if (calificacionRepositorio.existsByIncidencia_IdIncidencia(incidencia.getIdIncidencia())) {
            throw new DuplicateResourceException("La incidencia ya tiene una calificación registrada");
        }

        // Convertir el DTO a la entidad Calificacion
        Calificacion calificacion = modelMapper.map(calificacionDTO, Calificacion.class);
        calificacion.setIdCalificacion(null);
        calificacion.setIncidencia(incidencia);
        calificacion.setFechaCalificacion(LocalDateTime.now());
        calificacion = calificacionRepositorio.save(calificacion);
        return modelMapper.map(calificacion, CalificacionDTO.class);
    }

    @Override
    public List<CalificacionDTO> listar() {
        return calificacionRepositorio.findAll()
                .stream()
                .map(calificacion -> modelMapper.map(calificacion, CalificacionDTO.class))
                .toList();
    }

    @Override
    public CalificacionDTO buscarPorId(Long id) {
        return calificacionRepositorio.findById(id)
                .map(calificacion -> modelMapper.map(calificacion, CalificacionDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No existe la calificación con el id: " + id));
    }
}