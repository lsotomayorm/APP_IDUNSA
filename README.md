# 📱 APP IDUNSA

## 👥 Equipo de Trabajo

**Nombre del Proyecto:** APP IDUNSA  
**Integrantes del Equipo:**
- Paul Jesus Quispe Morocco (el mas gozu)
- Luis Alberto Sotomayor Moya

---

## 🎯 Propósito del Proyecto

El propósito de APP IDUNSA es **facilitar la gestión de eventos deportivos universitarios** mediante una aplicación móvil. Esta herramienta busca centralizar la información de torneos, inscripciones, equipos y resultados, promoviendo así una mejor organización y participación estudiantil.

**APP IDUNSA** es una aplicación móvil desarrollada con el propósito de **optimizar la gestión de eventos deportivos universitarios** a través de una plataforma centralizada, intuitiva y accesible. Su diseño está enfocado en resolver problemáticas comunes en la organización de torneos estudiantiles, como la dispersión de información, la falta de seguimiento estructurado y la baja participación.

La solución permite gestionar de forma eficiente torneos, equipos, inscripciones y resultados, brindando tanto a organizadores como a estudiantes una herramienta integral que mejora la experiencia de usuario, reduce la carga operativa y promueve una mayor participación.

Desde una perspectiva técnica, el proyecto se construye sobre una arquitectura robusta y mantenible, guiada por principios SOLID, prácticas de Clean Code y convenciones de codificación propias del lenguaje y del framework. La estructura del backend, desarrollada con Spring Boot, sigue el enfoque de Domain-Driven Design (DDD), lo que permite una separación clara de responsabilidades, mayor cohesión entre modelos de negocio y lógica de aplicación, y facilita la escalabilidad del sistema. En el frontend, implementado con Android Studio, se aplican patrones de diseño modernos y componentes reutilizables orientados a una experiencia de usuario fluida y consistente. Esta base técnica prepara la plataforma para integraciones futuras con servicios externos, autenticación segura y sistemas de analítica avanzada, garantizando sostenibilidad a largo plazo.

---

## ⚙️ Funcionalidades

### Diagrama de Casos de Uso (UML)

<!-- Inserta aquí tu imagen del Diagrama de Casos de Uso -->
![Casos de Uso](./docs/img/)


---

### Prototipo de la Interfaz de Usuario (GUI)

<!-- Inserta aquí tu imagen del prototipo de interfaz o capturas de pantalla de la app -->
![Prototipo GUI](./docs/img/PROTOTIPO_INTERFACES_1.png)

![Prototipo GUI](./docs/img/PROTOTIPO_INTERFACES_2.png)

