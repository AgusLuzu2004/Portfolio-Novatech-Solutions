# UML - NovaTech Solutions

## Introducción

Este documento describe los diagramas UML utilizados para el desarrollo del sistema NovaTech Solutions.

Los diagramas fueron realizados para representar la arquitectura, las relaciones entre clases y el flujo de las principales funcionalidades del sistema.

---

## Diagramas incluidos

### 1. Diagrama de Clases

Archivo:

- diagrama_clases.png

Descripción:

Representa las principales clases del sistema y sus relaciones.

Clases principales:

- Cliente
- Producto
- Categoria
- Empleado
- Sucursal
- Venta
- DetalleVenta
- Usuario
- Auditoria

Relaciones:

- Un Cliente puede realizar muchas Ventas.
- Una Venta pertenece a un único Cliente.
- Un Producto pertenece a una Categoría.
- Una Categoría contiene muchos Productos.
- Un Empleado trabaja en una Sucursal.
- Una Venta es registrada por un Empleado.
- Una Venta contiene uno o varios Detalles de Venta.

---

### 2. Diagrama de Casos de Uso

Archivo:

- diagrama_casos_uso.png

Actores:

- Administrador
- Vendedor

Casos de uso principales:

Administrador

- Gestionar Clientes
- Gestionar Productos
- Gestionar Empleados
- Gestionar Usuarios
- Consultar Reportes
- Exportar Información
- Crear Backups

Vendedor

- Registrar Ventas
- Consultar Clientes
- Consultar Productos
- Consultar Reportes

---

### 3. Diagrama de Secuencia

Archivo:

- diagrama_secuencia_venta.png

Proceso representado:

1. El usuario inicia una venta.
2. El Controller recibe la solicitud.
3. El Service valida la información.
4. El DAO realiza las operaciones en la base de datos.
5. Se actualiza el stock.
6. Se confirma la transacción.
7. Se devuelve la respuesta al usuario.

---

## Objetivo de los diagramas

Los diagramas UML fueron utilizados para:

- Diseñar la arquitectura del sistema.
- Definir responsabilidades de cada clase.
- Facilitar el mantenimiento.
- Mejorar la comprensión del proyecto.
- Documentar el desarrollo del sistema.
