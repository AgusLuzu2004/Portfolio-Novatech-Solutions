# UML - NovaTech Solutions

## Introducción

Este documento describe los diagramas UML utilizados para el desarrollo del sistema NovaTech Solutions.

Los diagramas están en formato PlantUML (`.puml`); no hay imágenes `.png` generadas de estos tres (a diferencia del modelo ER y el diagrama de arquitectura, que sí las tienen). Para verlos, abrí el `.puml` con un plugin de PlantUML o pegalo en <https://www.plantuml.com/plantuml>.

---

## Diagramas incluidos

### 1. Diagrama de Clases

Archivo:

- diagrama_clases.puml

Descripción:

Representa las principales clases del modelo de dominio (paquete `com.novatech.model`) y sus relaciones.

Clases principales:

- Cliente
- Producto
- Empleado
- Venta
- DetalleVenta
- Usuario
- Auditoria
- Rol (enum)

Nota: "Categoria" y "Sucursal" no son clases Java propias del proyecto — se manejan como `int`/`String` sueltos (`idCategoria`, `idSucursal`, nombres traídos por consulta), no hay `Categoria.java` ni `Sucursal.java`.

Relaciones:

- Un Cliente puede realizar muchas Ventas.
- Una Venta pertenece a un único Cliente y es registrada por un único Empleado.
- Un Producto puede estar en muchos DetalleVenta.
- Una Venta arma en memoria una lista de DetalleVenta (carrito) — pero esto no se persiste como una tabla propia: cada DetalleVenta termina siendo una fila independiente en "ventas".
- Un Usuario genera muchos registros de Auditoria.

---

### 2. Diagrama de Casos de Uso

Archivo:

- diagrama_casos_uso.puml

Actores:

- Administrador
- Vendedor

Estado actual: **ambos roles tienen acceso a las mismas funcionalidades**. El campo `rol` se guarda por usuario pero todavía no restringe ninguna pantalla.

Casos de uso principales (para ambos actores):

- Iniciar sesión
- Gestionar Clientes / Productos / Empleados
- Registrar Ventas
- Ver Reportes (con filtros)
- Gestionar Usuarios
- Consultar Auditoría
- Exportar a Excel / PDF
- Crear y Restaurar Backups
- Configurar Sistema / Editar Preferencias

No existe un caso de uso de "Cerrar sesión": para volver al login hay que cerrar y reabrir la aplicación.

---

### 3. Diagrama de Secuencia

Archivo:

- diagrama_secuencias.puml

Proceso representado (registrar una venta con varios productos):

1. El usuario confirma la venta desde el carrito armado en pantalla.
2. El Controller delega en VentaService.
3. El Service valida los datos y el stock disponible de cada producto.
4. El Service calcula subtotales y el total.
5. El DAO abre una transacción (`setAutoCommit(false)`).
6. Por cada producto del carrito: inserta una fila en "ventas" y descuenta stock (`UPDATE ... WHERE stock >= cantidad`).
7. Si algún producto se queda sin stock a mitad de camino, se hace `ROLLBACK` de toda la operación.
8. Si todo sale bien, se hace `COMMIT` y se devuelve la confirmación al usuario.

---

## Objetivo de los diagramas

Los diagramas UML fueron utilizados para:

- Diseñar la arquitectura del sistema.
- Definir responsabilidades de cada clase.
- Facilitar el mantenimiento.
- Mejorar la comprensión del proyecto.
- Documentar el desarrollo del sistema.
