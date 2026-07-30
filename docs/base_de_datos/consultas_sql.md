# Consultas SQL

## Listar clientes

```sql
SELECT *
FROM clientes;
```

---

## Buscar clientes por nombre

```sql
SELECT *
FROM clientes
WHERE nombre LIKE '%Juan%';
```

---

## Productos con bajo stock

```sql
SELECT *
FROM productos
WHERE stock < 10;
```

---

## Ventas por provincia

```sql
SELECT
provincia,
SUM(precio_unitario*cantidad) AS total
FROM ventas
GROUP BY provincia;
```

---

## Top 10 productos vendidos

```sql
SELECT
p.nombre_producto,
SUM(v.cantidad) AS total_vendido
FROM ventas v
JOIN productos p
ON v.id_producto = p.id_producto
GROUP BY p.nombre_producto
ORDER BY total_vendido DESC
LIMIT 10;
```

---

## Facturación mensual

```sql
SELECT
MONTH(fecha) AS mes,
SUM(precio_unitario*cantidad*(1-descuento/100)) AS facturacion
FROM ventas
GROUP BY MONTH(fecha)
ORDER BY mes;
```

---

## Ranking de empleados

```sql
SELECT
e.nombre,
e.apellido,
SUM(v.precio_unitario*v.cantidad) AS total
FROM ventas v
JOIN empleados e
ON v.id_empleado=e.id_empleado
GROUP BY e.id_empleado
ORDER BY total DESC;
```
