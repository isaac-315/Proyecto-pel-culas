package ec.edu.ups.streaming.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public abstract class Produccion {
    private String id;
    private String nombre;
    private List<String> generos;
    private Date fechaEstreno;
    private List<Actor> actoresPrincipales;
}