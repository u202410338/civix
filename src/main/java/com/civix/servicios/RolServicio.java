package com.civix.servicios;

import com.civix.dtos.RolDTO;

import java.util.List;

public interface RolServicio {
    RolDTO registrar(RolDTO rolDTO);
    List<RolDTO> listar();
    void eliminar(Long id);
}