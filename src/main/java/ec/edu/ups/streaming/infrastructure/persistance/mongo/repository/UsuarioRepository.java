package ec.edu.ups.streaming.infrastructure.persistance.mongo.repository;

import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.UsuarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<UsuarioDocument, String> {
    // Esto es vital para tu login: buscar usuario por email
    Optional<UsuarioDocument> findByEmail(String email);
}