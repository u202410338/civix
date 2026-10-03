package com.civix.controladores;

import com.civix.dtos.EvidenciaDTO;
import com.civix.servicios.EvidenciaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evidencia")
public class EvidenciaControlador {
    @Autowired
    private EvidenciaServicio evidenciaServicio;

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('CIUDADANO')")
    public ResponseEntity<EvidenciaDTO> registrar(@RequestBody EvidenciaDTO evidenciaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(evidenciaServicio.registrar(evidenciaDTO));
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<EvidenciaDTO>> listar(){
        return ResponseEntity.ok(evidenciaServicio.listar());
    }

    @GetMapping("/buscar-por-incidencia")
    @PreAuthorize("hasAnyRole('CIUDADANO','ADMINISTRADOR')")
    public ResponseEntity<List<EvidenciaDTO>> buscarPorIncidencia(Long idIncidencia){
        return ResponseEntity.ok(evidenciaServicio.buscarPorIncidencia(idIncidencia));
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(Long id){
        evidenciaServicio.eliminar(id);
    }
}