package ec.edu.ups.streaming.infrastructure.controller;
import ec.edu.ups.streaming.application.service.UsuarioService;
import ec.edu.ups.streaming.domain.model.Perfil;
import ec.edu.ups.streaming.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para registrar un usuario nuevo
    // En UsuarioController.java
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        Usuario usuarioRegistrado = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(201).body(usuarioRegistrado);
    }

    // Endpoint para agregar un perfil al usuario (tus compañeros lo usarán seguro)
    @PostMapping("/{email}/perfiles")
    public ResponseEntity<Usuario> agregarPerfil(@PathVariable String email, @RequestBody Perfil perfil) {
        Usuario usuarioActualizado = usuarioService.agregarPerfil(email, perfil);
        return ResponseEntity.ok(usuarioActualizado); // Retorna el usuario con el nuevo perfil
    }
}