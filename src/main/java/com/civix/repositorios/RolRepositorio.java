package com.civix.repositorios;

import com.civix.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RolRepositorio extends JpaRepository<Rol,Long> {
    Optional<Rol> findByNombreRol(String nombreRol);
}
