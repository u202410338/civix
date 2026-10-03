package com.civix.servicios;

import com.civix.dtos.UsuarioDTO;
import com.civix.dtos.UsuarioRegistroDTO;

import java.util.List;

public interface UsuarioServicio {
    UsuarioDTO registrar(UsuarioRegistroDTO usuarioRegistroDTO);
    List<UsuarioDTO> listar();
    UsuarioDTO buscarPorId(Long id);
    UsuarioDTO buscarPorCorreo(String correo);
    void eliminar(Long id);
}