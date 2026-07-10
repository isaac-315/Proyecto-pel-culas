package ec.edu.ups.streaming.application.service;

import ec.edu.ups.streaming.domain.model.Produccion;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.ProduccionDocument;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.mapper.ProduccionMapper;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.repository.ProduccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProduccionService {

    @Autowired
    private ProduccionRepository repository;

    @Autowired
    private ProduccionMapper mapper;

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
}