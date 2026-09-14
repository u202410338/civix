package com.civix.servicios;

import com.civix.entidades.Categoria;
import com.civix.repositorios.CategoriaRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicio {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    @Transactional
    public Categoria registrar(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }
    public List<Categoria> listar() {
        return categoriaRepositorio.findAll();
    }
    public Categoria buscarPorId(Long id) {
        return categoriaRepositorio.findById(id).orElse(null);
    }
    @Transactional
    public Categoria actualizar(Categoria categoria) {
        if (categoriaRepositorio.existsById(categoria.getIdCategoria())) {
            return categoriaRepositorio.save(categoria);
        }
        return null;
    }
}
