package com.civix.controladores;

import com.civix.dtos.RolDTO;
import com.civix.servicios.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class RolControlador {
    @Autowired
    private RolServicio rolServicio;

    @PostMapping("/registrar")
    public ResponseEntity<RolDTO> registrar(@RequestBody RolDTO rolDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(rolServicio.registrar(rolDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<RolDTO>> listar(){
        return ResponseEntity.ok(rolServicio.listar());
    }

    @DeleteMapping("/eliminar")
    public void eliminar(Long id){
        rolServicio.eliminar(id);
    }
}