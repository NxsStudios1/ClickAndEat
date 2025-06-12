# Click And Eat

# Base de Datos

# Equipo 1

CREATE DATABASE IF NOT EXISTS clickandeat;

USE clickandeat;
SHOW TABLES;

# ----------------------------
# CREACIÓN DE TABLAS
# ----------------------------

-- -----------------------------------------------------
-- Table `tbl_rol`
-- -----------------------------------------------------

CREATE TABLE tbl_rol (
	id INT PRIMARY KEY AUTO_INCREMENT,
	tipo ENUM('ADMINISTRADOR','CLIENTE') NOT NULL UNIQUE
);

-- -----------------------------------------------------
-- Table `tbl_usuario`
-- -----------------------------------------------------

CREATE TABLE tbl_usuario(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(255) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    idRol INT NOT NULL,
    FOREIGN KEY (idRol) REFERENCES tbl_rol(id)
);

-- -----------------------------------------------------
-- Table `tbl_comentario`
-- -----------------------------------------------------

CREATE TABLE tbl_comentario(
	id INT PRIMARY KEY AUTO_INCREMENT,
    asunto VARCHAR(100) NOT NULL,
    contenido VARCHAR (2000) NOT NULL,
    calificacion INT CHECK (calificacion >= 1 AND calificacion <= 5) NOT NULL,
    categoria ENUM('COMIDA', 'SERVICIO', 'AMBIENTE', 'TIEMPO_ESPERA', 'GENERAL'),
    fechaComentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idCliente INT,
    FOREIGN KEY (idCliente) REFERENCES tbl_usuario(id)
);

-- -----------------------------------------------------
-- Table `tbl_respuestaComentario`
-- -----------------------------------------------------

CREATE TABLE tbl_respuestaComentario (
	id INT PRIMARY KEY AUTO_INCREMENT,
    contenido VARCHAR (2000) NOT NULL,
    fechaRespuesta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idComentario INT NOT NULL,
    idAdministrador INT NOT NULL,
    FOREIGN KEY (idComentario) REFERENCES tbl_comentario(id) ON DELETE CASCADE,
    FOREIGN KEY (idAdministrador) REFERENCES tbl_usuario(id)
);

-- -----------------------------------------------------
-- Table `tbl_ingrediente`
-- -----------------------------------------------------

CREATE TABLE tbl_ingrediente (
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL, 
    cantidadPorcion DOUBLE NOT NULL,
    unidadMedida ENUM('gramos', 'litros', 'mililitros', 'unidades', 'kilogramos') NOT NULL,
    stockActual DOUBLE NOT NULL,
    precioUnitario DOUBLE NOT NULL
);

-- -----------------------------------------------------
-- Table `tbl_categoriaProducto`
-- -----------------------------------------------------

CREATE TABLE tbl_categoriaProducto(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL
);

-- -----------------------------------------------------
-- Table `tbl_producto`
-- -----------------------------------------------------

CREATE TABLE tbl_producto( 
	id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    precio DOUBLE NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    imagenUrl VARCHAR(500),
    idCategoria INT,
    FOREIGN KEY (idCategoria) REFERENCES tbl_categoriaProducto(id)
);

-- -----------------------------------------------------
-- Table `tbl_productoIngrediente`
-- -----------------------------------------------------

CREATE TABLE tbl_productoIngrediente(
    id INT PRIMARY KEY AUTO_INCREMENT,
    idProducto INT NOT NULL,
    idIngrediente INT NOT NULL,
    cantidadNecesaria DOUBLE NOT NULL,
    UNIQUE KEY productoIngrediente (idProducto, idIngrediente),
    FOREIGN KEY (idProducto) REFERENCES tbl_producto(id) ON DELETE CASCADE,
    FOREIGN KEY (idIngrediente) REFERENCES tbl_ingrediente(id)
);

-- -----------------------------------------------------
-- Table `tbl_pedido`
-- -----------------------------------------------------

CREATE TABLE tbl_pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numeroTicket VARCHAR(20) UNIQUE NOT NULL,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'TERMINADO', 'PAGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
    total DOUBLE NOT NULL,
    fechaPedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones VARCHAR(500),
    idCliente INT,
    FOREIGN KEY (idCliente) REFERENCES tbl_usuario(id)
);

-- -----------------------------------------------------
-- Table `tbl_promocion`
-- -----------------------------------------------------
CREATE TABLE tbl_promocion (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
	fechaInicio DATE NOT NULL,
    fechaFin DATE NOT NULL,
    precioTotalConDescuento DOUBLE NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- -----------------------------------------------------
-- Table `tbl_productoPromocion`
-- -----------------------------------------------------

CREATE TABLE tbl_promocionProducto(
    id INT PRIMARY KEY AUTO_INCREMENT,
    idProducto INT NOT NULL,
    idPromocion INT NOT NULL,
    cantidadProducto DOUBLE NOT NULL,
    UNIQUE KEY productoPromocion (idProducto, idPromocion),
    FOREIGN KEY (idPromocion) REFERENCES tbl_promocion(id) ON DELETE CASCADE,
    FOREIGN KEY (idProducto) REFERENCES tbl_producto(id)
);

-----------------------------------------------------
-- Table tbl_detallePedido`
-- --------------------------------------------------

CREATE TABLE tbl_detallePedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipoItem ENUM('PRODUCTO', 'PROMOCION') NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    precioUnitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,
    idProducto INT NULL,
    idPromocion INT NULL,
    idPedido INT NOT NULL,
    FOREIGN KEY (idPedido) REFERENCES tbl_pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (idProducto) REFERENCES tbl_producto(id),
    FOREIGN KEY (idPromocion) REFERENCES tbl_promocion(id)
);


