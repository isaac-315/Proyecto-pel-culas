package ec.edu.ups.streaming.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private String id;
    private String email;
    private String password;
    private List<Perfil> perfiles = new ArrayList<>();
}