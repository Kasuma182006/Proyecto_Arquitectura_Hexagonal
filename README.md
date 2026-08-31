
---
# API para gestión de personas

### 🚀 API de Gestión de Personas - Arquitectura Hexagonal - Andrés Stiven Velasquez Romo

## Descripción:
Este proyecto es una aplicación **Spring Boot** construida bajo los principios de la **Arquitectura Hexagonal (Puertos y Adaptadores)**. Proporciona una API REST completa para la gestión, consulta, registro, edición y eliminación de registros de personas, integrada con persistencia en **PostgreSQL**, pruebas automáticas y despliegue contenedorizado con **Docker**.

---
## 🛠️ Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 4.1.0** (Spring Web, Spring Data JPA, Validation, WebServices).
* **Spring Security** (Protección y seguridad de endpoints).
* **PostgreSQL 15** (Base de datos relacional principal).
* **Flyway 10.15.2** (Control de versiones y migraciones automáticas de base de datos).
* **H2 Database** (Base de datos en memoria para entornos de pruebas de integración).
* **Bucket4j 8.12.0** (Rate Limiting y control de frecuencia de peticiones HTTP).
* **Springdoc OpenAPI / Swagger 3 (2.8.5)** (Documentación e interfaz interactiva de la API).
* **Lombok** (Generación automática de código mediante anotaciones).
* **Docker & Docker Compose** (Empaquetado Multi-Stage y orquestación con PostgreSQL).

---

## 📐 Arquitectura del Proyecto

El código sigue estrictamente la separación de responsabilidades guiada por la **Arquitectura Hexagonal**:

```text
com.example.demo
 ├── domain                            # NÚCLEO DE DOMINIO (Reglas de negocio puro)
 │    ├── model                        # Entidades del dominio (ej. Persona)
 │    └── ports                        # Interfaces / Contratos
 │         ├── in                      # Puertos de entrada (ej. PersonaServiceInt)
 │         └── out                     # Puertos de salida (ej. PersonaRepositoryPort)
 |
 |── application
 |       └─ UseCasesServices            #Casos de uso de la aplicación
 |   
 └── infrastructure                    # 🔌 INFRAESTRUCTURA (Adaptadores externos)
      └── adapters
           ├── config                  # Configuraciones del proyecto(JpaConfig + BeanConfig)
           └── web                     # Adaptador de entrada Web (REST API)
                ├── data
                │    ├── dto           # Objetos de transferencia de datos (PersonaDto
                |    |                                                       QueryParams, etc.)
                │    |── mappers       # Mapeadores (MapStruct) entre Dominio y DTO
                |    |── entity        # Clases que representan las tablas en la base de datos
                |    |── repository    # Persistencia JPA + hibernate
                |    |── specification # Genereador de condiciones dinamica para filtrar personas
                |─── in
                |     |── controllers   # Controladores REST (PersonaController)
                |              |
                |              └─ controllerAdvice  # Manejador central de errores  
                |── out            
                     └─ Adaptadores de persistencia            
```

---

## 🐳 Despliegue Local con Docker Compose

El proyecto está preparado para ejecutarse completamente contenedorizado con **un solo comando**, utilizando una construcción *Multi-Stage*:

### Prerrequisitos

* Tener instalado **Docker Desktop** o **Rancher Desktop**.

### Instrucciones de Ejecución

1. Clona el repositorio y ubícate en la raíz del proyecto:
```bash
git clone <URL_DEL_REPOSITORIO>
cd demo

```


2. Levanta los servicios (Aplicación Spring Boot + PostgreSQL):
```bash
docker compose up -d --build

```


3. Revisa el estado de los contenedores:
```bash
docker compose ps

```


4. Para apagar los servicios y detener la base de datos:
```bash
docker compose down

```
---

## 🗄️ Migraciones de Base de Datos (Flyway)

El proyecto utiliza **Flyway** para el control de versiones y evolución del esquema de la base de datos. No es necesario ejecutar scripts DDL (`CREATE TABLE`, `ALTER TABLE`, etc.) de forma manual; el sistema los gestiona automáticamente.

* **Ejecución automática:** Al iniciar la aplicación (ya sea localmente o mediante Docker Compose), Flyway se conecta a PostgreSQL, verifica el estado actual de la base de datos y ejecuta cualquier script pendiente de forma segura.
* **Ubicación de los scripts:** Todos los archivos de migración se encuentran en el directorio `src/main/resources/db/migration`.
* **Convención de nomenclatura:** Si necesitas modificar la base de datos en el futuro, debes crear un nuevo archivo siguiendo estrictamente el estándar de Flyway:
  * Formato: `V{version}__{descripcion_en_minusculas}.sql`
  * Ejemplo: `V2__agregar_columna_telefono_a_persona.sql`
  * *(Nota: Son dos guiones bajos `__` separando la versión de la descripción).*


