package com.civix.servicios;

import com.civix.entidades.Categoria;
import com.civix.entidades.Usuario;
import com.civix.repositorios.CategoriaRepositorio;
import com.civix.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicio {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Transactional
    public Categoria insertar(Categoria categoria) {
        return categoriaRepositorio.save(categoria);
    }

    public List<Categoria> listar() {
        return categoriaRepositorio.findAll();
    }

    public Categoria obtenerPorId(Long id) {
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
