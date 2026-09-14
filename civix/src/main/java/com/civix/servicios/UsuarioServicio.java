package com.civix.servicios;

import com.civix.entidades.Usuario;
import com.civix.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Transactional
    public Usuario registrar(Usuario usuario){
        UsuarioServicio.log.info("Insertando usuario: {}", usuario.getCorreo());
        return usuarioRepositorio.save(usuario);
    }
    public List<Usuario> listar(){
        return usuarioRepositorio.findAll();
    }
    public Usuario buscarPorId(Long id){
        return usuarioRepositorio.findById(id).orElse(null);
    }
    public Optional<Usuario> buscarPorCorreo(String correo){
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
