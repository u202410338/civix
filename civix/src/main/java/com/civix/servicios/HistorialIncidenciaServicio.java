package com.civix.servicios;

import com.civix.entidades.HistorialIncidencia;
import com.civix.repositorios.HistorialIncidenciaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialIncidenciaServicio {
    @Autowired
    private HistorialIncidenciaRepositorio historialIncidenciaRepositorio;
    @Transactional
    public HistorialIncidencia registrar(HistorialIncidencia historialIncidencia){
        if(historialIncidencia.getFechaCambio()==null){
            historialIncidencia.setFechaCambio(LocalDateTime.now());
        }
        return historialIncidenciaRepositorio.save(historialIncidencia);
    }
    public List<HistorialIncidencia> listar(){
        return historialIncidenciaRepositorio.findAll();
    }
    public List<HistorialIncidencia> buscarPorIncidenciaOrdenadaRecientes(Long incidenciaId){
        return historialIncidenciaRepositorio.findByIncidencia_IdIncidenciaOrderByFechaCambioDesc(incidenciaId);
    }
    public List<HistorialIncidencia> buscarPorUsuario(Long idUsuario){
        return historialIncidenciaRepositorio.findByUsuario_IdUsuario(idUsuario);
    }
}
