package com.civix.controladores;

import com.civix.dtos.CategoriaDTO;
import com.civix.servicios.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {
    @Autowired
    private CategoriaServicio categoriaServicio;

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<CategoriaDTO> registrar(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaServicio.registrar(categoriaDTO));
    }

    // Público (configurado en SecurityConfig)
    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(categoriaServicio.listar());
    }

    @GetMapping("/buscar-por-id")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<CategoriaDTO> buscarPorId(Long id) {
        return ResponseEntity.ok(categoriaServicio.buscarPorId(id));
    }

    @PutMapping("/actualizar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<CategoriaDTO> actualizar(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaServicio.actualizar(categoriaDTO));
    }
}