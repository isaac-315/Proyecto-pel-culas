# Sistema de Catálogo de Películas y Series - NoSQL

## Descripción del proyecto

Este proyecto consiste en una aplicación backend desarrollada con **Spring Boot** y **MongoDB** para administrar el catálogo de una plataforma de películas y series por streaming.

El sistema permite registrar, consultar, editar y eliminar producciones audiovisuales, diferenciando entre películas y series. Además, implementa los reportes solicitados para el análisis del catálogo, como producciones por género, producciones estrenadas en un rango de fechas, producciones con mayor número de reproducciones, producciones donde participa un actor determinado y actores con mayor número de participaciones.

El proyecto fue desarrollado para la práctica de **Bases de Datos No Relacionales**, utilizando una base de datos documental.

---

## Problema elegido

El problema seleccionado fue:

**Películas o series de televisión usando una Base de Datos Documental.**

Se requiere desarrollar un sistema para administrar el catálogo de una plataforma de películas y series por streaming.

El sistema debe registrar:

### Películas

- Nombre
- Género(s)
- Fecha de estreno
- Duración
- Premios
- Número de reproducciones
- Actores principales

### Series

- Nombre
- Género(s)
- Fecha de estreno
- Número de temporadas
- Episodios por temporada
- Actores principales

Cada película o serie se almacena como un único documento dentro de la colección `producciones`.

---

## Justificación de la elección del problema

Se eligió el problema de películas y series porque se adapta correctamente a una base de datos documental. Cada producción puede representarse como un documento independiente, lo cual facilita almacenar información general, listas y datos anidados.

Este problema es adecuado para MongoDB porque:

- Cada película o serie puede guardarse como un documento completo.
- Los géneros pueden almacenarse como una lista.
- Los actores principales pueden almacenarse como objetos anidados.
- Las series pueden contener temporadas y episodios dentro del mismo documento.
- Las consultas por género, fecha, actor y reproducciones son directas.
- No se requiere una estructura relacional estricta con varias tablas.

Por estas razones, el problema seleccionado permite demostrar claramente el uso de una base de datos NoSQL documental.

---

## Justificación del manejador NoSQL elegido

Se eligió **MongoDB** porque es un manejador de base de datos NoSQL orientado a documentos. Este tipo de base de datos se adapta correctamente al problema, ya que una película o una serie puede representarse como un documento completo con atributos simples, listas y objetos anidados.

MongoDB permite guardar información flexible en formato similar a JSON/BSON. Esto resulta adecuado para manejar producciones que pueden tener estructuras diferentes. Por ejemplo, una película tiene duración, premios y número de reproducciones, mientras que una serie tiene temporadas y episodios.

Además, MongoDB permite consultar documentos por campos específicos, listas internas y objetos anidados, lo cual facilita la creación de reportes sobre géneros, actores, fechas de estreno y reproducciones.

---

## Tipo de base de datos NoSQL utilizada

El tipo de base de datos NoSQL utilizado es:

**Base de datos documental**

En una base de datos documental, la información se almacena en documentos. Estos documentos pueden contener campos simples, arreglos y objetos anidados.

En este proyecto, cada documento representa una producción audiovisual, que puede ser una película o una serie.

---

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje principal del backend |
| Spring Boot 4.1.0 | Framework principal de desarrollo |
| Spring Web MVC | Creación de API REST |
| Spring Data MongoDB | Conexión y operaciones con MongoDB |
| MongoDB | Base de datos NoSQL documental |
| Maven | Gestión de dependencias y ejecución del proyecto |
| Lombok | Reducción de código repetitivo |
| JWT | Seguridad y autenticación |
| Postman | Pruebas de endpoints |

---

## Arquitectura del proyecto

El proyecto está organizado usando una estructura por capas:

