ALTER TABLE productos
ADD CONSTRAINT chk_precio
CHECK(precio>0);

ALTER TABLE productos
ADD CONSTRAINT chk_stock
CHECK(stock>=0);

ALTER TABLE clientes
ADD CONSTRAINT chk_edad
CHECK(edad BETWEEN 18 AND 75);

ALTER TABLE ventas
ADD CONSTRAINT chk_cantidad
CHECK(cantidad>0);

ALTER TABLE ventas
ADD CONSTRAINT chk_descuento
CHECK(descuento>=0 AND descuento<=30);