package com.civix.serviceimpl;

import com.civix.dtos.IncidenciaActualizarDTO;
import com.civix.dtos.IncidenciaDTO;
import com.civix.dtos.IncidenciaRegistroDTO;
import com.civix.entidades.Categoria;
import com.civix.entidades.HistorialIncidencia;
import com.civix.entidades.Incidencia;
import com.civix.entidades.Usuario;
import com.civix.exceptions.BusinessRuleException;
import com.civix.exceptions.ResourceNotFoundException;
import com.civix.repositorios.CategoriaRepositorio;
import com.civix.repositorios.HistorialIncidenciaRepositorio;
import com.civix.repositorios.IncidenciaRepositorio;
import com.civix.repositorios.UsuarioRepositorio;
import com.civix.servicios.IncidenciaServicio;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IncidenciaServicioImpl implements IncidenciaServicio {
    private static final Set<String> ESTADOS = Set.of("PENDIENTE", "EN_PROCESO", "ATENDIDO", "RECHAZADO");
    private static final Map<String, Set<String>> TRANSICIONES = Map.of(
            "PENDIENTE", Set.of("EN_PROCESO"),
            "EN_PROCESO", Set.of("ATENDIDO", "RECHAZADO"));

    @Autowired
    private IncidenciaRepositorio incidenciaRepositorio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;
    @Autowired
    private HistorialIncidenciaRepositorio historialIncidenciaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public IncidenciaDTO registrar(IncidenciaRegistroDTO incidenciaRegistroDTO) {
        return registrar(incidenciaRegistroDTO, null);
    }

    @Transactional
    @Override
    public IncidenciaDTO registrar(IncidenciaRegistroDTO incidenciaRegistroDTO, String correoUsuario) {
        log.info("Registrando Incidencia: {}", incidenciaRegistroDTO.getTitulo());
        validarRegistro(incidenciaRegistroDTO);

        Categoria categoria = categoriaRepositorio.findById(incidenciaRegistroDTO.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la categoría con el id: " + incidenciaRegistroDTO.getIdCategoria()));

        // Convertir el DTO a la entidad Incidencia (Sin FK a Usuario para evitar bucles)
        Incidencia incidencia = modelMapper.map(incidenciaRegistroDTO, Incidencia.class);
        LocalDateTime ahora = LocalDateTime.now();
        int horas = categoria.getHorasEstimadas() != null ? categoria.getHorasEstimadas() : 48;
        incidencia.setIdIncidencia(null);
        incidencia.setCategoria(categoria);
        incidencia.setEstadoAtencion("PENDIENTE");
        incidencia.setEsSugeridoIa(false);
        incidencia.setFechaRegistro(ahora);
        incidencia.setFechaLimiteEstimada(ahora.plusHours(horas));
        incidencia = incidenciaRepositorio.save(incidencia);

        // El código necesita el id generado
        incidencia.setCodigoIncidencia(
                String.format("INC-%d-%04d", ahora.getYear(), incidencia.getIdIncidencia()));
        incidencia = incidenciaRepositorio.save(incidencia);

        // Solución Gloria & Orihen: El ciudadano creador queda registrado en HistorialIncidencia si está autenticado
        if (correoUsuario != null && !correoUsuario.isBlank()) {
            Usuario ciudadano = usuarioRepositorio.findByCorreo(correoUsuario)
                    .orElseThrow(() -> new ResourceNotFoundException("No se pudo identificar al usuario autenticado"));

            HistorialIncidencia historialInicial = new HistorialIncidencia();
            historialInicial.setIncidencia(incidencia);
            historialInicial.setUsuario(ciudadano);
            historialInicial.setEstadoAnterior("REGISTRADO");
            historialInicial.setEstadoNuevo("PENDIENTE");
            historialInicial.setComentario("Incidencia registrada por el ciudadano.");
            historialInicial.setAreaAsignada(categoria.getNombre());
            historialInicial.setUsuarioAccion(ciudadano.getNombre() + " " + ciudadano.getApellido());
            historialInicial.setFechaCambio(ahora);
            historialIncidenciaRepositorio.save(historialInicial);
        }

        return modelMapper.map(incidencia, IncidenciaDTO.class);
    }

    @Override
    public List<IncidenciaDTO> listarMisReportes(String correoUsuario) {
        Usuario ciudadano = usuarioRepositorio.findByCorreo(correoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo identificar al usuario autenticado"));
        List<HistorialIncidencia> historiales = historialIncidenciaRepositorio
                .findByUsuario_IdUsuarioOrderByFechaCambioDesc(ciudadano.getIdUsuario());

        return historiales.stream()
                .map(HistorialIncidencia::getIncidencia)
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Incidencia::getIdIncidencia, i -> i, (existing, replacement) -> existing, LinkedHashMap::new),
                        m -> m.values().stream().map(i -> modelMapper.map(i, IncidenciaDTO.class)).toList()
                ));
    }

    @Override
    public List<IncidenciaDTO> listar() {
        return incidenciaRepositorio.findAll()
                .stream()
                .map(incidencia -> modelMapper.map(incidencia, IncidenciaDTO.class))
                .toList();
    }

    @Override
    public IncidenciaDTO buscarPorId(Long id) {
        return incidenciaRepositorio.findById(id)
                .map(incidencia -> modelMapper.map(incidencia, IncidenciaDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No existe la incidencia con el id: " + id));
    }

    @Override
    public List<IncidenciaDTO> buscarPorEstadoAtencion(String estadoAtencion) {
        String estado = estadoAtencion == null ? "" : estadoAtencion.trim().toUpperCase();
        if (!ESTADOS.contains(estado)) {
            throw new BusinessRuleException(
                    "Estado de atención no válido. Valores permitidos: PENDIENTE, EN_PROCESO, ATENDIDO, RECHAZADO");
        }
        return incidenciaRepositorio.findByEstadoAtencion(estado)
                .stream()
                .map(incidencia -> modelMapper.map(incidencia, IncidenciaDTO.class))
                .toList();
    }

    @Transactional
    @Override
    public IncidenciaDTO actualizar(IncidenciaActualizarDTO dto, String correoUsuario) {
        if (dto.getIdIncidencia() == null) {
            throw new BusinessRuleException("El id de la incidencia es obligatorio");
        }
        Incidencia incidencia = incidenciaRepositorio.findById(dto.getIdIncidencia())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la incidencia con el id: " + dto.getIdIncidencia()));

        // Solo se modifican los campos que llegan en la petición
        if (dto.getTitulo() != null) {
            incidencia.setTitulo(dto.getTitulo());
        }
        if (dto.getDescripcion() != null) {
            incidencia.setDescripcion(dto.getDescripcion());
        }
        if (dto.getPrioridad() != null) {
            incidencia.setPrioridad(dto.getPrioridad());
        }
        if (dto.getIdCategoria() != null) {
            Categoria categoria = categoriaRepositorio.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No existe la categoría con el id: " + dto.getIdCategoria()));
            incidencia.setCategoria(categoria);
        }

        // Cambio de estado: valida la transición y deja rastro en el historial
        String estadoAnterior = incidencia.getEstadoAtencion() != null ? incidencia.getEstadoAtencion() : "PENDIENTE";
        String estadoNuevo = dto.getEstadoAtencion() == null ? null : dto.getEstadoAtencion().trim().toUpperCase();
        if (estadoNuevo != null && !estadoNuevo.equals(estadoAnterior)) {
            validarCambioEstado(estadoAnterior, estadoNuevo, dto.getComentario());
            incidencia.setEstadoAtencion(estadoNuevo);
            registrarHistorial(incidencia, estadoAnterior, estadoNuevo, dto, correoUsuario);
        }

        incidencia = incidenciaRepositorio.save(incidencia);
        return modelMapper.map(incidencia, IncidenciaDTO.class);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        if (!incidenciaRepositorio.existsById(id)) {
            throw new ResourceNotFoundException("No existe la incidencia con el id: " + id);
        }
        incidenciaRepositorio.deleteById(id);
    }

    private void validarRegistro(IncidenciaRegistroDTO dto) {
        if (dto.getIdCategoria() == null) {
            throw new BusinessRuleException("La categoría es obligatoria");
        }
        if (esVacio(dto.getTitulo())) {
            throw new BusinessRuleException("El título es obligatorio");
        }
        if (esVacio(dto.getDescripcion())) {
            throw new BusinessRuleException("La descripción es obligatoria");
        }
        if (esVacio(dto.getPrioridad())) {
            throw new BusinessRuleException("La prioridad es obligatoria");
        }
        if (dto.getLatitud() == null || dto.getLongitud() == null) {
            throw new BusinessRuleException("La latitud y la longitud son obligatorias");
        }
        if (dto.getLatitud() < -90 || dto.getLatitud() > 90 || dto.getLongitud() < -180 || dto.getLongitud() > 180) {
            throw new BusinessRuleException("La latitud o la longitud están fuera de rango");
        }
    }

    // PENDIENTE -> EN_PROCESO -> ATENDIDO / RECHAZADO
    private void validarCambioEstado(String anterior, String nuevo, String comentario) {
        if (!ESTADOS.contains(nuevo)) {
            throw new BusinessRuleException("Estado de atención no válido: " + nuevo);
        }
        if (!TRANSICIONES.getOrDefault(anterior, Set.of()).contains(nuevo)) {
            throw new BusinessRuleException("Transición no permitida: " + anterior + " -> " + nuevo);
        }
        if ("ATENDIDO".equals(nuevo) && esVacio(comentario)) {
            throw new BusinessRuleException(
                    "La nota de resolución (comentario) es obligatoria al marcar la incidencia como ATENDIDO");
        }
    }

    private void registrarHistorial(Incidencia incidencia, String estadoAnterior, String estadoNuevo,
                                    IncidenciaActualizarDTO dto, String correoUsuario) {
        Usuario admin = usuarioRepositorio.findByCorreo(correoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("No se pudo identificar al usuario autenticado"));

        // Se obtiene el ciudadano creador desde el primer registro del historial de la incidencia
        List<HistorialIncidencia> previos = historialIncidenciaRepositorio
                .findByIncidencia_IdIncidenciaOrderByFechaCambioAsc(incidencia.getIdIncidencia());
        Usuario ciudadano = previos.isEmpty() ? admin : previos.get(0).getUsuario();

        HistorialIncidencia historial = new HistorialIncidencia();
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(estadoNuevo);
        historial.setComentario(esVacio(dto.getComentario())
                ? "Estado cambiado a " + estadoNuevo + "."
                : dto.getComentario());
        historial.setAreaAsignada(dto.getAreaAsignada());
        // Atributo adicional sin FK: la persona que modifica (el admin)
        historial.setUsuarioAccion(admin.getNombre() + " " + admin.getApellido());
        historial.setFechaCambio(LocalDateTime.now());
        historial.setIncidencia(incidencia);
        // FK id_usuario: se mantiene vinculado al ciudadano que creó la incidencia
        historial.setUsuario(ciudadano);
        historialIncidenciaRepositorio.save(historial);
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.isBlank();
    }
}