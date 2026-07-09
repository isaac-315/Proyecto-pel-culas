package ec.edu.ups.streaming.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Perfil {
    private String id;
    private String nombre;
    private String avatar;
    private boolean esInfantil;
    private List<String> favoritos;

    public Perfil() {
        this.id = UUID.randomUUID().toString(); // Genera un ID único para el subperfil
        this.favoritos = new ArrayList<>();
    }

    public Perfil(String nombre, String avatar, boolean esInfantil) {
        this();
        this.nombre = nombre;
        this.avatar = avatar;
        this.esInfantil = esInfantil;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public boolean isEsInfantil() { return esInfantil; }
    public void setEsInfantil(boolean esInfantil) { this.esInfantil = esInfantil; }
    public List<String> getFavoritos() { return favoritos; }
    public void setFavoritos(List<String> favoritos) { this.favoritos = favoritos; }
}