Las interfaces fueron desarrolladas en [Figma](https://www.figma.com/proto/KOyBwW53udcyf76ax6r5Hf/IDUNSA-APP-MOBILE?node-id=5-230&p=f&t=O2pWYph2g76XQQkZ-1&scaling=min-zoom&content-scaling=fixed&page-id=0%3A1&starting-point-node-id=5%3A230)

---

## 🧩 Modelo de Dominio

### Diagrama de Clases

<!-- Inserta aquí el diagrama de clases -->
![Casos de Uso](./docs/img/Dominio_StraUML.png)

La arquitectura del sistema está modelada bajo un enfoque de **Domain-Driven Design (DDD)** y sigue una estructura **en capas**, lo que permite una clara separación de responsabilidades, facilita la escalabilidad y mejora el mantenimiento del sistema.

### 🔹 Capa de Dominio

Contiene la lógica de negocio central del sistema:

- **Módulo Torneo**: Define entidades, reglas y comportamientos relacionados con torneos (partidos, fechas, fixture, participantes).
- **Módulo Usuario**: Modela los roles del sistema como estudiantes y organizadores.
- **Servicios de Dominio**: Operaciones que no pertenecen a una sola entidad pero representan lógica del negocio.
- **Fábricas**: Encargadas de la creación controlada de entidades agregadas respetando invariantes del dominio.

### 🔹 Capa de Persistencia (Repositorios)

Implementada con **JPA**, esta capa abstrae el acceso a la base de datos:

- **Interfaces de Repositorio**: Declaraciones que representan contratos del dominio.
- **Implementaciones JPA**: Adaptadores que traducen las operaciones del dominio a consultas de base de datos.

### 🔹 Capa de Aplicación (Servicios)

Encargada de orquestar los casos de uso del sistema:

- **Servicios de Aplicación**: Coordinan la ejecución de lógica de negocio, sin contener lógica del dominio directamente.

### 🔹 Capa de Presentación

Expone la funcionalidad del sistema a los usuarios:

- **Controladores REST (Spring Boot)**: Gestionan solicitudes HTTP y exponen los endpoints del sistema.
- **Aplicación Móvil (Android Studio)**: Interfaz gráfica que consume los servicios y proporciona una experiencia de usuario intuitiva.

---

Esta arquitectura promueve el desarrollo modular, desacoplado y orientado a negocio, y sienta las bases para futuras integraciones, autenticación segura y analítica en tiempo real.

---

## 🏗️ Visión General de Arquitectura

### Arquitectura Aplicada

Mediante la estructura de un proyecto bajo el marco de trabajo SpringBoot se aplicó el enfoque **Domain-Driven Design (DDD)** y **Arquitectura Limpia** para la separación de responsabilidades y escalabilidad.

```plaintext
src/
└── main/
    ├── java/
    │   └── com/
    │       └── idunsa/
    │           └── app/
    │               ├── AppIdunsaApplication.java  # Clase principal (Spring Boot)
    │
    │               ├── dominio/                   # 🧠 Capa de dominio (pura)
    │               │   ├── torneo/
    │               │   │   ├── Torneo.java
    │               │   │   ├── Partido.java
    │               │   │   ├── TorneoFactory.java
    │               │   │   └── TorneoService.java
    │               │   ├── usuario/
    │               │   │   ├── Usuario.java
    │               │   │   └── RolUsuario.java
    │               │   └── comunes/
    │               │       └── Identificador.java
    │
    │               ├── aplicacion/                # 🔁 Capa de aplicación (casos de uso)
    │               │   ├── servicios/
    │               │   │   └── GestionTorneoService.java
    │               │   └── dtos/
    │               │       └── CrearTorneoDTO.java
    │
    │               ├── infraestructura/           # 🗃️ Adaptadores de salida (JPA, APIs, etc.)
    │               │   ├── persistencia/
    │               │   │   ├── repositorios/
    │               │   │   │   └── TorneoJpaRepository.java
    │               │   │   └── entidades/
    │               │   │       └── TorneoEntity.java
    │               │   └── configuracion/
    │               │       └── PersistenciaConfig.java
    │
    │               ├── interfaz/                  # 🌐 Adaptadores de entrada (controladores)
    │               │   ├── rest/
    │               │   │   └── TorneoController.java
    │               │   └── excepciones/
    │               │       └── ManejadorGlobal.java
    │
    │               └── configuracion/             # ⚙️ Config global de Spring (CORS, beans, etc.)
    │                   └── SeguridadConfig.java
    │
    └── resources/
        ├── application.yml
        └── templates/ (si usas Thymeleaf, por ejemplo)
```

### Diagrama de Paquetes

<!-- Inserta aquí tu diagrama de paquetes -->
La estructura del proyecto sigue una arquitectura en capas organizada en paquetes, orientada a principios de Clean Architecture y DDD:

- `com.idunsa.backend.controller`  
  Contiene los **controladores REST** que exponen los endpoints HTTP. Representa la **capa de presentación** o interfaz del sistema.

- `com.idunsa.backend.domain`  
  Incluye las **entidades del dominio** y la **lógica de negocio central**, como modelos, agregados, y servicios del dominio. Esta capa es **independiente** de frameworks y bibliotecas externas.

- `com.idunsa.backend.dto`  
  Contiene los **Data Transfer Objects** que permiten la comunicación entre capas, evitando exponer directamente las entidades del dominio.

- `com.idunsa.backend.repository`  
  Define las **interfaces de persistencia** (repositorios) y sus implementaciones, generalmente integradas con JPA/Hibernate. Representa la infraestructura de acceso a datos.

- `com.idunsa.backend.service`  
  Contiene la **lógica de aplicación**, orquestando el flujo entre controladores, dominio y repositorios. Aquí se ubican los servicios que implementan los casos de uso del sistema.

### Diagrama de Clases de Arquitectura

<!-- Inserta aquí el diagrama de clases a nivel de arquitectura -->
![Clases de Arquitectura](./docs/arquitectura_clases.png)

```plaintext
classDiagram
    Controller <|-- TorneoController
    Controller <|-- EncuentroController
    Controller <|-- EquipoController
    Controller <|-- DeporteController

    Service <|-- TorneoService
    Service <|-- EncuentroService
    Service <|-- EquipoService
    Service <|-- DeporteService

    TorneoService --> TorneoRepository
    EncuentroService --> EncuentroRepository
    EquipoService --> EquipoRepository
    DeporteService --> DeporteRepository

    TorneoService --> Torneo
    EncuentroService --> Encuentro
    EquipoService --> Equipo
    DeporteService --> Deporte

    TorneoController --> TorneoService
    EncuentroController --> EncuentroService
    EquipoController --> EquipoService
    DeporteController --> DeporteService

    Torneo <..> TorneoRequestDTO
    Torneo <..> TorneoResponseDTO
    Equipo <..> EquipoRequestDTO
```

### 🧠 Prácticas de Desarrollo Aplicadas:

#### 🔹 Estilos de Programación:

##### 1. Persistent-Tables

```kotlin
@Entity
data class Torneo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var nombre: String = "",
    var fecha: LocalDate = LocalDate.now(),
    var hora: LocalTime = LocalTime.now(),
    var direccion: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    var evento: Evento? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deporte_id", nullable = false)
    var deporte: Deporte? = null,

    @Column(columnDefinition = "TEXT")
    var reglamento: String? = null

)
```

El uso de la anotación @Entity de JPA/Hibernate, que indica que la clase Torneo representa una tabla persistente en una base de datos relacional. Las anotaciones como @Id, @GeneratedValue, @ManyToOne, y @JoinColumn son típicas del mapeo objeto-relacional (ORM), que es característico del estilo Persistent-Tables.

##### 2. Things

```kotlin
data class TorneoRequestDTO(
    val nombre: String,
    val fecha: String,
    val hora: String,
    val direccion: String,
    val eventoId: Long,
    val deporteId: Int 
)
```

El código define un DTO (Data Transfer Object), que es una estructura de datos simple utilizada para transportar información entre procesos, capas o servicios. No contiene lógica de negocio ni comportamiento, solo datos agrupados, lo cual es característico del estilo Things (objetos que representan cosas o datos).

##### 3. RESTful

```kotlin
@RestController
@RequestMapping("/api/deportes")
class DeporteController(
    private val deporteService: DeporteService
) {

    @GetMapping
    fun obtenerDeportes(): List<DeporteResponseDTO> {
        return deporteService.obtenerTodos()
    }

    @GetMapping("/buscar")
    fun obtenerDeportePorNombre(@RequestParam nombre: String): ResponseEntity<DeporteResponseDTO> {
        val deporte = deporteService.obtenerPorNombre(nombre)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(deporte)
    }
}
```

El uso de anotaciones como @RestController, @RequestMapping, @GetMapping indica que este controlador expone una API web siguiendo el estilo arquitectónico REST. Los métodos corresponden a endpoints HTTP (GET), devolviendo recursos o respuestas HTTP (ResponseEntity).

##### 4. Error/Exception Handling

```kotlin
class TorneoService(
    private val torneoRepository: TorneoRepository,
    private val eventoRepository: EventoRepository,
    private val deporteRepository: DeporteRepository

) {

    fun crearTorneo(request: TorneoRequestDTO): TorneoResponseDTO {
        val evento = eventoRepository.findById(request.eventoId)
            .orElseThrow { RuntimeException("Evento con ID ${request.eventoId} no encontrado") }

        val deporte = deporteRepository.findById(request.deporteId)
            .orElseThrow { RuntimeException("Deporte con ID ${request.deporteId} no encontrado") }
        .
        .
        .
}
```

Se utiliza el manejo de excepciones para controlar errores, por ejemplo, con orElseThrow { RuntimeException("...") } cuando no se encuentra un recurso en la base de datos.

#### 🔹 Prácticas de Codificación Limpia - Clean Code:

##### 1. Definir constantes

![Clean Code 1](./docs/img/CleanCode1.png)

Es una práctica de código limpio porque:
**- Evita duplicación:** La regla DRY (Don’t Repeat Yourself) es clave en clean code.
**- Facilita mantenimiento:** Cambias el mensaje en un solo lugar.
**- Mejora la legibilidad:** Mensajes descriptivos y centralizados.
**- Preparación para internacionalización:** Fácil adaptar el código para múltiples idiomas.

##### 2. Funciones cortas y con una sola responsabilidad

```kotlin
fun obtenerTorneosPorEvento(eventoId: Long): List<TorneoResponseDTO> {
    return torneoRepository.findByEventoId(eventoId)
        .map { convertirAResponseDTO(it) }
}
```

##### 3. Estructura de Código Fuente



##### 4. Estructura de Datos

```kotlin
val torneo = Torneo(
    nombre = request.nombre,
    fecha = LocalDate.parse(request.fecha),    
    hora = LocalTime.parse(request.hora),       
    direccion = request.direccion,
    evento = evento,
    deporte = deporte
)

val guardado = torneoRepository.save(torneo)
```

##### 5. Tratamiento de Errores

```kotlin
fun obtenerDeportePorNombre(@RequestParam nombre: String): ResponseEntity<DeporteResponseDTO> {
    val deporte = deporteService.obtenerPorNombre(nombre)
        ?: return ResponseEntity.notFound().build()
    return ResponseEntity.ok(deporte)
}
```

### ⚙️ Gestión de Proyecto:

Las gestiónes de desarrollo en equipos se realizaron con la herramienta Trello:

![Trello de Proyecto](./docs/img/Trello.png)

Enlace a [Trello](https://trello.com/invite/b/682c0773f278198ff928e567/ATTIf7f6967ec48dcb9369f59aa235ceb7689F4B2953/idunsa-app-gestion-de-eventos-deportivos) del proyecto.
