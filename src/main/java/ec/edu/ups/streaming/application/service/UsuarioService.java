package ec.edu.ups.streaming.application.service;

import ec.edu.ups.streaming.domain.model.Perfil;
import ec.edu.ups.streaming.domain.model.Usuario;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.UsuarioDocument;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.mapper.UsuarioMapper;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UsuarioMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Registrar usuario: ahora encripta la contraseña y asigna rol por defecto
    public Usuario registrarUsuario(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }
        if (usuario.getRol() == null || usuario.getRol().isBlank()) {
            usuario.setRol("USER");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        UsuarioDocument doc = mapper.toDocument(usuario);
        UsuarioDocument guardado = repository.save(doc);
        return mapper.toDomain(guardado);
    }

    // 2. Agregar perfil: sin cambios
    public Usuario agregarPerfil(String email, Perfil nuevoPerfil) {
        UsuarioDocument doc = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        doc.getPerfiles().add(nuevoPerfil);
        UsuarioDocument actualizado = repository.save(doc);
        return mapper.toDomain(actualizado);
    }

    // 3. Buscar usuario: sin cambios
    public Optional<Usuario> buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }
}