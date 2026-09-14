package com.civix.controladores;

import com.civix.entidades.Evidencia;
import com.civix.servicios.EvidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evidencia")
public class EvidenciaControlador {
    @Autowired
    private EvidenciaServicio evidenciaServicio;
    @PostMapping("/registrar")
    public Evidencia registrar(@RequestBody Evidencia evidencia){
        return evidenciaServicio.registrar(evidencia);
    }
    @GetMapping("/listar")
    public List<Evidencia> listar(){
        return evidenciaServicio.listar();
    }
    @GetMapping("/buscar-por-incidencia")
    public List<Evidencia> buscarPorIncidencia(Long idIncidencia){
        return evidenciaServicio.buscarPorIncidencia(idIncidencia);
    }
    @DeleteMapping("/eliminar")
    public void eliminar(Long id){
        evidenciaServicio.eliminar(id);
    }
}