```text
src
├── main
│   ├── java
│   │   └── ec
│   │       └── edu
│   │           └── ups
│   │               └── streaming
│   │                   │   StreamingApplication.java
│   │                   │
│   │                   ├── application
│   │                   │   └── service
│   │                   │           ProduccionService.java
│   │                   │           UsuarioService.java
│   │                   │
│   │                   ├── domain
│   │                   │   └── model
│   │                   │           Actor.java
│   │                   │           Episodio.java
│   │                   │           Pelicula.java
│   │                   │           Perfil.java
│   │                   │           Produccion.java
│   │                   │           Serie.java
│   │                   │           Temporada.java
│   │                   │           Usuario.java
│   │                   │
│   │                   └── infrastructure
│   │                       ├── controller
│   │                       │       AuthController.java
│   │                       │       ProduccionController.java
│   │                       │       UsuarioController.java
│   │                       │
│   │                       ├── persistance
│   │                       │   └── mongo
│   │                       │       ├── document
│   │                       │       │       ActorDocument.java
│   │                       │       │       EpisodioDocument.java
│   │                       │       │       PeliculaDocument.java
│   │                       │       │       ProduccionDocument.java
│   │                       │       │       SerieDocument.java
│   │                       │       │       TemporadaDocument.java
│   │                       │       │       UsuarioDocument.java
│   │                       │       │
│   │                       │       ├── mapper
│   │                       │       │       ProduccionMapper.java
│   │                       │       │       UsuarioMapper.java
│   │                       │       │
│   │                       │       └── repository
│   │                       │               ProduccionRepository.java
│   │                       │               UsuarioRepository.java
│   │                       │
│   │                       └── security
│   │                               JwtAuthenticationFilter.java
│   │                               JwtService.java
│   │                               PasswordEncoderConfig.java
│   │                               SecurityConfig.java
│   │                               UsuarioDetailsService.java
│   │
│   └── resources
│           application.properties
│
└── test
    └── java
        └── ec
            └── edu
                └── ups
                    └── streaming
                            StreamingApplicationTests.java
```

---

## Capas principales del sistema

### 1. Capa de dominio

Contiene las clases principales del modelo del negocio.

Ubicación:

```text
src/main/java/ec/edu/ups/streaming/domain/model
```

Clases principales:

- `Produccion.java`
- `Pelicula.java`
- `Serie.java`
- `Actor.java`
- `Temporada.java`
- `Episodio.java`
- `Usuario.java`
- `Perfil.java`

La clase `Produccion` funciona como clase base para películas y series.

---

### 2. Capa de documentos MongoDB

Contiene las clases que representan la estructura de los documentos almacenados en MongoDB.

Ubicación:

```text
src/main/java/ec/edu/ups/streaming/infrastructure/persistance/mongo/document
```

Clases principales:

- `ProduccionDocument.java`
- `PeliculaDocument.java`
- `SerieDocument.java`
- `ActorDocument.java`
- `TemporadaDocument.java`
- `EpisodioDocument.java`
- `UsuarioDocument.java`

La colección principal del sistema se llama:

```text
producciones
```

---

### 3. Capa de mapeo

Convierte objetos del dominio a documentos MongoDB y viceversa.

Ubicación:

```text
src/main/java/ec/edu/ups/streaming/infrastructure/persistance/mongo/mapper
```

Clases principales:

- `ProduccionMapper.java`
- `UsuarioMapper.java`

Esta capa permite separar el modelo del dominio del modelo usado para persistencia en MongoDB.

---

### 4. Capa de repositorio

Contiene las interfaces que permiten acceder a MongoDB usando Spring Data MongoDB.

Ubicación:

```text
src/main/java/ec/edu/ups/streaming/infrastructure/persistance/mongo/repository
```

Repositorios principales:

- `ProduccionRepository.java`
- `UsuarioRepository.java`

El repositorio de producciones permite realizar búsquedas por:

- Nombre
- Tipo de producción
- Género
- Rango de fechas
- Actor principal
- Número de reproducciones

---

### 5. Capa de servicio

Contiene la lógica de negocio del sistema.

Ubicación:

```text
src/main/java/ec/edu/ups/streaming/application/service
```

Servicios principales:

- `ProduccionService.java`
- `UsuarioService.java`

En esta capa se implementan:

- Registro de películas y series
- Edición de producciones
- Eliminación de producciones
- Consulta general de producciones
- Reportes solicitados
- Gestión de usuarios

---

### 6. Capa de controlador

Contiene los endpoints REST que se consumen desde Postman, navegador o una futura interfaz web.

