package com.civix.controladores;

import com.civix.dtos.IncidenciaActualizarDTO;
import com.civix.dtos.IncidenciaDTO;
import com.civix.dtos.IncidenciaRegistroDTO;
import com.civix.servicios.IncidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidencia")
public class IncidenciaControlador {
    @Autowired
    private IncidenciaServicio incidenciaServicio;

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('CIUDADANO')")
    public ResponseEntity<IncidenciaDTO> registrar(@RequestBody IncidenciaRegistroDTO incidenciaRegistroDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(incidenciaServicio.registrar(incidenciaRegistroDTO));
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('CIUDADANO','ADMINISTRADOR')")
    public ResponseEntity<List<IncidenciaDTO>> listar() {
        return ResponseEntity.ok(incidenciaServicio.listar());
    }

    @GetMapping("/buscar-por-id/{id}")
    @PreAuthorize("hasAnyRole('CIUDADANO','ADMINISTRADOR')")
    public ResponseEntity<IncidenciaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(incidenciaServicio.buscarPorId(id));
    }

    @GetMapping("/estado/{estadoAtencion}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<IncidenciaDTO>> buscarPorEstadoAtencion(@PathVariable String estadoAtencion) {
        return ResponseEntity.ok(incidenciaServicio.buscarPorEstadoAtencion(estadoAtencion));
    }

    @PutMapping("/actualizar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<IncidenciaDTO> actualizar(@RequestBody IncidenciaActualizarDTO incidenciaActualizarDTO,
                                                    Authentication authentication){
        return ResponseEntity.ok(incidenciaServicio.actualizar(incidenciaActualizarDTO, authentication.getName()));
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(@RequestBody Long id){
        incidenciaServicio.eliminar(id);
    }
}