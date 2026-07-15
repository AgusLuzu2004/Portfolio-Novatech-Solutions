DELIMITER $$

CREATE PROCEDURE sp_buscar_cliente(IN p_id INT)
BEGIN
    SELECT *
    FROM clientes
    WHERE id_cliente = p_id;
END $$

DELIMITER ;

CALL sp_buscar_cliente(150);

DELIMITER $$

CREATE PROCEDURE sp_ventas_anio(IN p_anio INT)
BEGIN
    SELECT *
    FROM ventas
    WHERE YEAR(fecha) = p_anio;
END $$

DELIMITER ;

CALL sp_ventas_anio(2024);

DELIMITER $$

CREATE PROCEDURE sp_productos_categoria(IN p_categoria INT)
BEGIN
    SELECT *
    FROM productos
    WHERE id_categoria = p_categoria;
END $$

DELIMITER ;

CALL sp_productos_categoria(3);

DELIMITER $$

CREATE PROCEDURE sp_top_productos()
BEGIN
    SELECT
        p.nombre,
        SUM(v.cantidad) AS unidades
    FROM ventas v
    JOIN productos p
        ON p.id_producto = v.id_producto
    GROUP BY p.id_producto
    ORDER BY unidades DESC
    LIMIT 10;
END $$

DELIMITER ;

CALL sp_top_productos();

DELIMITER $$

CREATE FUNCTION fn_importe(
    precio DECIMAL(10,2),
    cantidad INT,
    descuento DECIMAL(5,2)
)

RETURNS DECIMAL(10,2)

DETERMINISTIC

BEGIN

RETURN precio*cantidad*(1-descuento/100);

END $$

DELIMITER ;

SELECT fn_importe(100000,2,10);

DELIMITER $$

CREATE FUNCTION fn_edad_promedio()

RETURNS DECIMAL(5,2)

DETERMINISTIC

BEGIN

DECLARE promedio DECIMAL(5,2);

SELECT AVG(edad)
INTO promedio
FROM clientes;

RETURN promedio;

END $$

DELIMITER ;

SELECT fn_edad_promedio();

DELIMITER $$

CREATE TRIGGER trg_stock

BEFORE UPDATE

ON productos

FOR EACH ROW

BEGIN

IF NEW.stock<0 THEN

SIGNAL SQLSTATE '45000'

SET MESSAGE_TEXT='El stock no puede ser negativo';

END IF;

END $$

DELIMITER ;

SELECT * FROM productos;

CREATE TABLE auditoria_ventas(

id INT AUTO_INCREMENT PRIMARY KEY,

id_venta INT,

fecha DATETIME

);

DELIMITER $$

CREATE TRIGGER trg_auditoria

AFTER INSERT

ON ventas

FOR EACH ROW

BEGIN

INSERT INTO auditoria_ventas(

id_venta,

fecha

)

VALUES(

NEW.id_venta,

NOW()

);

END $$

DELIMITER ;

SELECT * FROM auditoria_ventas;

DELIMITER $$

CREATE TRIGGER trg_descuento

BEFORE INSERT

ON ventas

FOR EACH ROW

BEGIN

IF NEW.descuento>30 THEN

SIGNAL SQLSTATE '45000'

SET MESSAGE_TEXT='Descuento inválido';

END IF;

END $$

DELIMITER ;

SELECT * FROM ventas;