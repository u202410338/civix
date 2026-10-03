package com.civix.serviceimpl;

import com.civix.dtos.EvidenciaDTO;
import com.civix.entidades.Evidencia;
import com.civix.entidades.Incidencia;
import com.civix.exceptions.BusinessRuleException;
import com.civix.exceptions.ResourceNotFoundException;
import com.civix.repositorios.EvidenciaRepositorio;
import com.civix.repositorios.IncidenciaRepositorio;
import com.civix.servicios.EvidenciaServicio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvidenciaServicioImpl implements EvidenciaServicio {
    @Autowired
    private EvidenciaRepositorio evidenciaRepositorio;
    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public EvidenciaDTO registrar(EvidenciaDTO evidenciaDTO) {
        if (evidenciaDTO.getIdIncidencia() == null) {
            throw new BusinessRuleException("El id de la incidencia es obligatorio");
        }
        String url = evidenciaDTO.getUrlArchivo();
        if (url == null || url.isBlank()) {
            throw new BusinessRuleException("La URL del archivo es obligatoria");
        }
        if (!url.toLowerCase().matches(".+\\.(jpg|jpeg|png)(\\?.*)?$")) {
            throw new BusinessRuleException("Formatos permitidos: .jpg, .jpeg, .png");
        }
        Incidencia incidencia = incidenciaRepositorio.findById(evidenciaDTO.getIdIncidencia())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la incidencia con el id: " + evidenciaDTO.getIdIncidencia()));

        // Convertir el DTO a la entidad Evidencia
        Evidencia evidencia = modelMapper.map(evidenciaDTO, Evidencia.class);
        evidencia.setIdEvidencia(null);
        evidencia.setIncidencia(incidencia);
        evidencia.setFechaRegistro(LocalDateTime.now());
        if (evidencia.getTipoArchivo() == null) {
            evidencia.setTipoArchivo("IMAGE");
        }
        if (evidencia.getFase() == null) {
            evidencia.setFase("Inicial");
        }
        evidencia = evidenciaRepositorio.save(evidencia);
        return modelMapper.map(evidencia, EvidenciaDTO.class);
    }

    @Override
    public List<EvidenciaDTO> listar() {
        return evidenciaRepositorio.findAll()
                .stream()
                .map(evidencia -> modelMapper.map(evidencia, EvidenciaDTO.class))
                .toList();
    }

    @Override
    public List<EvidenciaDTO> buscarPorIncidencia(Long idIncidencia) {
        return evidenciaRepositorio.findByIncidencia_IdIncidencia(idIncidencia)
                .stream()
                .map(evidencia -> modelMapper.map(evidencia, EvidenciaDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        if (!evidenciaRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("No existe la evidencia con el id: " + id);
        }
        evidenciaRepositorio.deleteById(id);
    }
}