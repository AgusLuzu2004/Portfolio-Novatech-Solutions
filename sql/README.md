## Importación de datos

Los datos fueron generados mediante un script en Python y cargados en MySQL utilizando `LOAD DATA LOCAL INFILE`.

Archivos importados:

- categorias.csv
- sucursales.csv
- productos.csv
- clientes.csv
- empleados.csv
- ventas.csv

## Consultas de negocio

Este módulo incluye consultas SQL orientadas a responder preguntas reales sobre el desempeño de la empresa.

Entre ellas:

- Facturación total.
- Ticket promedio.
- Top 10 productos.
- Ventas por año.
- Ventas por categoría.
- Clientes con más compras.
- Ventas por empleado.
- Medios de pago.
- Canales de venta.

## Vistas

Se desarrollaron vistas para simplificar consultas complejas y facilitar el análisis de información.

Vistas implementadas:

- Facturación por categoría
- Top productos
- Ventas anuales
- Rendimiento de empleados
- Clientes frecuentes
- Stock disponible
- Ventas por provincia
- Medios de pago
- Canales de venta
- Dashboard ejecutivo

## Lógica de negocio

Este módulo implementa lógica directamente en la base de datos mediante:

- Procedimientos almacenados
- Funciones
- Triggers

Estas herramientas permiten automatizar tareas, validar datos y centralizar reglas de negocio.