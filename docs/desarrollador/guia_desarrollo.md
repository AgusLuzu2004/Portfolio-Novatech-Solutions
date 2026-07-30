# Guía para Desarrolladores

## Introducción

Este proyecto fue desarrollado como parte de un portafolio personal con el objetivo de simular un sistema de gestión comercial para una empresa ficticia llamada NovaTech Solutions.

La aplicación está desarrollada siguiendo una arquitectura en capas para facilitar el mantenimiento y la escalabilidad.

---

## Arquitectura

El proyecto utiliza la siguiente estructura:

```text
JavaFX
    │
    ▼
Controller
    │
    ▼
Service
    │
    ▼
DAO
    │
    ▼
MySQL
```

### Controller

Recibe los eventos de la interfaz y coordina las acciones del usuario.

### Service

Contiene la lógica de negocio y las validaciones.

### DAO

Realiza las operaciones sobre la base de datos mediante JDBC.

### Model

Representa las entidades del sistema.

---

## Flujo de trabajo

Cuando se implementa una nueva funcionalidad, se recomienda seguir el siguiente orden:

1. Crear el modelo.
2. Crear el DAO.
3. Crear el Service.
4. Crear el Controller.
5. Diseñar la vista en JavaFX.
6. Agregar pruebas unitarias.
7. Documentar el cambio.

---

## Tecnologías utilizadas

- Java 21
- JavaFX
- MySQL
- JDBC
- Maven
- JUnit 5
- Mockito
- Logback
