package com.civix.controladores;

import com.civix.dtos.HistorialIncidenciaDTO;
import com.civix.servicios.HistorialIncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historia-incidencia")
public class HistorialIncidenciaControlador {
    @Autowired
    private HistorialIncidenciaServicio historialIncidenciaServicio;

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<HistorialIncidenciaDTO> registrar(@RequestBody HistorialIncidenciaDTO historialIncidenciaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(historialIncidenciaServicio.registrar(historialIncidenciaDTO));
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<HistorialIncidenciaDTO>> listar(){
        return ResponseEntity.ok(historialIncidenciaServicio.listar());
    }

    @GetMapping("/buscar-por-incidencia-recientes")
    @PreAuthorize("hasAnyRole('CIUDADANO','ADMINISTRADOR')")
    public ResponseEntity<List<HistorialIncidenciaDTO>> buscarPorIncidenciaOrdenadaRecientes(Long incidenciaId){
        return ResponseEntity.ok(historialIncidenciaServicio.buscarPorIncidenciaOrdenadaRecientes(incidenciaId));
    }

    @GetMapping("/buscar-por-usuario")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @autorizacion.esUsuarioActual(#idUsuario)")
    public ResponseEntity<List<HistorialIncidenciaDTO>> buscarPorUsuario(Long idUsuario){
        return ResponseEntity.ok(historialIncidenciaServicio.buscarPorUsuario(idUsuario));
    }
}