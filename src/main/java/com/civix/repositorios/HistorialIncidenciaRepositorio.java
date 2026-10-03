package com.civix.repositorios;

import com.civix.entidades.HistorialIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialIncidenciaRepositorio extends JpaRepository<HistorialIncidencia,Long> {
    List<HistorialIncidencia> findByIncidencia_IdIncidenciaOrderByFechaCambioDesc(Long idIncidencia);
    List<HistorialIncidencia> findByUsuario_IdUsuario(Long idUsuario);
}
