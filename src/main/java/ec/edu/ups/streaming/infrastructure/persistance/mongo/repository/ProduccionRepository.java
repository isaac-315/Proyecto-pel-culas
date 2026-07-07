package ec.edu.ups.streaming.infrastructure.persistance.mongo.repository;


import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.ProduccionDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionRepository extends MongoRepository<ProduccionDocument, String> {
}
