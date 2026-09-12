package com.civix.repositorios;

import com.civix.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepositorio extends JpaRepository<Incidencia,Long> {
    List<Incidencia> findByEstadoAtencion(String estadoAtencion);
}
