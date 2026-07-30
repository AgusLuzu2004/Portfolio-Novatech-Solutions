# Arquitectura del Sistema

## Descripción

NovaTech Solutions utiliza una arquitectura en capas (Layered Architecture), separando la interfaz de usuario, la lógica de negocio y el acceso a datos.

Esta organización facilita el mantenimiento, la reutilización del código y la escalabilidad del proyecto.

---

## Arquitectura General

```text
JavaFX (Interfaz)

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

---

## Capas

### JavaFX

Es la capa de presentación.

Responsabilidades:

- Mostrar la interfaz gráfica.
- Recibir acciones del usuario.
- Mostrar mensajes y resultados.

No contiene lógica de negocio.

---

### Controller

Recibe los eventos de la interfaz y coordina la comunicación con la capa Service.

Ejemplos:

- ClienteController
- ProductoController
- VentaController

---

### Service

Contiene la lógica de negocio.

Responsabilidades:

- Validaciones.
- Reglas del sistema.
- Cálculos.
- Coordinación entre DAOs.

Ejemplos:

- VentaService calcula el total de una venta.
- ClienteService valida los datos antes de guardar.

---

### DAO

(Data Access Object)

Responsable del acceso a la base de datos.

Funciones:

- Consultas SQL.
- Inserciones.
- Actualizaciones.
- Eliminaciones.

Toda la comunicación con MySQL se realiza desde esta capa.

---

### Base de Datos

Motor utilizado:

- MySQL 8

Contiene las tablas:

- Clientes
- Productos
- Categorías
- Empleados
- Sucursales
- Ventas
- Usuarios
- Auditoría

---

## Flujo de una operación

Ejemplo: Registrar una venta

1. El usuario completa el formulario.
2. VentaController recibe la acción.
3. VentaService valida la información.
4. VentaDAO registra la venta.
5. MySQL almacena los datos.
6. El resultado vuelve al usuario.

---

## Beneficios

- Separación de responsabilidades.
- Código más organizado.
- Mayor facilidad para realizar pruebas.
- Mejor mantenimiento.
- Escalabilidad.
