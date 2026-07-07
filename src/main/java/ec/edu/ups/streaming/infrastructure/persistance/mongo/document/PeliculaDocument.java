package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PeliculaDocument extends ProduccionDocument {
    private Integer duracionMinutos;
    private List<String> premios;
    private Long numeroReproducciones;

    public PeliculaDocument() {
        this.setTipo("pelicula");
    }
}
