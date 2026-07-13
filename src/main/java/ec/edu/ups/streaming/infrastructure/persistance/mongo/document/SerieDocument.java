package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias; // <--- Importante
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // Necesario para Spring Data
@TypeAlias("serie") // <--- Esto se guardará en el campo _class de MongoDB
public class SerieDocument extends ProduccionDocument {
    private Integer numeroTemporadas;
    private List<TemporadaDocument> temporadas;

    public SerieDocument(Integer numeroTemporadas, List<TemporadaDocument> temporadas) {
        this.setTipo("serie");
        this.numeroTemporadas = numeroTemporadas;
        this.temporadas = temporadas;
    }
}