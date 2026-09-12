package com.civix.servicios;

import com.civix.entidades.Usuario;
import com.civix.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public Usuario insert(Usuario usuario){
        UsuarioServicio.log.info("Insertando usuario: {}", usuario.getCorreo());
        return usuarioRepositorio.save(usuario);
    }

    public List<Usuario> listar(){
        return usuarioRepositorio.findAll();
    }

    public Usuario obtenerPorId(Long id){
        return usuarioRepositorio.findById(id).orElse(null);
    }

    public Usuario obtenerPorCorreo(String correo){
        return usuarioRepositorio.findByCorreo(correo);
    }

    @Transactional
    public void eliminar(Long id){
        if(!usuarioRepositorio.existsById(id)){
            throw new RuntimeException("No existe el usuario con el id: "+id);
        }
        usuarioRepositorio.deleteById(id);
    }
}
