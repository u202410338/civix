package com.civix.servicios;

import com.civix.entidades.Evidencia;
import com.civix.entidades.Incidencia;
import com.civix.repositorios.EvidenciaRepositorio;
import com.civix.repositorios.IncidenciaRepositorio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvidenciaServicio {
    @Autowired
    private EvidenciaRepositorio evidenciaRepositorio;

    @Transactional
    public Evidencia guardar(Evidencia evidencia){
        return evidenciaRepositorio.save(evidencia);
    }

    public List<Evidencia> listar(){
        return evidenciaRepositorio.findAll();
    }

    public List<Evidencia> listarPorIncidencia(Long idIncidencia){
        return evidenciaRepositorio.findByIncidencia_IdIncidencia(idIncidencia);
    }

    @Transactional
    public void eliminar(Long id){
        evidenciaRepositorio.deleteById(id);
    }
}
