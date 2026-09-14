package com.civix.controladores;

import com.civix.entidades.Rol;
import com.civix.servicios.RolServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol")
public class RolControlador {
    @Autowired
    private RolServicio rolServicio;
    @PostMapping("/registrar")
    public Rol registrar(@RequestBody Rol rol){
        return rolServicio.registrar(rol);
    }
    @GetMapping("/listar")
    public List<Rol> listar(){
        return rolServicio.listar();
    }
    @DeleteMapping("/eliminar")
    public void eliminar(Long id){
        rolServicio.eliminar(id);
    }
}
