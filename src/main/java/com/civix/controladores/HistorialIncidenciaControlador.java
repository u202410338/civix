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
@RequestMapping({"/historial-incidencia", "/historia-incidencia"})
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

    @GetMapping({"/buscar-por-incidencia", "/buscar-por-incidencia-recientes"})
    @PreAuthorize("hasAnyRole('CIUDADANO','ADMINISTRADOR')")
    public ResponseEntity<List<HistorialIncidenciaDTO>> buscarPorIncidenciaOrdenadaRecientes(
            @RequestParam(value = "id_incidencia", required = false) Long id_incidencia,
            @RequestParam(value = "incidenciaId", required = false) Long incidenciaId) {
        Long id = id_incidencia != null ? id_incidencia : incidenciaId;
        return ResponseEntity.ok(historialIncidenciaServicio.buscarPorIncidenciaOrdenadaRecientes(id));
    }

    @GetMapping("/buscar-por-usuario")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @autorizacion.esUsuarioActual(#idUsuario != null ? #idUsuario : #id_usuario)")
    public ResponseEntity<List<HistorialIncidenciaDTO>> buscarPorUsuario(
            @RequestParam(value = "id_usuario", required = false) Long id_usuario,
            @RequestParam(value = "idUsuario", required = false) Long idUsuario) {
        Long id = idUsuario != null ? idUsuario : id_usuario;
        return ResponseEntity.ok(historialIncidenciaServicio.buscarPorUsuario(id));
    }
}