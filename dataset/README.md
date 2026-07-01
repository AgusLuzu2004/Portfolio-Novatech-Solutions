# Modelo de Datos - NovaTech Solutions

## Descripción

Este documento describe la estructura de datos utilizada en el proyecto **NovaTech Solutions**.

El objetivo del modelo es representar el funcionamiento de una empresa dedicada a la comercialización de productos tecnológicos, permitiendo realizar análisis de ventas, construir dashboards y desarrollar aplicaciones de gestión.

---

# Entidades

## Clientes

| Campo | Tipo |
|--------|------|
| id_cliente | INT |
| nombre | VARCHAR |
| apellido | VARCHAR |
| edad | INT |
| sexo | VARCHAR |
| provincia | VARCHAR |
| ciudad | VARCHAR |
| fecha_alta | DATE |

---

## Categorías

| Campo | Tipo |
|--------|------|
| id_categoria | INT |
| nombre_categoria | VARCHAR |

---

## Productos

| Campo | Tipo |
|--------|------|
| id_producto | INT |
| nombre | VARCHAR |
| marca | VARCHAR |
| id_categoria | INT |
| precio | DECIMAL |
| stock | INT |

---

## Sucursales

| Campo | Tipo |
|--------|------|
| id_sucursal | INT |
| nombre | VARCHAR |
| ciudad | VARCHAR |
| provincia | VARCHAR |

---

## Empleados

| Campo | Tipo |
|--------|------|
| id_empleado | INT |
| nombre | VARCHAR |
| apellido | VARCHAR |
| id_sucursal | INT |
| fecha_ingreso | DATE |

---

## Ventas

| Campo | Tipo |
|--------|------|
| id_venta | INT |
| fecha | DATE |
| id_cliente | INT |
| id_producto | INT |
| id_empleado | INT |
| cantidad | INT |
| precio_unitario | DECIMAL |
| descuento | DECIMAL |
| medio_pago | VARCHAR |
| canal | VARCHAR |

---

# Relaciones

- Un cliente puede realizar muchas ventas.
- Un producto pertenece a una única categoría.
- Una categoría puede contener muchos productos.
- Un empleado trabaja en una sucursal.
- Una sucursal puede tener muchos empleados.
- Una venta pertenece a un cliente, un producto y un empleado.

---

# Objetivo del modelo

Este modelo servirá como base para los proyectos de:

- Microsoft Excel
- SQL
- Power BI
- Python
- Java

Todos los módulos del portafolio utilizarán la misma estructura de datos para mantener la coherencia del proyecto.