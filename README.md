# CourseQuest

## 1. Descripción del proyecto

CourseQuest es una aplicación que permite a los usuarios explorar formaciones relacionadas con la programación en distintos centros docentes de España. Los usuarios pueden registrarse en formaciones ofrecidas por diversas instituciones.

## 2. Diagrama de Clases
[Diagrama de clases](src/main/resources/static/Diagrama.png)

## 3. Configuración

**Clona el repositorio en tu GitHub**
```bash
   git clone https://github.com/daniorta/CourseQuest.git
```

Opción 1: Ve a **File > Open** y selecciona la carpeta del proyecto.

Opción 2: Usa la opción **Get from Version Control** con la URL del repositorio:
https://github.com/daniorta/CourseQuest.git

## 4. Configurar la base de datos

Crea una base de datos MySQL llamada `coursequest_db`.

Edita el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:

```properties
spring.application.name=CourseQuest
spring.datasource.url=jdbc:mysql://localhost:3306/coursequest_db
spring.datasource.username=tu_usuario_mysql
spring.datasource.password=tu_contraseña_mysql
spring.jpa.hibernate.ddl-auto=update
```
## 💻 Tecnologías Utilizadas

- Java 21 – Lenguaje principal

- Spring Boot – Framework backend

- MySQL – Base de datos relacional

- Spring Data JPA – Gestión de entidades

- Spring Security – Autenticación y autorización

- Lombok – Reducción de código repetitivo

- Hibernate – ORM para acceso a datos

- Maven – Gestión de dependencias

## 🧭 Estructura de Controladores y Rutas

Estructura general del proyecto:

src/
├── controller/
├── config/ 
├── dto/
├── enums/
├── exception/
├── model/
├── repository/
├── security/
├── service/

## Endpoints Principales

| Método | Ruta          | Descripción                 |
|--------|---------------|-----------------------------|
| GET    | /course       | Obtener lista de cursos     |
| POST   | /courses      | Crear un curso (solo admin) |
| GET    | /student/{id} | Ver perfil de student       |
| POST   | /enrollment   | Registro de nuevos usuarios |
| POST   | /api/login    | Inicio de sesión con JWT    |


## 🔗 Enlaces Adicionales

- 🗂️ [Enlace Trello](https://trello.com/b/D2o0aiFW/mi-tablero-de-trello)

- 📽️ Presentación del Proyecto: (Agrega enlace a presentación o demo)

## 🔮 Trabajo Futuro
- Desarrollar una aplicación frontend separada usando frameworks modernos como React o Angular para mejorar la experiencia de usuario.
- Sistema de comentarios y valoraciones en cursos.
- Optimizar el rendimiento y escalabilidad para manejar mayor número de usuarios simultáneos.
- Añadir integración con APIs externas para importar o sincronizar formaciones de otros proveedores.


## 📚 Recursos

- [Documentación Oficial Spring Boot](https://docs.spring.io/spring-boot/documentation.html)
- [Documentación oficial de MySQL](https://dev.mysql.com/doc/)
- [Guía oficial de Lombok](https://projectlombok.org/)
- [Autenticación con JWT y Spring Security (Baeldung)](https://www.baeldung.com/spring-security-oauth-jwt)
- [Documentación oficial de Spring Security](https://spring.io/projects/spring-security)


## 👨‍💻 Miembros del Equipo

**Daniel Orta – Desarrollador Backend Developer**

**GitHub: [DaniDev](https://github.com/daniorta)**

**Email: daniel_orta88@hotmail.com**