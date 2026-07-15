SELECT COUNT(*) AS total_clientes
FROM clientes;

SELECT COUNT(*) AS total_productos
FROM productos;

SELECT COUNT(*) AS total_ventas
FROM ventas;

SELECT COUNT(*) AS total_empleados
FROM empleados;

SELECT
SUM(precio_unitario*cantidad*(1-descuento/100))
AS facturacion_total
FROM ventas;

SELECT
AVG(precio_unitario*cantidad*(1-descuento/100))
AS ticket_promedio
FROM ventas;

SELECT
*
FROM ventas
ORDER BY
(precio_unitario*cantidad*(1-descuento/100))
DESC
LIMIT 1;

SELECT

p.nombre,

SUM(v.cantidad) AS unidades

FROM ventas v

JOIN productos p

ON v.id_producto=p.id_producto

GROUP BY p.nombre

ORDER BY unidades DESC

LIMIT 10;

SELECT

c.nombre_categoria,

SUM(v.precio_unitario*v.cantidad)

FROM ventas v

JOIN productos p

ON v.id_producto=p.id_producto

JOIN categorias c

ON p.id_categoria=c.id_categoria

GROUP BY c.nombre_categoria

ORDER BY SUM(v.precio_unitario*v.cantidad) DESC;

SELECT

c.nombre,

c.apellido,

COUNT(*) AS compras

FROM ventas v

JOIN clientes c

ON v.id_cliente=c.id_cliente

GROUP BY c.id_cliente

ORDER BY compras DESC

LIMIT 10;

SELECT

provincia,

COUNT(*)

FROM clientes

GROUP BY provincia

ORDER BY COUNT(*) DESC;

SELECT

e.nombre,

e.apellido,

COUNT(*)

FROM ventas v

JOIN empleados e

ON v.id_empleado=e.id_empleado

GROUP BY e.id_empleado

ORDER BY COUNT(*) DESC;

SELECT

YEAR(fecha),

COUNT(*)

FROM ventas

GROUP BY YEAR(fecha)

ORDER BY YEAR(fecha);

SELECT

YEAR(fecha),

SUM(precio_unitario*cantidad)

FROM ventas

GROUP BY YEAR(fecha)

ORDER BY YEAR(fecha);

SELECT

MONTH(fecha),

COUNT(*)

FROM ventas

GROUP BY MONTH(fecha)

ORDER BY MONTH(fecha);

SELECT

medio_pago,

COUNT(*)

FROM ventas

GROUP BY medio_pago

ORDER BY COUNT(*) DESC;

SELECT

canal,

COUNT(*)

FROM ventas

GROUP BY canal;

SELECT

AVG(descuento)

FROM ventas;

SELECT

COUNT(*)

FROM ventas

WHERE descuento>0;