## Descripción

**Foro Hub** es una API RESTful desarrollada para gestionar un foro en línea, donde los usuarios pueden interactuar mediante la creación, edición, eliminación y visualización de *tópicos*. Este proyecto implementa un CRUD completo y sigue las mejores prácticas de desarrollo en Java y Spring Boot.

La persistencia de datos se gestiona a través de una base de datos MySQL, y se incluyen características como autenticación y autorización con tokens JWT, asegurando un manejo seguro y eficiente de las sesiones de usuario.


##  Funcionalidades principales

1. **Autenticación y registro de usuarios**
   - Registro e inicio de sesión seguros utilizando credenciales encriptadas con BCrypt.
   - Generación y validación de tokens JWT para gestionar sesiones de usuario.
   - Acceso restringido a endpoints protegidos solo para usuarios autenticados.
  

2. **Gestión de tópicos**
   - Creación, visualización, actualización y eliminación de tópicos en el foro.
   - Restricción de duplicados basada en título y mensaje únicos por usuario.

4. **Manejo de errores**
   - Validación de datos enviados por los usuarios.
   - Respuesta clara con códigos HTTP estándar (400, 404, 409, etc.) y mensajes explicativos.

      ```json
      {
        "ok": false,
        "status": 400,
        "message": "El campo 'titulo' es obligatorio.",
        "data": null
      }
      ``` 

5. **Seguridad**
   - Protección de rutas con autenticación mediante JWT.
   - Restricción de acceso a usuarios no autenticados (HTTP 403 Forbidden).

      ```json
        {
          "ok": false,
          "status": 404,
          "message": "El recurso solicitado no fue encontrado.",
          "data": null  
        }
      ```

---

## Tecnologías utilizadas

- **Java**: Lenguaje de programación principal utilizado para implementar la lógica del backend.
- **Spring Boot**: Framework que simplifica el desarrollo de aplicaciones Java. Incluye:
  - *Spring Security*: Manejo de autenticación y autorización mediante tokens JWT.
  - *Spring Data JPA*: Para la integración con la base de datos relacional PostgreSQL.
  - *Spring Web*: Gestión de peticiones HTTP RESTful.
  - *Spring Boot DevTools*: Herramienta para reinicio automático durante el desarrollo.
- **MySQL**: Base de datos relacional utilizada para la persistencia de los datos.
- **JWT (JSON Web Tokens)**: Para la autenticación basada en tokens seguros.
- **Maven**: Herramienta de construcción y gestión de dependencias del proyecto.
- **Validation**: Validación de datos en los DTOs para garantizar integridad y consistencia.
- **Flyway Migration**: Gestión de versiones y migraciones de la base de datos.
- **Auth0**: Solución de autenticación y autorización simplificada con soporte para usuarios y roles.

---

##  Endpoints principales
### Base URL:  /api
### Autenticación de usuarios
| Método | Endpoint         | Descripción                         | Autenticación |
|--------|------------------|------------------------------------ |---------------|
| POST   | `/auth/register` | Registro de un nuevo usuario        | ❌            |
| POST   | `/auth/login`    | Inicio de sesión y generación de JWT| ❌            |


### Tópicos
| Método | Endpoint          | Descripción                              | Autenticación |
|--------|-------------------|------------------------------------------|---------------|
| GET    | `/topicos`        | Obtener todos los tópicos                | ✅            |
| POST   | `/topicos`        | Crear un nuevo tópico                    | ✅            |
| PUT    | `/topicos/{id}`   | Actualizar un tópico existente           | ✅            |
| DELETE | `/topicos/{id}`   | Eliminar un tópico                       | ✅            |


---

## Configuración del Proyecto 
### 1. Requisitos Previos

  Para ejecutar el proyecto en tu máquina local, necesitas tener instalados los siguientes programas:

  * Java 17 o superior.
  * Maven: Para la gestión de dependencias y construcción del proyecto.
  * PostgreSQL: Para la base de datos.
  * IDE recomendado: IntelliJ IDEA o Eclipse (para facilitar el desarrollo).
  * Git: Para la clonación y gestión del código fuente.

### 2. Clonación del repositorio


  ```bash
      git clone <URL_DEL_REPOSITORIO>
  ```

### 3. Configuración de la base de datos
 1. Crea una base de datos en PostgreSQL, por ejemplo, foro_hub.
 2. Copia el archivo application-template.properties a application.properties y configura las siguientes variables de entorno:

     ```properties
      # Nombre de la aplicación
      spring.application.name=Foro Hub

      # Configuración de la base de datos
      # La URL de conexión a la base de datos PostgreSQL. Asegúrate de reemplazar <database_name> por el nombre real de tu base de datos.
      spring.datasource.url=jdbc:postgresql://localhost:5432/<database_name>
      # Nombre de usuario para conectarse a la base de datos
      spring.datasource.username=<username>
      # Contraseña para conectarse a la base de datos
      spring.datasource.password=<password>

      # Configuración de JPA e Hibernate
      # Establece el comportamiento de Hibernate para la creación y actualización de tablas en la base de datos.
      spring.jpa.hibernate.ddl-auto=update
      # Muestra las consultas SQL ejecutadas en la consola
      spring.jpa.show-sql=true
      # Formatea las consultas SQL para que sean más legibles
      spring.jpa.properties.hibernate.format_sql=true
      # Configura si Hibernate debe mantener una sesión abierta durante toda la solicitud
      # spring.jpa.open-in-view=false

      # Configuración de seguridad (JWT)
      # Secret para la creación y validación de los tokens JWT. Asegúrate de configurar una clave secreta segura.
      api.security.secret=${JWT_SECRET:< JWT_SECRET >}

      # Configuración de las credenciales predeterminadas de usuario para Spring Security
      spring.security.user.name=<username>
      spring.security.user.password=<password>

      # Configuración de la API
      # Ruta base de la API
      server.servlet.context-path=/api

    ```

Recuerda reemplazar los valores `<database_name>`, `<username>`, `<password>`, y `<JWT_SECRET>` con los valores reales para tu entorno.


### 4. Instalación de dependencias
 1. Abre una terminal en la raíz del proyecto.
 2. Ejecuta el siguiente comando para descargar las dependencias del proyecto:
        `
        mvn clean install
        `
### 5. Ejecutar el Proyecto
Para ejecutar el proyecto, usa el siguiente comando en la terminal:
    `
    mvn spring-boot:run
    `