package ec.edu.ups.streaming.application.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActorParticipacionReporte {
    private String actor;
    private Long participaciones;
}