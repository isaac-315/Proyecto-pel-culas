package ec.edu.ups.streaming.infrastructure.persistance.mongo.mapper;

import ec.edu.ups.streaming.domain.model.Usuario;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.UsuarioDocument;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioDocument doc) {
        return new Usuario(doc.getId(), doc.getEmail(), doc.getPassword(), doc.getPerfiles());
    }

    public UsuarioDocument toDocument(Usuario user) {
        return new UsuarioDocument(user.getId(), user.getEmail(), user.getPassword(), user.getPerfiles());
    }
}