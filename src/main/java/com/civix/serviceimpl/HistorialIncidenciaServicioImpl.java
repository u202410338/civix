package com.civix.serviceimpl;

import com.civix.dtos.HistorialIncidenciaDTO;
import com.civix.entidades.HistorialIncidencia;
import com.civix.entidades.Incidencia;
import com.civix.entidades.Usuario;
import com.civix.exceptions.BusinessRuleException;
import com.civix.exceptions.ResourceNotFoundException;
import com.civix.repositorios.HistorialIncidenciaRepositorio;
import com.civix.repositorios.IncidenciaRepositorio;
import com.civix.repositorios.UsuarioRepositorio;
import com.civix.servicios.HistorialIncidenciaServicio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialIncidenciaServicioImpl implements HistorialIncidenciaServicio {
    @Autowired
    private HistorialIncidenciaRepositorio historialIncidenciaRepositorio;
    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public HistorialIncidenciaDTO registrar(HistorialIncidenciaDTO historialIncidenciaDTO) {
        if (historialIncidenciaDTO.getIdIncidencia() == null) {
            throw new BusinessRuleException("El id de la incidencia es obligatorio");
        }
        if (historialIncidenciaDTO.getIdUsuario() == null) {
            throw new BusinessRuleException("El id del usuario es obligatorio");
        }
        if (historialIncidenciaDTO.getEstadoNuevo() == null || historialIncidenciaDTO.getEstadoNuevo().isBlank()) {
            throw new BusinessRuleException("El estado nuevo es obligatorio");
        }
        Incidencia incidencia = incidenciaRepositorio.findById(historialIncidenciaDTO.getIdIncidencia())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la incidencia con el id: " + historialIncidenciaDTO.getIdIncidencia()));
        Usuario usuario = usuarioRepositorio.findById(historialIncidenciaDTO.getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el usuario con el id: " + historialIncidenciaDTO.getIdUsuario()));

        // Convertir el DTO a la entidad HistorialIncidencia
        HistorialIncidencia historial = modelMapper.map(historialIncidenciaDTO, HistorialIncidencia.class);
        historial.setIdHistorial(null);
        historial.setIncidencia(incidencia);
        historial.setUsuario(usuario);
        historial.setFechaCambio(LocalDateTime.now());
        historial = historialIncidenciaRepositorio.save(historial);
        return modelMapper.map(historial, HistorialIncidenciaDTO.class);
    }

    @Override
    public List<HistorialIncidenciaDTO> listar() {
        return historialIncidenciaRepositorio.findAll()
                .stream()
                .map(historial -> modelMapper.map(historial, HistorialIncidenciaDTO.class))
                .toList();
    }

    @Override
    public List<HistorialIncidenciaDTO> buscarPorIncidenciaOrdenadaRecientes(Long incidenciaId) {
        if (!incidenciaRepositorio.existsById(incidenciaId)) {
            throw new ResourceNotFoundException("No existe la incidencia con el id: " + incidenciaId);
        }
        return historialIncidenciaRepositorio.findByIncidencia_IdIncidenciaOrderByFechaCambioDesc(incidenciaId)
                .stream()
                .map(historial -> modelMapper.map(historial, HistorialIncidenciaDTO.class))
                .toList();
    }

    @Override
    public List<HistorialIncidenciaDTO> buscarPorUsuario(Long idUsuario) {
        if (!usuarioRepositorio.existsById(idUsuario)) {
            throw new ResourceNotFoundException("No existe el usuario con el id: " + idUsuario);
        }
        return historialIncidenciaRepositorio.findByUsuario_IdUsuarioOrderByFechaCambioDesc(idUsuario)
                .stream()
                .map(historial -> modelMapper.map(historial, HistorialIncidenciaDTO.class))
                .toList();
    }
}