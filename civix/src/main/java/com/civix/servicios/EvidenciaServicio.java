package com.civix.servicios;

import com.civix.entidades.Evidencia;
import com.civix.repositorios.EvidenciaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvidenciaServicio {
    @Autowired
    private EvidenciaRepositorio evidenciaRepositorio;
    @Transactional
    public Evidencia registrar(Evidencia evidencia){
        return evidenciaRepositorio.save(evidencia);
    }
    public List<Evidencia> listar(){
        return evidenciaRepositorio.findAll();
    }
    public List<Evidencia> buscarPorIncidencia(Long idIncidencia){
        return evidenciaRepositorio.findByIncidencia_IdIncidencia(idIncidencia);
    }
    @Transactional
    public void eliminar(Long id){
        evidenciaRepositorio.deleteById(id);
    }
}
