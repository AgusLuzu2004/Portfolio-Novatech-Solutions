CREATE TABLE categorias (

    id_categoria INT PRIMARY KEY,

    nombre_categoria VARCHAR(50) NOT NULL

);

CREATE TABLE sucursales (

    id_sucursal INT PRIMARY KEY,

    nombre VARCHAR(100) NOT NULL,

    ciudad VARCHAR(80),

    provincia VARCHAR(80)

);

CREATE TABLE clientes (

    id_cliente INT PRIMARY KEY,

    nombre VARCHAR(80),

    apellido VARCHAR(80),

    edad INT,

    sexo VARCHAR(20),

    provincia VARCHAR(80),

    ciudad VARCHAR(80),

    fecha_alta DATE

);

CREATE TABLE productos (

    id_producto INT PRIMARY KEY,

    nombre VARCHAR(150),

    marca VARCHAR(80),

    id_categoria INT,

    precio DECIMAL(10,2),

    stock INT,

    FOREIGN KEY(id_categoria)

        REFERENCES categorias(id_categoria)

);

CREATE TABLE empleados (

    id_empleado INT PRIMARY KEY,

    nombre VARCHAR(80),

    apellido VARCHAR(80),

    id_sucursal INT,

    fecha_ingreso DATE,

    FOREIGN KEY(id_sucursal)

        REFERENCES sucursales(id_sucursal)

);

CREATE TABLE ventas (

    id_venta INT PRIMARY KEY AUTO_INCREMENT,

    fecha DATE,

    id_cliente INT,

    id_producto INT,

    id_empleado INT,

    cantidad INT,

    precio_unitario DECIMAL(10,2),

    descuento DECIMAL(5,2),

    medio_pago VARCHAR(50),

    canal VARCHAR(30),

    FOREIGN KEY(id_cliente)

        REFERENCES clientes(id_cliente),

    FOREIGN KEY(id_producto)

        REFERENCES productos(id_producto),

    FOREIGN KEY(id_empleado)

        REFERENCES empleados(id_empleado)

);

CREATE TABLE usuarios (

    id_usuario INT AUTO_INCREMENT PRIMARY KEY,

    usuario VARCHAR(50) UNIQUE NOT NULL,

    contraseña VARCHAR(255) NOT NULL,

    nombre VARCHAR(100) NOT NULL,

    rol VARCHAR(30) NOT NULL,

    activo BOOLEAN DEFAULT TRUE

);

CREATE TABLE auditoria (

    id INT AUTO_INCREMENT PRIMARY KEY,

    usuario VARCHAR(100),

    accion VARCHAR(100),

    modulo VARCHAR(100),

    fecha DATETIME

);