package com.civix.serviceimpl;

import com.civix.dtos.RolDTO;
import com.civix.entidades.Rol;
import com.civix.exceptions.BusinessRuleException;
import com.civix.exceptions.ResourceNotFoundException;
import com.civix.repositorios.RolRepositorio;
import com.civix.servicios.RolServicio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicioImpl implements RolServicio {
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public RolDTO registrar(RolDTO rolDTO) {
        if (rolDTO.getNombreRol() == null || !rolDTO.getNombreRol().matches("^[A-Z0-9_]+$")) {
            throw new BusinessRuleException("El nombre del rol es obligatorio, en mayúsculas y sin espacios");
        }
        // Convertir el DTO a la entidad Rol
        Rol rol = modelMapper.map(rolDTO, Rol.class);
        rol.setIdRol(null); // evita sobrescribir un rol existente
        rol = rolRepositorio.save(rol);
        return modelMapper.map(rol, RolDTO.class);
    }

    @Override
    public List<RolDTO> listar() {
        return rolRepositorio.findAll()
                .stream()
                .map(rol -> modelMapper.map(rol, RolDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        if (!rolRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("No existe el rol con el id: " + id);
        }
        rolRepositorio.deleteById(id);
    }
}