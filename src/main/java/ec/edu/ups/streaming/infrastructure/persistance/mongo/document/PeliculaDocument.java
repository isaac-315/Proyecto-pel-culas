package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias; // <--- Importante
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // Necesario para Spring Data
@TypeAlias("pelicula") // <--- Esto se guardará en el campo _class de MongoDB
public class PeliculaDocument extends ProduccionDocument {
    private Integer duracionMinutos;
    private List<String> premios;
    private Long numeroReproducciones;

    public PeliculaDocument(Integer duracionMinutos, List<String> premios, Long numeroReproducciones) {
        this.setTipo("pelicula");
        this.duracionMinutos = duracionMinutos;
        this.premios = premios;
        this.numeroReproducciones = numeroReproducciones;
    }
}