Ubicación:

```text
src/main/java/ec/edu/ups/streaming/infrastructure/controller
```

Controladores principales:

- `ProduccionController.java`
- `UsuarioController.java`
- `AuthController.java`

Ruta base principal:

```http
/api/producciones
```

---

### 7. Capa de seguridad

Contiene la configuración de seguridad del sistema.

Ubicación:

```text
src/main/java/ec/edu/ups/streaming/infrastructure/security
```

Archivos principales:

- `JwtAuthenticationFilter.java`
- `JwtService.java`
- `PasswordEncoderConfig.java`
- `SecurityConfig.java`
- `UsuarioDetailsService.java`

Esta capa permite manejar autenticación y protección de endpoints mediante JWT.

---

## Modelo de datos documental

La colección principal del sistema es:

```text
producciones
```

Cada documento representa una película o una serie.

---

## Modelo general de producción

La clase base `Produccion` contiene los campos comunes:

```java
public abstract class Produccion {
    private String id;
    private String nombre;
    private List<String> generos;
    private Date fechaEstreno;
    private List<Actor> actoresPrincipales;
}
```

Campos comunes:

| Campo | Descripción |
|---|---|
| `id` | Identificador único del documento |
| `nombre` | Nombre de la película o serie |
| `generos` | Lista de géneros de la producción |
| `fechaEstreno` | Fecha de estreno |
| `actoresPrincipales` | Lista de actores principales |

---

## Modelo de película

La clase `Pelicula` extiende de `Produccion` y agrega campos específicos:

```java
public class Pelicula extends Produccion {
    private Integer duracionMinutos;
    private List<String> premios;
    private Long numeroReproducciones;
}
```

Campos específicos:

| Campo | Descripción |
|---|---|
| `duracionMinutos` | Duración de la película en minutos |
| `premios` | Lista de premios recibidos |
| `numeroReproducciones` | Número de reproducciones de la película |

---

## Modelo de serie

La clase `Serie` extiende de `Produccion` y agrega campos específicos:

```java
public class Serie extends Produccion {
    private Integer numeroTemporadas;
    private List<Temporada> temporadas;
}
```

Campos específicos:

| Campo | Descripción |
|---|---|
| `numeroTemporadas` | Cantidad de temporadas |
| `temporadas` | Lista de temporadas de la serie |

---

## Modelo de actor

```java
public class Actor {
    private String nombreCompleto;
}
```

Campo:

| Campo | Descripción |
|---|---|
| `nombreCompleto` | Nombre completo del actor |

---

## Documento de MongoDB para producciones

La clase `ProduccionDocument` representa la estructura base almacenada en MongoDB:

```java
@Document(collection = "producciones")
public abstract class ProduccionDocument {
    @Id
    private String id;
    private String tipo;
    private String nombre;
    private List<String> generos;
    private Date fechaEstreno;
    private List<ActorDocument> actoresPrincipales;
}
```

El campo `tipo` permite diferenciar entre:

```text
pelicula
serie
```

---

## Documento de película

```java
public class PeliculaDocument extends ProduccionDocument {
    private Integer duracionMinutos;
    private List<String> premios;
    private Long numeroReproducciones;

    public PeliculaDocument() {
        this.setTipo("pelicula");
    }
}
```

---

## Documento de serie

```java
public class SerieDocument extends ProduccionDocument {
    private Integer numeroTemporadas;
    private List<TemporadaDocument> temporadas;

    public SerieDocument() {
        this.setTipo("serie");
    }
}
```

---

## Ejemplo de documento de película

```json
{
  "tipo": "pelicula",
  "nombre": "Spider-Man: No Way Home",
  "generos": ["Acción", "Aventura", "Ciencia ficción"],
  "fechaEstreno": "2021-12-17T00:00:00.000Z",
  "actoresPrincipales": [
    {
      "nombreCompleto": "Tom Holland"
    },
    {
      "nombreCompleto": "Zendaya"
    },
    {
      "nombreCompleto": "Benedict Cumberbatch"
    }
  ],
  "duracionMinutos": 148,
  "premios": ["People's Choice Award"],
  "numeroReproducciones": 9500000
}
```

