CREATE VIEW vw_facturacion_categoria AS
SELECT
    c.nombre_categoria,
    SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS facturacion
FROM ventas v
JOIN productos p ON v.id_producto = p.id_producto
JOIN categorias c ON p.id_categoria = c.id_categoria
GROUP BY c.nombre_categoria;

SELECT *
FROM vw_facturacion_categoria;

CREATE VIEW vw_top_productos AS
SELECT
    p.nombre,
    SUM(v.cantidad) AS unidades_vendidas
FROM ventas v
JOIN productos p
ON v.id_producto = p.id_producto
GROUP BY p.id_producto
ORDER BY unidades_vendidas DESC;

SELECT *
FROM vw_top_productos;

CREATE VIEW vw_ventas_anuales AS
SELECT
    YEAR(fecha) AS anio,
    COUNT(*) AS cantidad_ventas,
    SUM(precio_unitario * cantidad * (1 - descuento / 100)) AS facturacion
FROM ventas
GROUP BY YEAR(fecha);

SELECT *
FROM vw_ventas_anuales;

CREATE VIEW vw_rendimiento_empleados AS
SELECT
    e.id_empleado,
    CONCAT(e.nombre, ' ', e.apellido) AS empleado,
    COUNT(v.id_venta) AS ventas_realizadas,
    SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS facturacion
FROM empleados e
LEFT JOIN ventas v
ON e.id_empleado = v.id_empleado
GROUP BY e.id_empleado;

SELECT *
FROM vw_rendimiento_empleados;

CREATE VIEW vw_clientes_frecuentes AS
SELECT
    c.id_cliente,
    CONCAT(c.nombre, ' ', c.apellido) AS cliente,
    COUNT(v.id_venta) AS compras
FROM clientes c
JOIN ventas v
ON c.id_cliente = v.id_cliente
GROUP BY c.id_cliente
HAVING compras >= 10;

SELECT *
FROM vw_clientes_frecuentes;

CREATE VIEW vw_stock_productos AS
SELECT
    nombre,
    marca,
    stock
FROM productos
ORDER BY stock ASC;

SELECT *
FROM vw_stock_productos;

CREATE VIEW vw_ventas_provincia AS
SELECT
    c.provincia,
    COUNT(v.id_venta) AS ventas,
    SUM(v.precio_unitario * v.cantidad * (1 - v.descuento / 100)) AS facturacion
FROM ventas v
JOIN clientes c
ON v.id_cliente = c.id_cliente
GROUP BY c.provincia;

SELECT *
FROM vw_ventas_provincia;

CREATE VIEW vw_medios_pago AS
SELECT
    medio_pago,
    COUNT(*) AS cantidad
FROM ventas
GROUP BY medio_pago;

SELECT *
FROM vw_medios_pago;

CREATE VIEW vw_canales AS
SELECT
    canal,
    COUNT(*) AS ventas
FROM ventas
GROUP BY canal;

SELECT *
FROM vw_canales;

CREATE VIEW vw_dashboard AS
SELECT

COUNT(*) AS ventas,

SUM(precio_unitario*cantidad*(1-descuento/100)) AS facturacion,

AVG(precio_unitario*cantidad*(1-descuento/100)) AS ticket_promedio

FROM ventas;

SELECT *
FROM vw_dashboard;