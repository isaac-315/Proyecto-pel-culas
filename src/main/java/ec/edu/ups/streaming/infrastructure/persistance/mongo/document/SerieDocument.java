package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SerieDocument extends ProduccionDocument {
    private Integer numeroTemporadas;
    private List<TemporadaDocument> temporadas;

    public SerieDocument() {
        this.setTipo("serie");
    }
}