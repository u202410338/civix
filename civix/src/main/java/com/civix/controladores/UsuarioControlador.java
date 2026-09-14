package com.civix.controladores;

import com.civix.entidades.Usuario;
import com.civix.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;
    @PostMapping("/registrar")
    public Usuario registrar(@RequestBody Usuario usuario){
        return usuarioServicio.registrar(usuario);
    }
    @GetMapping("/listar")
    public List<Usuario> listar(){
        return usuarioServicio.listar();
    }
    @GetMapping("/buscar-por-id")
    public Usuario buscarPorId(Long id){
        return usuarioServicio.buscarPorId(id);
    }
    @GetMapping("/buscar-por-correo")
    public Optional<Usuario> buscarPorCorreo(String correo){
        return usuarioServicio.buscarPorCorreo(correo);
    }
    @DeleteMapping("/eliminar")
    public void eliminar(Long id){
        usuarioServicio.eliminar(id);
    }
}
