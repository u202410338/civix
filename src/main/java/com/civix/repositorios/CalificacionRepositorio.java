package com.civix.repositorios;

import com.civix.entidades.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion,Long> {
    boolean existsByIncidencia_IdIncidencia(Long idIncidencia);
    Optional<Calificacion> findByIncidencia_IdIncidencia(Long idIncidencia);
}
