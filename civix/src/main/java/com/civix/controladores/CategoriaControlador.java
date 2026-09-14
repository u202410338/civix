package com.civix.controladores;

import com.civix.entidades.Categoria;
import com.civix.servicios.CategoriaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {
    @Autowired
    private CategoriaServicio categoriaServicio;
    @PostMapping("/registrar")
    public Categoria registrar(@RequestBody Categoria categoria) {
        return categoriaServicio.registrar(categoria);
    }
    @GetMapping("/listar")
    public List<Categoria> listar() {
        return categoriaServicio.listar();
    }
    @GetMapping("/buscar-por-id")
    public Categoria buscarPorId(Long id) {
        return categoriaServicio.buscarPorId(id);
    }
    @PutMapping("/actualizar")
    public Categoria actualizar(@RequestBody Categoria categoria) {
        return categoriaServicio.actualizar(categoria);
    }
}
