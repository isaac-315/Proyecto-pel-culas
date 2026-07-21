package ec.edu.ups.streaming.infrastructure.controller;

import ec.edu.ups.streaming.application.service.ProduccionService;
import ec.edu.ups.streaming.application.service.dto.ActorParticipacionReporte;
import ec.edu.ups.streaming.domain.model.Pelicula;
import ec.edu.ups.streaming.domain.model.Produccion;
import ec.edu.ups.streaming.domain.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/producciones")
public class ProduccionController {

    @Autowired
    private ProduccionService service;



    // ---------- Consultas (públicas) ----------

    @GetMapping
    public ResponseEntity<List<Produccion>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produccion> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Produccion>> buscarPorTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(service.buscarPorTitulo(titulo));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Produccion>> buscarPorGenero(@PathVariable String genero) {
        return ResponseEntity.ok(service.buscarPorGenero(genero));
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<Produccion>> buscarPorAnio(@PathVariable int anio) {
        return ResponseEntity.ok(service.buscarPorAnio(anio));
    }

    // ---------- CRUD (exclusivo administradores — se restringe en el Paso 6 con
    // JWT) ----------

    @PostMapping("/peliculas")
    public ResponseEntity<Produccion> crearPelicula(@RequestBody Pelicula pelicula) {
        return ResponseEntity.status(201).body(service.guardar(pelicula));
    }

    @PostMapping("/series")
    public ResponseEntity<Produccion> crearSerie(@RequestBody Serie serie) {
        return ResponseEntity.status(201).body(service.guardar(serie));
    }

    @PutMapping("/peliculas/{id}")
    public ResponseEntity<Produccion> editarPelicula(@PathVariable String id, @RequestBody Pelicula pelicula) {
        return ResponseEntity.ok(service.editar(id, pelicula));
    }

    @PutMapping("/series/{id}")
    public ResponseEntity<Produccion> editarSerie(@PathVariable String id, @RequestBody Serie serie) {
        return ResponseEntity.ok(service.editar(id, serie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Reportes ----------

    @GetMapping("/reportes/estrenadas")
    public ResponseEntity<List<Produccion>> produccionesEstrenadasEnRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(service.buscarPorRangoFechas(desde, hasta));
    }

    @GetMapping("/reportes/mas-reproducidas")
    public ResponseEntity<List<Produccion>> produccionesConMasReproducciones(
            @RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(service.produccionesConMasReproducciones(limite));
    }

    @GetMapping("/reportes/actor")
    public ResponseEntity<List<Produccion>> produccionesPorActor(
            @RequestParam String nombre) {
        return ResponseEntity.ok(service.produccionesPorActor(nombre));
    }

    @GetMapping("/reportes/actores-mayor-participacion")
    public ResponseEntity<List<ActorParticipacionReporte>> actoresConMasParticipaciones(
            @RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(service.actoresConMasParticipaciones(limite));
    }

    // ✨ ¡AGREGA ESTOS DOS AQUÍ! ✨
    @GetMapping("/peliculas")
    public ResponseEntity<List<Produccion>> listarSoloPeliculas() {
        return ResponseEntity.ok(service.listarPorTipo("pelicula"));
    }

    @GetMapping("/series")
    public ResponseEntity<List<Produccion>> listarSoloSeries() {
        return ResponseEntity.ok(service.listarPorTipo("serie"));
    }
}