package ec.edu.ups.streaming.infrastructure.persistance.mongo.repository;


import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.ProduccionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProduccionRepository extends MongoRepository<ProduccionDocument, String> {

    // Búsqueda por título (coincidencia parcial, ignora mayúsculas/minúsculas)
    List<ProduccionDocument> findByNombreContainingIgnoreCase(String nombre);

    // Búsqueda por tipo: "pelicula" o "serie"
    List<ProduccionDocument> findByTipo(String tipo);

    // Búsqueda por rango de fechas de estreno (el servicio arma el rango a partir del año)
    List<ProduccionDocument> findByFechaEstrenoBetween(Date desde, Date hasta);

    // Búsqueda por coincidencia de género (requiere el nombre exacto del género, ej. "Drama")
    List<ProduccionDocument> findByGenerosContaining(String genero);
}