package com.civix.serviceimpl;

import com.civix.dtos.CategoriaDTO;
import com.civix.entidades.Categoria;
import com.civix.exceptions.BusinessRuleException;
import com.civix.exceptions.ResourceNotFoundException;
import com.civix.repositorios.CategoriaRepositorio;
import com.civix.servicios.CategoriaServicio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicioImpl implements CategoriaServicio {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public CategoriaDTO registrar(CategoriaDTO categoriaDTO) {
        validar(categoriaDTO);
        // Convertir el DTO a la entidad Categoria
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
        categoria.setIdCategoria(null); // evita sobrescribir una categoría existente
        if (categoria.getEstado() == null) {
            categoria.setEstado(true);
        }
        categoria = categoriaRepositorio.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    @Override
    public List<CategoriaDTO> listar() {
        return categoriaRepositorio.findAll()
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .toList();
    }

    @Override
    public CategoriaDTO buscarPorId(Long id) {
        return categoriaRepositorio.findById(id)
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No existe la categoría con el id: " + id));
    }

    @Transactional
    @Override
    public CategoriaDTO actualizar(CategoriaDTO categoriaDTO) {
        if (categoriaDTO.getIdCategoria() == null) {
            throw new BusinessRuleException("El id de la categoría es obligatorio");
        }
        validar(categoriaDTO);
        return categoriaRepositorio.findById(categoriaDTO.getIdCategoria())
                .map(existente -> {
                    Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
                    if (categoria.getEstado() == null) {
                        categoria.setEstado(existente.getEstado());
                    }
                    return modelMapper.map(categoriaRepositorio.save(categoria), CategoriaDTO.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la categoría con el id: " + categoriaDTO.getIdCategoria()));
    }

    private void validar(CategoriaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new BusinessRuleException("El nombre de la categoría es obligatorio");
        }
        if (dto.getNombre().length() > 100) {
            throw new BusinessRuleException("El nombre no puede superar los 100 caracteres");
        }
        if (dto.getHorasEstimadas() != null && dto.getHorasEstimadas() < 1) {
            throw new BusinessRuleException("Las horas estimadas deben ser al menos 1");
        }
    }
}