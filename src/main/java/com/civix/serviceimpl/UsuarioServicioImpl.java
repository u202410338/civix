package com.civix.serviceimpl;

import com.civix.dtos.UsuarioDTO;
import com.civix.dtos.UsuarioRegistroDTO;
import com.civix.entidades.Rol;
import com.civix.entidades.Usuario;
import com.civix.exceptions.BusinessRuleException;
import com.civix.exceptions.DuplicateResourceException;
import com.civix.exceptions.ResourceNotFoundException;
import com.civix.repositorios.RolRepositorio;
import com.civix.repositorios.UsuarioRepositorio;
import com.civix.servicios.UsuarioServicio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UsuarioServicioImpl implements UsuarioServicio {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public UsuarioDTO registrar(UsuarioRegistroDTO usuarioRegistroDTO) {
        log.info("Registrando usuario: {}", usuarioRegistroDTO.getCorreo());
        validarRegistro(usuarioRegistroDTO);

        // El registro público siempre crea ciudadanos
        Rol rolCiudadano = rolRepositorio.findByNombreRol("CIUDADANO")
                .orElseThrow(() -> new ResourceNotFoundException("No existe el rol CIUDADANO"));

        // Convertir el DTO a la entidad Usuario
        Usuario usuario = modelMapper.map(usuarioRegistroDTO, Usuario.class);
        usuario.setIdUsuario(null);
        usuario.setPasswordHash(passwordEncoder.encode(usuarioRegistroDTO.getPassword()));
        usuario.setRol(rolCiudadano);
        usuario.setEstado(true);
        usuario.setFechaRegistro(LocalDateTime.now());

        usuario = usuarioRepositorio.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public List<UsuarioDTO> listar() {
        return usuarioRepositorio.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .toList();
    }

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No existe el usuario con el id: " + id));
    }

    @Override
    public UsuarioDTO buscarPorCorreo(String correo) {
        return usuarioRepositorio.findByCorreo(correo)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el correo: " + correo));
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        if (!usuarioRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("No existe el usuario con el id: " + id);
        }
        usuarioRepositorio.deleteById(id);
    }

    // Validaciones del registro (formato y unicidad)
    private void validarRegistro(UsuarioRegistroDTO dto) {
        if (esVacio(dto.getNombre())) {
            throw new BusinessRuleException("El nombre es obligatorio");
        }
        if (esVacio(dto.getApellido())) {
            throw new BusinessRuleException("El apellido es obligatorio");
        }
        if (esVacio(dto.getCorreo()) || !dto.getCorreo().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new BusinessRuleException("El correo es obligatorio y debe tener un formato válido");
        }
        if (esVacio(dto.getDni()) || !dto.getDni().matches("\\d{8}")) {
            throw new BusinessRuleException("El DNI debe tener exactamente 8 dígitos");
        }
        if (esVacio(dto.getPassword()) || dto.getPassword().length() < 8 || dto.getPassword().length() > 72) {
            throw new BusinessRuleException("La contraseña debe tener entre 8 y 72 caracteres");
        }
        if (usuarioRepositorio.existsByCorreo(dto.getCorreo())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + dto.getCorreo());
        }
        if (usuarioRepositorio.existsByDni(dto.getDni())) {
            throw new DuplicateResourceException("Ya existe un usuario con el DNI: " + dto.getDni());
        }
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.isBlank();
    }
}