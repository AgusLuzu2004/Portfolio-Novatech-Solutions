# Estructura del Proyecto

La organización del repositorio es la siguiente:

```text
Portfolio-Novatech-Solutions
│
├── dataset
├── docs
├── java
├── powerbi
├── python
├── sql
├── README.md
└── LICENSE
```

## Carpeta java

Contiene la aplicación principal.

```text
src
│
├── main
│   ├── java
│   └── resources
│
└── test
```

Dentro de `main/java`:

```text
config/
controller/
dao/
model/
service/
util/
```

### config

Configuración de conexión a la base de datos (`Conexion`, `DbConfig`).

### controller

Controladores JavaFX.

### model

Clases del dominio.

### dao

Acceso a datos.

### service

Lógica de negocio.

### util

Clases auxiliares.

---

## Carpeta sql

Scripts para crear y poblar la base de datos.

---

## Carpeta python

Scripts para generar y validar datasets.

---

## Carpeta powerbi

Dashboard e informes.

---

## Carpeta docs

Documentación técnica del proyecto.
