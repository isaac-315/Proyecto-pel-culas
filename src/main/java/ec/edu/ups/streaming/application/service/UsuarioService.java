package ec.edu.ups.streaming.application.service;

import ec.edu.ups.streaming.domain.model.Perfil;
import ec.edu.ups.streaming.domain.model.Usuario;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.UsuarioDocument;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.mapper.UsuarioMapper;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper mapper; // Es mejor inyectar el mapper si es un Bean

    // 1. Registrar usuario: Ahora retorna el Usuario creado
    public Usuario registrarUsuario(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        UsuarioDocument doc = mapper.toDocument(usuario);
        UsuarioDocument guardado = repository.save(doc);
        return mapper.toDomain(guardado);
    }

    // 2. Agregar perfil: Ahora retorna el usuario actualizado
    public Usuario agregarPerfil(String email, Perfil nuevoPerfil) {
        UsuarioDocument doc = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        doc.getPerfiles().add(nuevoPerfil);
        UsuarioDocument actualizado = repository.save(doc);
        return mapper.toDomain(actualizado);
    }

    // 3. Buscar usuario: Para el login
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }
}