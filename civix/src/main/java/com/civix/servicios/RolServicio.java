package com.civix.servicios;

import com.civix.entidades.Rol;
import com.civix.repositorios.RolRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicio {
    @Autowired
    private RolRepositorio rolRepositorio;

    @Transactional
    public Rol insertar(Rol rol){
        return rolRepositorio.save(rol);
    }

    public List<Rol> listar(){
        return rolRepositorio.findAll();
    }

    @Transactional
    public void eliminar(Long id){
        rolRepositorio.deleteById(id);
    }
}
