package com.civix.servicios;

import com.civix.dtos.CategoriaDTO;

import java.util.List;

public interface CategoriaServicio {
    CategoriaDTO registrar(CategoriaDTO categoriaDTO);
    List<CategoriaDTO> listar();
    CategoriaDTO buscarPorId(Long id);
    CategoriaDTO actualizar(CategoriaDTO categoriaDTO);
}