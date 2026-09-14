package com.civix.controladores;

import com.civix.entidades.Calificacion;
import com.civix.servicios.CalificacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calificacion")
public class CalificacionControlador {
    @Autowired
    private CalificacionServicio calificacionServicio;
    @PostMapping("/registrar")
    public Calificacion registrar(@RequestBody Calificacion calificacion){
        return calificacionServicio.registrar(calificacion);
    }
    @GetMapping("/listar")
    public List<Calificacion> listar(){
        return calificacionServicio.listar();
    }
    @GetMapping("/buscar-por-id")
    public Calificacion buscarPorId(Long id){
        return calificacionServicio.buscarPorId(id);
    }
}
