package com.civix.servicios;

import com.civix.dto.IncidenciaDTO;
import com.civix.entidades.Incidencia;
import com.civix.repositorios.IncidenciaRepositorio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IncidenciaServicio {
    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public Incidencia registrar(Incidencia incidencia){
        log.info("Registrando Incidencia: {}", incidencia.getTitulo());
        if(incidencia.getFechaRegistro() == null){
            incidencia.setFechaRegistro(LocalDateTime.now());
        }
        return incidenciaRepositorio.save(incidencia);
    }
    public List<Incidencia> listar(){
        return incidenciaRepositorio.findAll();
    }

    @Transactional
    public Incidencia buscarPorId(Long id) {
        return incidenciaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la incidencia con el id: " + id));
    }

    public List<IncidenciaDTO> buscarPorEstadoAtencion(String estadoAtencion) {
        return incidenciaRepositorio.findByEstadoAtencion(estadoAtencion).stream()
                .map(entity -> modelMapper.map(entity, IncidenciaDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public Incidencia actualizar(Incidencia incidencia){
        if(incidenciaRepositorio.existsById(incidencia.getIdIncidencia())){
            return incidenciaRepositorio.save(incidencia);
        }
        return null;
    }
    @Transactional
    public void eliminar(Long id){
        if(!incidenciaRepositorio.existsById(id)){
            throw new RuntimeException("No existe la incidencia con el id: "+id);
        }
        incidenciaRepositorio.deleteById(id);
    }
}
