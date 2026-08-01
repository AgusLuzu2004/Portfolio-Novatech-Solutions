# Sistema de Gestión - NovaTech Solutions

## Objetivo

Aplicación de escritorio desarrollada en Java para administrar la información comercial de NovaTech Solutions.

## Tecnologías

- Java 25
- JavaFX
- JDBC
- Maven
- MySQL

## Funcionalidades implementadas

- Inicio de sesión
- Conexión a MySQL
- Menú principal

## Gestión de Clientes

Funcionalidades:

- Listado
- Alta
- Modificación
- Eliminación
- Búsqueda
- Filtrado por provincia

Tecnologías:

- JavaFX
- JDBC
- MySQL

## Gestión de Productos

Funcionalidades implementadas:

- Listado
- Alta
- Modificación
- Eliminación
- Búsqueda
- Filtro por categoría
- Filtro por marca
- Control de stock

Tecnologías:

- JavaFX
- JDBC
- MySQL

## Gestión de Empleados

Funcionalidades:

- Listado
- Alta
- Modificación
- Eliminación
- Búsqueda
- Filtro por sucursal

Validaciones:

- Fecha de ingreso
- Datos obligatorios
- Integridad referencial

## Gestión de Ventas

Funcionalidades:

- Registro de ventas
- Múltiples productos por venta (carrito)
- Actualización automática de stock
- Validación de stock disponible
- Transacciones JDBC (rollback si falla algún producto)

Integraciones:

- Clientes
- Productos
- Empleados
- MySQL

## Reportes

Incluye:

- KPIs
- Evolución mensual
- Ventas por categoría
- Ranking de empleados
- Top productos
- Ventas por provincia
- Medios de pago

Características:

- Gráficos JavaFX
- Filtros dinámicos
- Actualización en tiempo real

## Seguridad

Características:

- Login
- Roles (Administrador / Vendedor)
- Gestión de usuarios
- Auditoría
- Cambio de contraseña
- Configuración del sistema

Nota: actualmente el rol se guarda por usuario pero no restringe el acceso a ninguna pantalla — todo usuario logueado ve el mismo menú.

Tecnologías:

- JavaFX
- JDBC
- PBKDF2WithHmacSHA256 (hash de contraseñas con sal aleatoria)

## Funcionalidades finales

- Exportación a Excel
- Exportación a PDF
- Copias de seguridad
- Restauración
- Preferencias (tema, idioma, ruta) con persistencia local

## Testing

Se implementaron pruebas unitarias utilizando:

- JUnit 5
- Mockito

Cobertura:

- Validaciones de clientes
- Validaciones de productos
- Registro de ventas
- Gestión de usuarios

## Logging

Tecnologías:

- SLF4J
- Logback

Se registran:

- Inicio del sistema
- Inicio de sesión
- Altas
- Modificaciones
- Eliminaciones
- Ventas
- Exportaciones
- Backups
- Errores