## 📑 Documentación de la API (Endpoints)

Ruta base del controlador: `/persona`

| Método | Endpoint | Descripción | Códigos HTTP de Respuesta |
| --- | --- | --- | --- |
| **GET** | `/persona/listar-personas` | Obtiene el listado completo de personas sin filtros ni paginación. | `200 OK` |
| **GET** | `/persona/buscar-persona/{id}` | Busca la información detallada de una persona por su ID único. | `200 OK`, `404 Not Found` |
| **POST** | `/persona/crear-persona` | Registra una nueva persona en el sistema. | `201 Created`, `400 Bad Request`, `409 Conflict` (Email duplicado) |
| **PUT** | `/persona/editar-persona/{id}` | Actualiza los datos de una persona existente identificada por su ID. | `200 OK`, `400 Bad Request`, `404 Not Found`, `409 Conflict` |
| **DELETE** | `/persona/eliminar-persona/{id}` | Elimina permanentemente el registro de una persona por su ID. | `204 No Content`, `404 Not Found` |
| **GET** | `/persona/buscar-filtro` | Búsqueda dinámica con filtros avanzados y respuesta paginada (`QueryParams`). | `200 OK` |

---

## 🔍 Swagger / OpenAPI UI

Una vez iniciada la aplicación, puedes acceder a la interfaz interactiva de Swagger para probar todos los endpoints directamente desde tu navegador:

👉 **URL de Swagger:** [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

👉 **Endpoint de Health Check:** [http://localhost:8081/ceiba/health](http://localhost:8081/ceiba/health)



---

## 🧪 Pruebas Automatizadas

Las pruebas unitarias y de integración se ejecutan utilizando **H2 en memoria** durante el proceso de compilación del `Dockerfile` (Etapa `tester`).

Para ejecutar las pruebas localmente con Maven:

```bash
mvn test

```

Para forzar la reconstrucción verificando los tests dentro del contenedor Docker:

```bash
docker compose build --progress=plain

```

---

## ⚙️ Variables de Entorno

Configuradas por defecto en el archivo `docker-compose.yml`:

| Variable | Valor por Defecto | Descripción |
| --- | --- | --- |
| `DB_URL` | `jdbc:postgresql://postgres-db:5432/ceibadb` | URL de conexión JDBC a PostgreSQL |
| `DB_USERNAME` | `postgres` | Usuario de la base de datos |
| `DB_PASSWORD` | `postgrespassword` | Contraseña de la base de datos |

# 📚 Documentación de API: Colección "ceiba"

Esta es la documentación estructurada de los endpoints extraídos de la colección de Postman.

---

## 🔍 Detalle de cada Petición

### 1. Buscar con Paginación y Orden
* **Método:** `GET`
* **URL:** `http://localhost:8081/ceiba/persona/buscar-filtro?anio=2026&orden=email&direccion=desc&pagina=1&tamano=5`
* **Query Params:** `anio=2026`, `orden=email`, `direccion=desc`, `pagina=1`, `tamano=5`
* **Autenticación:** No Auth

---

### 2. Crear Persona
* **Método:** `POST`
* **URL:** `http://localhost:8081/ceiba/persona/crear-persona`
* **Body (JSON):**
```json
{
    "nombre": "andrew",
    "apellido": "velasquez",
    "email": "zmail@gmail.com",
    "fechaNacimiento": "2026-05-26"
}
```

---

### 3. Buscar Persona por Apellido
* **Método:** `GET`
* **URL:** `http://localhost:8081/ceiba/persona/buscar-filtro?apellido=velasquez`
* **Query Params:** `apellido=velasquez`

---

### 4. Eliminar Persona por ID
* **Método:** `DELETE`
* **URL:** `http://localhost:8081/ceiba/persona/eliminar-persona/28`
* **Parámetro de Ruta:** `id = 28`

---

### 5. Buscar Persona por ID (Singular)
* **Método:** `GET`
* **URL:** `http://localhost:8081/ceiba/persona/buscar-persona/28`
* **Parámetro de Ruta:** `id = 28`

---

### 6. Editar Persona
* **Método:** `PUT`
* **URL:** `http://localhost:8081/ceiba/persona/editar-persona/25`
* **Parámetro de Ruta:** `id = 25`
* **Body (JSON):**
```json
{ 
    "id": 25,
    "nombre": "andrew",
    "apellido": "stiven",
    "email": "andres@gmail.com",
    "fechaNacimiento": "2026-06-01"
}
```

---

### 7. Listar Todas las Personas
* **Método:** `GET`
* **URL:** `http://localhost:8081/ceiba/personas/listar-personas`

---

### 8. Buscar Persona por ID (Plural)
* **Método:** `GET`
* **URL:** `http://localhost:8081/ceiba/personas/buscar-persona/10`
* **Parámetro de Ruta:** `id = 10`
