package ec.edu.ups.streaming.application.service;

import ec.edu.ups.streaming.application.service.dto.ActorParticipacionReporte;
import ec.edu.ups.streaming.domain.model.Produccion;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.ProduccionDocument;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.mapper.ProduccionMapper;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.repository.ProduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProduccionService {

    @Autowired
    private ProduccionRepository repository;

    @Autowired
    private ProduccionMapper mapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    // ---------- CRUD (exclusivo para administradores; la restricción de rol se aplica en el controller/seguridad) ----------

    public Produccion guardar(Produccion produccion) {
        ProduccionDocument doc = mapper.toDocument(produccion);
        ProduccionDocument guardado = repository.save(doc);
        return mapper.toDomain(guardado);
    }

    public Produccion editar(String id, Produccion produccionActualizada) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producción no encontrada con id: " + id);
        }
        produccionActualizada.setId(id); // aseguramos que actualiza el mismo documento, no crea uno nuevo
        ProduccionDocument doc = mapper.toDocument(produccionActualizada);
        ProduccionDocument actualizado = repository.save(doc);
        return mapper.toDomain(actualizado);
    }

    public void eliminar(String id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Producción no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    // ---------- Consultas (públicas) ----------

    public Produccion buscarPorId(String id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Producción no encontrada con id: " + id));
    }

    public List<Produccion> listarTodas() {
        return mapper.toDomainList(repository.findAll());
    }

    public List<Produccion> buscarPorTitulo(String nombre) {
        return mapper.toDomainList(repository.findByNombreContainingIgnoreCase(nombre));
    }

    public List<Produccion> buscarPorGenero(String genero) {
        return mapper.toDomainList(repository.findByGenerosContaining(genero));
    }

    // Recibe el año como número (ej. 2023) y arma el rango de fechas internamente
    public List<Produccion> buscarPorAnio(int anio) {
        Calendar inicio = Calendar.getInstance();
        inicio.clear();
        inicio.set(anio, Calendar.JANUARY, 1);

        Calendar fin = Calendar.getInstance();
        fin.clear();
        fin.set(anio, Calendar.DECEMBER, 31, 23, 59, 59);

        Date desde = inicio.getTime();
        Date hasta = fin.getTime();

        return mapper.toDomainList(repository.findByFechaEstrenoBetween(desde, hasta));
    }

    // ---------- Reportes solicitados ----------

    public List<Produccion> buscarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) {
            throw new RuntimeException("La fecha desde no puede ser mayor que la fecha hasta.");
        }

        ZoneId zona = ZoneId.systemDefault();

        Date fechaDesde = Date.from(desde.atStartOfDay(zona).toInstant());
        Date fechaHasta = Date.from(hasta.atTime(LocalTime.MAX).atZone(zona).toInstant());

        return mapper.toDomainList(repository.findByFechaEstrenoBetween(fechaDesde, fechaHasta));
    }

    public List<Produccion> produccionesConMasReproducciones(int limite) {
        if (limite <= 0) {
            limite = 10;
        }

        return mapper.toDomainList(
                repository.buscarProduccionesConMasReproducciones(PageRequest.of(0, limite))
        );
    }

    public List<Produccion> produccionesPorActor(String nombreActor) {
        return mapper.toDomainList(
                repository.findByActoresPrincipalesNombreCompletoContainingIgnoreCase(nombreActor)
        );
    }

    public List<ActorParticipacionReporte> actoresConMasParticipaciones(int limite) {
        if (limite <= 0) {
            limite = 10;
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("actoresPrincipales"),
                Aggregation.group("actoresPrincipales.nombreCompleto")
                        .count()
                        .as("participaciones"),
                Aggregation.project("participaciones")
                        .and("_id")
                        .as("actor"),
                Aggregation.sort(Sort.Direction.DESC, "participaciones"),
                Aggregation.limit(limite)
        );

        return mongoTemplate
                .aggregate(aggregation, "producciones", ActorParticipacionReporte.class)
                .getMappedResults();
    }

    public List<Produccion> listarPorTipo(String tipo) {
        // repository.findByTipo nos trae los documentos, y toDomainList los convierte al dominio polimórfico
        return mapper.toDomainList(repository.findByTipo(tipo));
    }
}