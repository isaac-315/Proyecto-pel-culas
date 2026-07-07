package ec.edu.ups.streaming.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Serie extends Produccion {
    private Integer numeroTemporadas;
    private List<Temporada> temporadas;
}
