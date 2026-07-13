package ec.edu.ups.streaming.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Instant;
import java.util.*;

@Configuration
public class DataSeeder {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            // 🧹 1. Limpieza total para borrar tus registros de prueba anteriores
            System.out.println("🧹 Limpiando registros de prueba anteriores en MongoDB...");
            mongoTemplate.dropCollection("producciones");

            System.out.println("🚀 Generando catálogo masivo automático (100 películas)...");

            // Banco de 50 actores
            String[] listaActores = {
                    "Leonardo DiCaprio", "Brad Pitt", "Scarlett Johansson", "Meryl Streep", "Tom Hanks",
                    "Robert Downey Jr.", "Natalie Portman", "Morgan Freeman", "Al Pacino", "Robert De Niro",
                    "Matt Damon", "Christian Bale", "Johnny Depp", "Tom Cruise", "Denzel Washington",
                    "Jennifer Lawrence", "Emma Stone", "Will Smith", "Hugh Jackman", "Ryan Reynolds",
                    "Chris Hemsworth", "Mark Ruffalo", "Benedict Cumberbatch", "Chadwick Boseman", "Samuel L. Jackson",
                    "Chris Evans", "Margot Robbie", "Cillian Murphy", "Joaquin Phoenix", "Matthew McConaughey",
                    "Anne Hathaway", "Jessica Chastain", "Emily Blunt", "Amy Adams", "Kate Winslet",
                    "Penelope Cruz", "Salma Hayek", "Antonio Banderas", "Javier Bardem", "Ricardo Darín",
                    "Benicio del Toro", "Pedro Pascal", "Oscar Isaac", "Diego Luna", "Gael García Bernal",
                    "Zendaya", "Timothée Chalamet", "Florence Pugh", "Tom Holland", "Viola Davis"
            };

            String[] verbos = {"El Viaje", "La Venganza", "El Secreto", "La Crónica", "El Retorno", "El Misterio", "La Sombra", "El Despertar", "El Destino", "La Última"};
            String[] sustantivos = {"del Tiempo", "de las Estrellas", "en la Oscuridad", "del Guerrero", "Olvidado", "del Laberinto", "Eterno", "de la Ilusión", "del Héroe", "Bajo el Agua"};
            String[] generosDisponibles = {"Accion", "Ciencia Ficcion", "Drama", "Suspenso", "Comedia", "Terror", "Romance"};
            String[] premiosDisponibles = {"Oscar a la mejor fotografia", "Oscar a mejores efectos visuales", "Oscar a mejor pelicula", "Globo de Oro a mejor drama", "Palma de Oro"};

            Random random = new Random();
            List<Map<String, Object>> peliculasAInsertar = new ArrayList<>();

            for (int i = 1; i <= 100; i++) {
                Map<String, Object> produccion = new HashMap<>();

                String titulo = verbos[random.nextInt(verbos.length)] + " " + sustantivos[random.nextInt(sustantivos.length)] + " " + i;
                produccion.put("nombre", titulo);
                produccion.put("fechaEstreno", Instant.now().minusSeconds(random.nextInt(1000000000)));

                // Géneros comunes
                Set<String> generos = new HashSet<>();
                generos.add(generosDisponibles[random.nextInt(generosDisponibles.length)]);
                generos.add(generosDisponibles[random.nextInt(generosDisponibles.length)]);
                produccion.put("generos", new ArrayList<>(generos));

                // Actores comunes
                List<Map<String, String>> actoresPelicula = new ArrayList<>();
                Set<String> actoresSeleccionados = new HashSet<>();
                int cantidadActores = 2 + random.nextInt(3);
                while (actoresSeleccionados.size() < cantidadActores) {
                    actoresSeleccionados.add(listaActores[random.nextInt(listaActores.length)]);
                }
                for (String actor : actoresSeleccionados) {
                    Map<String, String> actorMap = new HashMap<>();
                    actorMap.put("nombreCompleto", actor);
                    actoresPelicula.add(actorMap);
                }
                produccion.put("actoresPrincipales", actoresPelicula);

                // 🔀 Decidir aleatoriamente si es Película o Serie
                if (random.nextBoolean()) {
                    // --- CONFIGURACIÓN DE PELÍCULA ---
                    produccion.put("tipo", "pelicula");
                    produccion.put("_class", "pelicula"); // <-- Coincide con tu @TypeAlias("pelicula")

                    produccion.put("duracionMinutos", 90 + random.nextInt(90));
                    produccion.put("numeroReproducciones", 10000L + random.nextInt(1000000));

                    List<String> premios = new ArrayList<>();
                    if (random.nextBoolean()) {
                        premios.add(premiosDisponibles[random.nextInt(premiosDisponibles.length)]);
                    }
                    produccion.put("premios", premios);
                } else {
                    // --- CONFIGURACIÓN DE SERIE ---
                    produccion.put("tipo", "serie");
                    produccion.put("_class", "serie"); // <-- Coincide con tu @TypeAlias("serie")

                    produccion.put("numeroTemporadas", 1 + random.nextInt(8));

                    // Estructura básica para la lista de temporadas si la requieres
                    produccion.put("temporadas", new ArrayList<>());
                }

                peliculasAInsertar.add(produccion);
            }

            mongoTemplate.insert(peliculasAInsertar, "producciones");
            System.out.println("🎯 ¡Base de datos reiniciada! 100 películas listas para la simulación.");
        };
    }
}