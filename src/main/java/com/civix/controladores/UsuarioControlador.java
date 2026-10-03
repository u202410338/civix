package com.civix.controladores;

import com.civix.dtos.UsuarioDTO;
import com.civix.dtos.UsuarioRegistroDTO;
import com.civix.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioControlador {
    @Autowired
    private UsuarioServicio usuarioServicio;

    // Público (configurado en SecurityConfig)
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> registrar(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServicio.registrar(usuarioRegistroDTO));
    }

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<List<UsuarioDTO>> listar(){
        return ResponseEntity.ok(usuarioServicio.listar());
    }

    @GetMapping("/buscar-por-id")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @autorizacion.esUsuarioActual(#id)")
    public ResponseEntity<UsuarioDTO> buscarPorId(Long id){
        return ResponseEntity.ok(usuarioServicio.buscarPorId(id));
    }

    @GetMapping("/buscar-por-correo")
    @PreAuthorize("hasRole('ADMINISTRADOR') or @autorizacion.esCorreoActual(#correo)")
    public ResponseEntity<UsuarioDTO> buscarPorCorreo(String correo){
        return ResponseEntity.ok(usuarioServicio.buscarPorCorreo(correo));
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public void eliminar(Long id){
        usuarioServicio.eliminar(id);
    }
}