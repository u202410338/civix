package com.civix.repositorios;

import com.civix.entidades.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepositorio extends JpaRepository<Incidencia,Long> {
    List<Incidencia> findByEstadoAtencion(String estadoAtencion);

    @Query("SELECT i FROM Incidencia i WHERE i.estadoAtencion = :estadoAtencion")
    List<Incidencia> buscarPorEstadoAtencionJPQL(@Param("estadoAtencion") String estadoAtencion);

    @Query("SELECT COUNT(i) FROM Incidencia i WHERE i.categoria.idCategoria = :idCategoria")
    Long contarPorCategoria(@Param("idCategoria") Long idCategoria);
}
