package ec.edu.ups.streaming.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Temporada {
    private Integer numeroTemporada;
    private Integer cantidadEpisodios;
    private List<Episodio> episodios;
}
