package com.civix.controladores;

import com.civix.dto.IncidenciaDTO;
import com.civix.entidades.Incidencia;
import com.civix.servicios.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidencia")
public class IncidenciaControlador {
    @Autowired
    private IncidenciaServicio incidenciaServicio;

    @PostMapping("/registrar")
    public Incidencia registrar(@RequestBody Incidencia incidencia) {
        return incidenciaServicio.registrar(incidencia);
    }
    @GetMapping("/listar")
    public List<Incidencia> listar() {
        return incidenciaServicio.listar();
    }
    @GetMapping("/buscar-por-id/{id}")
    public Incidencia buscarPorId(@PathVariable Long id) {
        return incidenciaServicio.buscarPorId(id);
    }
    @GetMapping("/estado/{estadoAtencion}")
    public ResponseEntity<List<IncidenciaDTO>> buscarPorEstadoAtencion(@PathVariable String estadoAtencion) {
        return ResponseEntity.ok(incidenciaServicio.buscarPorEstadoAtencion(estadoAtencion));
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
