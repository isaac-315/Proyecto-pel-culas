package ec.edu.ups.streaming.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Actor {
    private String nombreCompleto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(nombreCompleto, actor.nombreCompleto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreCompleto);
    }
}