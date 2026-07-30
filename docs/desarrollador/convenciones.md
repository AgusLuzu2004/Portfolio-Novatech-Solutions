# Convenciones del Proyecto

## Clases

Las clases utilizan PascalCase.

Ejemplos:

- Cliente
- Producto
- VentaService
- ClienteDAO

---

## Métodos

Los métodos utilizan camelCase.

Ejemplos:

registrarVenta()

buscarCliente()

listarProductos()

---

## Variables

Las variables utilizan camelCase.

Ejemplos:

precioTotal

cantidadProductos

fechaVenta

---

## Constantes

Las constantes utilizan MAYÚSCULAS.

Ejemplos:

MAX_DESCUENTO

IVA

RUTA_BACKUP

---

## Paquetes

Los paquetes se escriben en minúsculas.

Ejemplos:

controller

dao

model

service

util

---

## Base de datos

### Tablas

Se utilizan nombres en plural.

Ejemplos:

clientes

productos

ventas

empleados

---

### Claves primarias

Formato:

id_cliente

id_producto

id_venta

---

### Claves foráneas

Se mantiene el mismo nombre de la clave primaria referenciada.

Ejemplo:

id_cliente

id_producto

---

## Commits

Se recomienda utilizar mensajes descriptivos.

Ejemplos:

feat: agregar módulo de clientes

fix: corregir cálculo del total de ventas

docs: actualizar README

test: agregar pruebas de ClienteService

refactor: reorganizar estructura del proyecto

---

## Buenas prácticas

- Mantener una única responsabilidad por clase.
- Evitar duplicar código.
- Validar la información en la capa Service.
- No escribir consultas SQL en los Controllers.
- Utilizar nombres claros y descriptivos.
- Documentar los cambios importantes.
- Crear pruebas unitarias para la lógica de negocio.
