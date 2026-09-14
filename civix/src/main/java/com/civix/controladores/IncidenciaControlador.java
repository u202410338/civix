package com.civix.controladores;

import com.civix.entidades.Incidencia;
import com.civix.servicios.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidencia")
public class IncidenciaControlador {
    @Autowired
    private IncidenciaServicio incidenciaServicio;
    @PostMapping("/registrar")
    public Incidencia registrar(@RequestBody Incidencia incidencia){
        return incidenciaServicio.registrar(incidencia);
    }
    @GetMapping("/listar")
    public List<Incidencia> listar(){
        return incidenciaServicio.listar();
    }
    @GetMapping("/buscar-por-id")
    public Incidencia buscarPorId(@PathVariable Long id){
        return incidenciaServicio.buscarPorId(id);
    }
    @GetMapping("/buscar-por-estado-atencion")
    public List<Incidencia> buscarPorEstadoAtencion(@RequestBody String estadoAtencion){
        return incidenciaServicio.buscarPorEstadoAtencion(estadoAtencion);
    }
    @PutMapping("/actualizar")
    public Incidencia actualizar(@RequestBody Incidencia incidencia){
        return incidenciaServicio.actualizar(incidencia);
    }
    @DeleteMapping("/eliminar")
    public void eliminar(@RequestBody Long id){
        incidenciaServicio.eliminar(id);
    }
}
