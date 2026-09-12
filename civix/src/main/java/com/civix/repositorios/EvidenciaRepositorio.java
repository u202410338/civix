package com.civix.repositorios;

import com.civix.entidades.Evidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvidenciaRepositorio extends JpaRepository<Evidencia,Long> {
    List<Evidencia> findByIncidencia_IdIncidencia(Long incidencia);
}
