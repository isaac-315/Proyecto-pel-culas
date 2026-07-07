package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodioDocument {
    private Integer numeroEpisodio;
    private String titulo;
    private Integer duracionMinutos;
}
