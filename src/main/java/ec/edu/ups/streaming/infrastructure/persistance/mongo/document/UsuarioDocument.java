package ec.edu.ups.streaming.infrastructure.persistance.mongo.document;

import ec.edu.ups.streaming.domain.model.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios")
public class UsuarioDocument {
    @Id
    private String id;
    private String email;
    private String password;
    private List<Perfil> perfiles;
    private String rol;
}