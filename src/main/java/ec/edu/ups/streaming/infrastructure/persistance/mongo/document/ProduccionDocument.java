package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "producciones") // Nombre de la colección en MongoDB
public abstract class ProduccionDocument {
    @Id
    private String id; // Mongo usará esto como el ObjectId (_id)
    private String tipo; // "pelicula" o "serie"
    private String nombre;
    private List<String> generos;
    private Date fechaEstreno;
    private List<ActorDocument> actoresPrincipales;
}