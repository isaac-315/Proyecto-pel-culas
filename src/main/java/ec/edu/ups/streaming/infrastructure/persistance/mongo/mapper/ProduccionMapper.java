package ec.edu.ups.streaming.infrastructure.persistance.mongo.mapper;

import ec.edu.ups.streaming.domain.model.*;
import ec.edu.ups.streaming.infrastructure.persistance.mongo.document.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProduccionMapper {

    // ---------- Dominio -> Documento ----------
    public ProduccionDocument toDocument(Produccion produccion) {
        if (produccion instanceof Pelicula pelicula) {
            return toDocument(pelicula);
        } else if (produccion instanceof Serie serie) {
            return toDocument(serie);
        }
        throw new IllegalArgumentException("Tipo de producción no soportado: " + produccion.getClass());
    }

    public PeliculaDocument toDocument(Pelicula pelicula) {
        PeliculaDocument doc = new PeliculaDocument();
        doc.setId(pelicula.getId());
        doc.setNombre(pelicula.getNombre());
        doc.setGeneros(pelicula.getGeneros());
        doc.setFechaEstreno(pelicula.getFechaEstreno());
        doc.setActoresPrincipales(toActorDocuments(pelicula.getActoresPrincipales()));
        doc.setDuracionMinutos(pelicula.getDuracionMinutos());
        doc.setPremios(pelicula.getPremios());
        doc.setNumeroReproducciones(pelicula.getNumeroReproducciones());
        return doc;
    }

    public SerieDocument toDocument(Serie serie) {
        SerieDocument doc = new SerieDocument();
        doc.setId(serie.getId());
        doc.setNombre(serie.getNombre());
        doc.setGeneros(serie.getGeneros());
        doc.setFechaEstreno(serie.getFechaEstreno());
        doc.setActoresPrincipales(toActorDocuments(serie.getActoresPrincipales()));
        doc.setNumeroTemporadas(serie.getNumeroTemporadas());
        doc.setTemporadas(toTemporadaDocuments(serie.getTemporadas()));
        return doc;
    }

    // ---------- Documento -> Dominio ----------
    public Produccion toDomain(ProduccionDocument doc) {
        if (doc instanceof PeliculaDocument peliculaDoc) {
            return toDomain(peliculaDoc);
        } else if (doc instanceof SerieDocument serieDoc) {
            return toDomain(serieDoc);
        }
        throw new IllegalArgumentException("Tipo de documento no soportado: " + doc.getClass());
    }

    public Pelicula toDomain(PeliculaDocument doc) {
        Pelicula pelicula = new Pelicula();
        pelicula.setId(doc.getId());
        pelicula.setNombre(doc.getNombre());
        pelicula.setGeneros(doc.getGeneros());
        pelicula.setFechaEstreno(doc.getFechaEstreno());
        pelicula.setActoresPrincipales(toActores(doc.getActoresPrincipales()));
        pelicula.setDuracionMinutos(doc.getDuracionMinutos());
        pelicula.setPremios(doc.getPremios());
        pelicula.setNumeroReproducciones(doc.getNumeroReproducciones());
        return pelicula;
    }

    public Serie toDomain(SerieDocument doc) {
        Serie serie = new Serie();
        serie.setId(doc.getId());
        serie.setNombre(doc.getNombre());
        serie.setGeneros(doc.getGeneros());
        serie.setFechaEstreno(doc.getFechaEstreno());
        serie.setActoresPrincipales(toActores(doc.getActoresPrincipales()));
        serie.setNumeroTemporadas(doc.getNumeroTemporadas());
        serie.setTemporadas(toTemporadas(doc.getTemporadas()));
        return serie;
    }

    public List<Produccion> toDomainList(List<ProduccionDocument> docs) {
        return docs.stream().map(this::toDomain).collect(Collectors.toList());
    }

    // ---------- Helpers: Actor ----------
    private List<ActorDocument> toActorDocuments(List<Actor> actores) {
        if (actores == null) return new ArrayList<>();
        return actores.stream()
                .map(a -> new ActorDocument(a.getNombreCompleto()))
                .collect(Collectors.toList());
    }

    private List<Actor> toActores(List<ActorDocument> actorDocuments) {
        if (actorDocuments == null) return new ArrayList<>();
        return actorDocuments.stream()
                .map(ad -> new Actor(ad.getNombreCompleto()))
                .collect(Collectors.toList());
    }

    // ---------- Helpers: Temporada / Episodio ----------
    private List<TemporadaDocument> toTemporadaDocuments(List<Temporada> temporadas) {
        if (temporadas == null) return new ArrayList<>();
        return temporadas.stream().map(t -> {
            TemporadaDocument td = new TemporadaDocument();
            td.setNumeroTemporada(t.getNumeroTemporada());
            td.setCantidadEpisodios(t.getCantidadEpisodios());
            td.setEpisodios(toEpisodioDocuments(t.getEpisodios()));
            return td;
        }).collect(Collectors.toList());
    }

    private List<Temporada> toTemporadas(List<TemporadaDocument> temporadaDocuments) {
        if (temporadaDocuments == null) return new ArrayList<>();
        return temporadaDocuments.stream().map(td -> {
            Temporada t = new Temporada();
            t.setNumeroTemporada(td.getNumeroTemporada());
            t.setCantidadEpisodios(td.getCantidadEpisodios());
            t.setEpisodios(toEpisodios(td.getEpisodios()));
            return t;
        }).collect(Collectors.toList());
    }

    private List<EpisodioDocument> toEpisodioDocuments(List<Episodio> episodios) {
        if (episodios == null) return new ArrayList<>();
        return episodios.stream()
                .map(e -> new EpisodioDocument(e.getNumeroEpisodio(), e.getTitulo(), e.getDuracionMinutos()))
                .collect(Collectors.toList());
    }

    private List<Episodio> toEpisodios(List<EpisodioDocument> episodioDocuments) {
        if (episodioDocuments == null) return new ArrayList<>();
        return episodioDocuments.stream()
                .map(ed -> new Episodio(ed.getNumeroEpisodio(), ed.getTitulo(), ed.getDuracionMinutos()))
                .collect(Collectors.toList());
    }
}