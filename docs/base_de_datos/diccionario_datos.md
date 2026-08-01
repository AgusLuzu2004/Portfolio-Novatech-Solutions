# Diccionario de Datos

## Tabla: clientes

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id_cliente | INT | Identificador único |
| nombre | VARCHAR(100) | Nombre del cliente |
| apellido | VARCHAR(100) | Apellido |
| edad | INT | Edad |
| sexo | VARCHAR(20) | Sexo |
| provincia | VARCHAR(100) | Provincia |
| ciudad | VARCHAR(100) | Ciudad |
| fecha_alta | DATE | Fecha de alta |

---

## Tabla: categorias

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id_categoria | INT | Identificador |
| nombre_categoria | VARCHAR(100) | Nombre |

---

## Tabla: productos

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id_producto | INT | Identificador |
| nombre | VARCHAR(150) | Nombre |
| marca | VARCHAR(100) | Marca |
| precio | DECIMAL(10,2) | Precio |
| stock | INT | Stock disponible |
| id_categoria | INT | Categoría |

---

## Tabla: sucursales

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id_sucursal | INT | Identificador |
| nombre | VARCHAR(100) | Nombre |
| provincia | VARCHAR(100) | Provincia |
| ciudad | VARCHAR(100) | Ciudad |

---

## Tabla: empleados

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id_empleado | INT | Identificador |
| nombre | VARCHAR(100) | Nombre |
| apellido | VARCHAR(100) | Apellido |
| fecha_ingreso | DATE | Fecha de ingreso |
| id_sucursal | INT | Sucursal |

---

## Tabla: ventas

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id_venta | INT | Identificador |
| fecha | DATE | Fecha de la venta |
| id_cliente | INT | Cliente |
| id_producto | INT | Producto |
| id_empleado | INT | Empleado |
| cantidad | INT | Cantidad |
| precio_unitario | DECIMAL(10,2) | Precio |
| descuento | DECIMAL(5,2) | Descuento |
| medio_pago | VARCHAR(50) | Medio de pago |
| canal | VARCHAR(50) | Canal de venta |

---

## Tabla: usuarios

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id_usuario | INT | Identificador |
| usuario | VARCHAR(50) | Nombre de usuario (único) |
| contraseña | VARCHAR(255) | Hash PBKDF2 (nunca texto plano) |
| nombre | VARCHAR(100) | Nombre completo |
| rol | VARCHAR(30) | ADMINISTRADOR / VENDEDOR |
| activo | BOOLEAN | Si el usuario puede loguearse |

---

## Tabla: auditoria

| Campo | Tipo | Descripción |
| -------- | ------ | ------------- |
| id | INT | Identificador |
| usuario | VARCHAR(100) | Usuario que hizo la acción |
| accion | VARCHAR(100) | INSERT / UPDATE / ELIMINAR / LOGIN / etc. |
| modulo | VARCHAR(100) | Módulo afectado |
| fecha | DATETIME | Fecha y hora exacta |
