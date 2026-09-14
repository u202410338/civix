package com.civix.servicios;

import com.civix.entidades.Calificacion;
import com.civix.repositorios.CalificacionRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalificacionServicio {
    @Autowired
    private CalificacionRepositorio calificacionRepositorio;
    @Transactional
    public Calificacion registrar(Calificacion calificacion){
        if(calificacion.getFechaCalificacion()==null){
            calificacion.setFechaCalificacion(LocalDateTime.now());
        }
        return calificacionRepositorio.save(calificacion);
    }
    public List<Calificacion> listar(){
        return calificacionRepositorio.findAll();
    }
    public Calificacion buscarPorId(Long id){
        return calificacionRepositorio.findById(id).orElse(null);
    }
}
