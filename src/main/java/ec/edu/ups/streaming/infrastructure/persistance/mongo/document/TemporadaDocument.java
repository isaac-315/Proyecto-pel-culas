package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TemporadaDocument {
    private Integer numeroTemporada;
    private Integer cantidadEpisodios;
    private List<EpisodioDocument> episodios;
}
