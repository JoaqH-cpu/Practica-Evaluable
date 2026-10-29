![CI](https://github.com/JoaqH-cpu/Practica-Evaluable/actions/workflows/ci.yml/badge.svg)
# almacen-papeleria-api

API REST del almacén de una papelería, hecha con Spring Boot (Java 21), Spring Data JPA y PostgreSQL.
Permite consultar el catálogo de artículos y los datos básicos de cada uno, indicando si está agotado.

## Funcionalidades

- Listar todos los artículos del almacén.
- Consultar id, nombre y stock de un artículo; si no quedan unidades, la respuesta incluye `"mensaje": "Agotado"`.
- Respuesta 404 si el artículo no existe.

## Endpoints

| Método | Ruta | Descripción | Códigos |
|--------|------|-------------|---------|
| GET | `/api/articulos` | Todos los artículos con todos sus campos | 200 |
| GET | `/api/articulos/{id}/basico` | Id, nombre y stock de un artículo | 200, 404 |

### `GET /api/articulos`

```json
[
  { "id": 1, "nombre": "Bolígrafo azul", "precio": 0.45, "stock": 500, "categoria": "papelería" },
  { "id": 3, "nombre": "Grapadora metálica", "precio": 8.95, "stock": 0, "categoria": "oficina" }
]
```

### `GET /api/articulos/1/basico` (con stock)

```json
{ "id": 1, "nombre": "Bolígrafo azul", "stock": 500 }
```

### `GET /api/articulos/3/basico` (agotado)

```json
{ "id": 3, "nombre": "Grapadora metálica", "stock": 0, "mensaje": "Agotado" }
```

### `GET /api/articulos/99/basico` (no existe)

Respuesta `404 Not Found` sin cuerpo.

## Requisitos

- JDK 21
- Maven (se incluye el wrapper `mvnw`)
- PostgreSQL 17 (o Docker). Opcionalmente, sin PostgreSQL con el perfil `h2`.
- Git

## Instalación y ejecución en local

1. Clona el repositorio y entra en la carpeta:
   ```bash
   git clone <url-del-repositorio>
   cd almacen-papeleria-api
   ```
2. Si falta `pom.xml`/`mvnw`, genera el esqueleto en https://start.spring.io (Maven, Java 21,
   group `com.hellin`, artifact `almacen`, dependencias Spring Web, Spring Data JPA, PostgreSQL Driver
   y H2) y copia encima el contenido de este proyecto.
3. Arranca PostgreSQL con **una** de estas opciones:
   - **Docker:**
     ```bash
     docker run --name pg-almacen -e POSTGRES_USER=almacen -e POSTGRES_PASSWORD=cambiame -e POSTGRES_DB=almacen -p 5432:5432 -d postgres:17
     ```
   - **PostgreSQL instalado** (https://www.postgresql.org/download/): crea el usuario `almacen` y la base de datos `almacen`.
4. Define la contraseña como variable de entorno (nunca se guarda en el código) y ejecuta:
   ```bash
   # Linux / macOS / Git Bash
   export DB_PASSWORD=cambiame
   ./mvnw spring-boot:run
   ```
   ```powershell
   # Windows PowerShell
   $env:DB_PASSWORD="cambiame"
   .\mvnw spring-boot:run
   ```
   Variables opcionales: `DB_HOST` (por defecto `localhost`), `DB_NAME` y `DB_USER` (por defecto `almacen`).
5. Prueba la API:
   ```bash
   curl http://localhost:8080/api/articulos
   curl http://localhost:8080/api/articulos/3/basico
   ```

Al arrancar se crean la tabla y los datos de ejemplo (`schema.sql` y `data.sql`).

### Sin PostgreSQL (base de datos en memoria)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

### Pruebas automáticas

```bash
SPRING_PROFILES_ACTIVE=h2 ./mvnw verify          # Linux / macOS / Git Bash
$env:SPRING_PROFILES_ACTIVE="h2"; .\mvnw verify  # Windows PowerShell
```

## Documentación del código (Javadoc)

```bash
./mvnw javadoc:javadoc
```

Se genera en `target/reports/apidocs/index.html` (en `target/site/apidocs` según la versión del plugin).

## Estructura

```
src/main/java/com/hellin/almacen/
  controller/  ArticuloController   (endpoints REST)
  service/     ArticuloService      (lógica: mensaje "Agotado")
  repository/  ArticuloRepository   (acceso a datos con JPA)
  model/       Articulo             (entidad)
src/main/resources/  application*.properties, schema.sql, data.sql
```

## Equipo y flujo de trabajo

| Integrante | Código | Documentación |
|------------|--------|---------------|
| Joaq | `listarTodos()` en el servicio + integración continua | Wiki: Home |
| Rafa | `GET /api/articulos` en el controlador | Wiki: Endpoints |
| Elliot | `buscarBasico()` en el servicio (404 y "Agotado") | Wiki: Modelo de datos |
| Juanma | `GET /api/articulos/{id}/basico` en el controlador | Wiki: Guía de uso + README |

### Flujo de trabajo

- Cada tarea es una **issue** con etiqueta (`historia`, `tarea`, `docs`, `bug`), estimación en horas y asignada al **Sprint 1** en el [tablero del proyecto](https://github.com/users/JoaqH-cpu/projects/1).
- Ramas:
  - `main`: versiones publicadas (v1.0.0).
  - `develop`: rama de integración y rama por defecto.
  - `feature/N-descripcion`: una rama por tarea, creada desde `develop`, donde `N` es el número de la issue.
- Commits en formato convencional citando la issue: `feat: implementa listarTodos (#13)`.
- Cada rama se integra mediante **pull request hacia `develop`** con `Closes #N` en la descripción.
- Cada PR la revisa otro compañero (comentario y aprobación) antes de fusionarla.
- `develop` y `main` están protegidas: no se puede fusionar si el check **CI - Compilar** falla.
- Al terminar el sprint se fusiona `develop` en `main` y se etiqueta la versión (`v1.0.0`).

## Uso de IA

| Integrante | Herramienta | Para qué |
|------------|-------------|----------|
| Joaq | Claude (Anthropic) | Interpretar el enunciado, resolver errores de Git (push rechazado, merges), configurar `JAVA_HOME`, redactar el workflow de CI y revisar el estado del repositorio. |
| Rafa | *(rellenar)* | *(rellenar)* |
| Elliot | *Codex (Open IA)* | He implementado el método buscarBasico() de ArticuloService y he utilizado la IA, resolver errores y comprobar la compilación del proyecto.|
| Juanma | *(rellenar)* | *(rellenar)* |

No se han introducido contraseñas ni claves en ninguna herramienta de IA.
