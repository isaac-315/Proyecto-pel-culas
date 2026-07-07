package ec.edu.ups.streaming.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Pelicula extends Produccion {
    private Integer duracionMinutos;
    private List<String> premios;
    private Long numeroReproducciones;
}
