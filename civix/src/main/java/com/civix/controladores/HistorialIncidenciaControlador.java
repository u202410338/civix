package com.civix.controladores;

import com.civix.entidades.HistorialIncidencia;
import com.civix.servicios.HistorialIncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historia-incidencia")
public class HistorialIncidenciaControlador {
    @Autowired
    private HistorialIncidenciaServicio historialIncidenciaServicio;
    @PostMapping("/registrar")
    public HistorialIncidencia registrar(@RequestBody HistorialIncidencia historialIncidencia){
        return historialIncidenciaServicio.registrar(historialIncidencia);
    }
    @GetMapping("/listar")
    public List<HistorialIncidencia> listar(){
        return historialIncidenciaServicio.listar();
    }
    @GetMapping("/buscar-por-incidencia-recientes")
    public List<HistorialIncidencia> buscarPorIncidenciaOrdenadaRecientes(Long incidenciaId){
        return historialIncidenciaServicio.buscarPorIncidenciaOrdenadaRecientes(incidenciaId);
    }
    @GetMapping("/buscar-por-usuario")
    public List<HistorialIncidencia> buscarPorUsuario(Long idUsuario){
        return historialIncidenciaServicio.buscarPorUsuario(idUsuario);
    }
}