---

## Ejemplo de documento de serie

```json
{
  "tipo": "serie",
  "nombre": "Stranger Things",
  "generos": ["Ciencia ficción", "Terror", "Drama"],
  "fechaEstreno": "2016-07-15T00:00:00.000Z",
  "actoresPrincipales": [
    {
      "nombreCompleto": "Millie Bobby Brown"
    },
    {
      "nombreCompleto": "Finn Wolfhard"
    }
  ],
  "numeroTemporadas": 4,
  "temporadas": [
    {
      "numeroTemporada": 1,
      "cantidadEpisodios": 8,
      "episodios": [
        {
          "numeroEpisodio": 1,
          "titulo": "Chapter One",
          "duracionMinutos": 48
        }
      ]
    }
  ]
}
```

---

## Configuración de MongoDB

La conexión a MongoDB se configura en el archivo:

```text
src/main/resources/application.properties
```

Configuración principal:

```properties
spring.application.name=streaming

spring.data.mongodb.uri=mongodb://localhost:27017/streaming_db

logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG
```

La base de datos utilizada es:

```text
streaming_db
```

La colección principal utilizada es:

```text
producciones
```

---

## Dependencias principales del proyecto

El proyecto utiliza Maven como gestor de dependencias.

Dependencias principales del archivo `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webmvc</artifactId>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.6</version>
</dependency>

<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

---

## Requisitos para ejecutar el proyecto

Antes de ejecutar el proyecto se necesita tener instalado:

- Java 17
- Maven
- MongoDB
- Postman o navegador para probar los endpoints

Verificar Java:

```bash
java -version
```

Verificar Maven:

```bash
mvn -version
```

Verificar MongoDB:

```bash
mongod --version
```

---

## Ejecución del proyecto

Desde la raíz del proyecto ejecutar:

```bash
mvn clean compile
```

Luego iniciar la aplicación:

```bash
mvn spring-boot:run
```

El backend se ejecuta por defecto en:

```http
http://localhost:8080
```

---

## Endpoints principales

### Listar todas las producciones

```http
GET http://localhost:8080/api/producciones
```

Devuelve todas las películas y series registradas.

---

### Buscar producción por ID

```http
GET http://localhost:8080/api/producciones/{id}
```

Ejemplo:

```http
GET http://localhost:8080/api/producciones/65f123abc456
```

---

### Buscar producción por título

```http
GET http://localhost:8080/api/producciones/buscar?titulo=Spider
```

Permite buscar películas o series por coincidencia parcial del nombre.

---

### Buscar producciones por género

```http
GET http://localhost:8080/api/producciones/genero/Acción
```

Devuelve las producciones que pertenecen al género indicado.

---

### Buscar producciones por año

```http
GET http://localhost:8080/api/producciones/anio/2023
```

Devuelve las producciones estrenadas en un año específico.

---

## Endpoints CRUD

### Crear película

```http
POST http://localhost:8080/api/producciones/peliculas
```

Ejemplo de cuerpo JSON:

```json
{
  "nombre": "Spider-Man: No Way Home",
  "generos": ["Acción", "Aventura", "Ciencia ficción"],
  "fechaEstreno": "2021-12-17T00:00:00.000Z",
  "actoresPrincipales": [
    {
      "nombreCompleto": "Tom Holland"
    },
    {
      "nombreCompleto": "Zendaya"
    }
  ],
  "duracionMinutos": 148,
  "premios": ["People's Choice Award"],
  "numeroReproducciones": 9500000
}
```

---

### Crear serie

```http
POST http://localhost:8080/api/producciones/series
```

Ejemplo de cuerpo JSON:

```json
{
  "nombre": "Stranger Things",
  "generos": ["Ciencia ficción", "Terror", "Drama"],
  "fechaEstreno": "2016-07-15T00:00:00.000Z",
  "actoresPrincipales": [
    {
      "nombreCompleto": "Millie Bobby Brown"
    },
    {
      "nombreCompleto": "Finn Wolfhard"
    }
  ],
  "numeroTemporadas": 4,
  "temporadas": [
    {
      "numeroTemporada": 1,
      "cantidadEpisodios": 8,
      "episodios": [
        {
          "numeroEpisodio": 1,
          "titulo": "Chapter One",
          "duracionMinutos": 48
        }
      ]
    }
  ]
}
```

---

### Editar película

```http
PUT http://localhost:8080/api/producciones/peliculas/{id}
```

---

### Editar serie

```http
PUT http://localhost:8080/api/producciones/series/{id}
```

---

### Eliminar producción

```http
DELETE http://localhost:8080/api/producciones/{id}
```

---

## Reportes implementados

El sistema implementa los reportes solicitados para el problema de películas y series.

---

### 1. Producciones estrenadas en un período

Permite consultar producciones estrenadas dentro de un rango libre de fechas.

```http
GET http://localhost:8080/api/producciones/reportes/estrenadas?desde=2020-01-01&hasta=2024-12-31
```

Parámetros:

| Parámetro | Descripción |
|---|---|
| `desde` | Fecha inicial del rango |
| `hasta` | Fecha final del rango |

Formato de fecha:

```text
YYYY-MM-DD
```

Ejemplo:

```http
GET http://localhost:8080/api/producciones/reportes/estrenadas?desde=2020-01-01&hasta=2024-12-31
```

---

### 2. Producciones con mayor número de reproducciones

Devuelve las producciones ordenadas de mayor a menor número de reproducciones.

```http
GET http://localhost:8080/api/producciones/reportes/mas-reproducidas?limite=5
```

Parámetros:

| Parámetro | Descripción |
|---|---|
| `limite` | Cantidad máxima de resultados a mostrar |

Nota: en el modelo actual, el campo `numeroReproducciones` se encuentra implementado en las películas.

---

### 3. Producciones por género

Devuelve las producciones que pertenecen a un género determinado.

```http
GET http://localhost:8080/api/producciones/genero/Acción
```

---

### 4. Producciones donde participa un actor determinado

Devuelve las producciones en las que participa un actor específico.

```http
GET http://localhost:8080/api/producciones/reportes/actor?nombre=Tom Holland
```

Parámetros:

| Parámetro | Descripción |
|---|---|
| `nombre` | Nombre o parte del nombre del actor |

---

### 5. Actores con mayor número de participaciones

Devuelve los actores ordenados según la cantidad de producciones en las que aparecen.

```http
GET http://localhost:8080/api/producciones/reportes/actores-mayor-participacion?limite=10
```

Parámetros:

| Parámetro | Descripción |
|---|---|
| `limite` | Cantidad máxima de actores a mostrar |

Ejemplo de respuesta:

```json
[
  {
    "actor": "Tom Holland",
    "participaciones": 3
  },
  {
    "actor": "Zendaya",
    "participaciones": 2
  }
]
```

---

## Resumen de endpoints probados

Se probaron correctamente los siguientes endpoints en Postman:

```http
GET http://localhost:8080/api/producciones
```

```http
GET http://localhost:8080/api/producciones/genero/Acción
```

```http
GET http://localhost:8080/api/producciones/reportes/estrenadas?desde=2020-01-01&hasta=2024-12-31
```

```http
GET http://localhost:8080/api/producciones/reportes/mas-reproducidas?limite=5
```

```http
GET http://localhost:8080/api/producciones/reportes/actor?nombre=Tom Holland
```

```http
GET http://localhost:8080/api/producciones/reportes/actores-mayor-participacion?limite=10
```

---

## Consultas MongoDB relacionadas

### Ver todas las producciones

```javascript
db.producciones.find()
```

---

### Buscar producciones por género

```javascript
db.producciones.find({
  generos: "Acción"
})
```

---

### Buscar producciones por actor

```javascript
db.producciones.find({
  "actoresPrincipales.nombreCompleto": /Tom Holland/i
})
```

---

### Buscar producciones estrenadas en un rango de fechas

```javascript
db.producciones.find({
  fechaEstreno: {
    $gte: ISODate("2020-01-01T00:00:00.000Z"),
    $lte: ISODate("2024-12-31T23:59:59.999Z")
  }
})
```

---

### Producciones con más reproducciones

```javascript
db.producciones.find({
  tipo: "pelicula",
  numeroReproducciones: { $exists: true }
}).sort({
  numeroReproducciones: -1
}).limit(5)
```

---

### Actores con mayor número de participaciones

```javascript
db.producciones.aggregate([
  {
    $unwind: "$actoresPrincipales"
  },
  {
    $group: {
      _id: "$actoresPrincipales.nombreCompleto",
      participaciones: {
        $sum: 1
      }
    }
  },
  {
    $project: {
      _id: 0,
      actor: "$_id",
      participaciones: 1
    }
  },
  {
    $sort: {
      participaciones: -1
    }
  },
  {
    $limit: 10
  }
])
```

---

## Lógica de los reportes

### Reporte de producciones estrenadas en un período

Este reporte recibe dos fechas: `desde` y `hasta`.

El backend convierte esas fechas en objetos de tipo `Date` y consulta MongoDB usando un rango sobre el campo `fechaEstreno`.

Consulta usada en el repositorio:

```java
List<ProduccionDocument> findByFechaEstrenoBetween(Date desde, Date hasta);
```

---

### Reporte de producciones con mayor número de reproducciones

Este reporte busca las películas que tienen el campo `numeroReproducciones` y las ordena de mayor a menor.

Consulta usada en el repositorio:

```java
@Query(
    value = "{ 'tipo': 'pelicula', 'numeroReproducciones': { $exists: true } }",
    sort = "{ 'numeroReproducciones': -1 }"
)
List<ProduccionDocument> buscarProduccionesConMasReproducciones(Pageable pageable);
```

---

### Reporte de producciones donde participa un actor determinado

Este reporte consulta el campo anidado:

```text
actoresPrincipales.nombreCompleto
```

Consulta usada en el repositorio:

```java
List<ProduccionDocument> findByActoresPrincipalesNombreCompletoContainingIgnoreCase(String nombreActor);
```

---

### Reporte de actores con mayor número de participaciones

Este reporte usa una agregación de MongoDB.

La agregación realiza los siguientes pasos:

1. Descompone la lista `actoresPrincipales`.
2. Agrupa por nombre del actor.
3. Cuenta cuántas veces aparece cada actor.
4. Ordena de mayor a menor.
5. Limita la cantidad de resultados.

Código base usado:

```java
Aggregation aggregation = Aggregation.newAggregation(
    Aggregation.unwind("actoresPrincipales"),
    Aggregation.group("actoresPrincipales.nombreCompleto")
            .count()
            .as("participaciones"),
    Aggregation.project("participaciones")
            .and("_id")
            .as("actor"),
    Aggregation.sort(Sort.Direction.DESC, "participaciones"),
    Aggregation.limit(limite)
);
```

---

## Seguridad

El proyecto incluye configuración de seguridad con JWT.

Archivos relacionados:

```text
JwtAuthenticationFilter.java
JwtService.java
PasswordEncoderConfig.java
SecurityConfig.java
UsuarioDetailsService.java
AuthController.java
UsuarioController.java
```

La seguridad permite controlar el acceso a determinadas operaciones, especialmente las relacionadas con la administración de usuarios y el CRUD de producciones.

---

## Comandos útiles

### Compilar el proyecto

```bash
mvn clean compile
```

---

### Ejecutar el proyecto

```bash
mvn spring-boot:run
```

---

## Base de datos

Nombre de la base de datos:

```text
streaming_db
```

Colección principal:

```text
producciones
```

Colecciones adicionales posibles:

```text
usuarios
```

---

## Conclusión

El proyecto implementa una solución NoSQL documental para administrar un catálogo de películas y series. MongoDB permite representar cada producción como un documento independiente, lo cual simplifica el almacenamiento de datos flexibles y anidados.

La aplicación permite realizar operaciones CRUD sobre películas y series, además de generar los reportes solicitados para analizar el catálogo. Con esto se cumple el objetivo de diseñar e implementar un sistema de base de datos no relacional aplicado a un problema real de una plataforma de streaming.

La solución demuestra el uso de documentos, campos anidados, listas, consultas por criterios específicos y agregaciones, características importantes de MongoDB como base de datos NoSQL documental.