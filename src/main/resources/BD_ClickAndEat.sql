# Click And Eat

# Base de Datos

# Equipo 1

CREATE DATABASE clickAndEat;
USE clickAndEat;
SHOW TABLES;

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_rol`
-- -----------------------------------------------------

CREATE TABLE rol (
	idRol INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	rol ENUM('ADMINISTRADOR','CLIENTE') NOT NULL UNIQUE
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_usuarios`
-- -----------------------------------------------------

CREATE TABLE usuario(
	idUsuario INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    idRol INT NOT NULL,
    FOREIGN KEY (idRol) REFERENCES rol(idRol)
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_comentarios`
-- -----------------------------------------------------

CREATE TABLE comentario(
	idComentario INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    asunto VARCHAR(100),
    contenido VARCHAR (2000) NOT NULL,
    calificacion INT CHECK (calificacion >= 1 AND calificacion <= 5) NOT NULL,
    categoriaComentario ENUM('COMIDA', 'SERVICIO', 'AMBIENTE', 'TIEMPO_ESPERA', 'GENERAL') DEFAULT 'GENERAL',
    fechaComentario TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    idCliente INT,
    FOREIGN KEY (idCliente) REFERENCES usuario(idUsuario)
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_respuestaComentarios`
-- -----------------------------------------------------

CREATE TABLE respuestaComentario (
	idRespuestaComentario INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    contenido VARCHAR (2000) NOT NULL,
    fechaRespuesta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idComentario INT,
    idAdministrador INT,
    FOREIGN KEY (idComentario) REFERENCES comentario(idComentario) ON DELETE CASCADE,
    FOREIGN KEY (idAdministrador) REFERENCES usuario(idUsuario)
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_ingredientes`
-- -----------------------------------------------------

CREATE TABLE ingrediente (
	idIngrediente INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL, 
    stockActual INT NOT NULL DEFAULT 0,
    stockMinimo INT NOT NULL DEFAULT 2,
    unidadMedida ENUM('gramos', 'litros', 'mililitros', 'unidades', 'kilogramos') NOT NULL,
    precioUnitario DECIMAL(10,2) NOT NULL
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_categoriaProducto`
-- -----------------------------------------------------

CREATE TABLE categoriaProducto(
	idCategoria INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(200),
    activo BOOLEAN DEFAULT TRUE
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_producto`
-- -----------------------------------------------------

CREATE TABLE producto( 
	idProducto INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    imagenUrl VARCHAR(500),
    fechaCreacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    idCategoria INT,
    FOREIGN KEY (idCategoria) REFERENCES categoriaProducto(idCategoria)
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_productoIngrediente`
-- -----------------------------------------------------

CREATE TABLE productoIngrediente(
    idProductoIngrediente INT PRIMARY KEY AUTO_INCREMENT,
    idProducto INT NOT NULL,
    idIngrediente INT NOT NULL,
    cantidadNecesaria DECIMAL(8,2) NOT NULL,
    UNIQUE KEY productoIngrediente (idProducto, idIngrediente),
    FOREIGN KEY (idProducto) REFERENCES producto(idProducto) ON DELETE CASCADE,
    FOREIGN KEY (idIngrediente) REFERENCES ingrediente(idIngrediente)
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_pedido`
-- -----------------------------------------------------

CREATE TABLE pedido (
    idPedido INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    numeroTicket VARCHAR(20) UNIQUE NOT NULL,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'TERMINADO', 'PAGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
    total DECIMAL(10,2) NOT NULL,
    fechaPedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones VARCHAR(500),
    metodoPagoPrevisto ENUM('EFECTIVO', 'TARJETA') DEFAULT 'EFECTIVO',
    idCliente INT,
    FOREIGN KEY (idCliente) REFERENCES usuario(idUsuario)
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_personalizacion`
-- -----------------------------------------------------
CREATE TABLE personalizacion (
    idPersonalizacion INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(50) NOT NULL, -- Mostaza, Catsup, Sin cebolla, etc.
    tipo VARCHAR(30) NOT NULL, -- SALSA, EXTRA, SIN_INGREDIENTE
    precio_adicional DECIMAL(10,2) DEFAULT 0.00
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_promocion`
-- -----------------------------------------------------
CREATE TABLE promocion (
    idPromocion INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300),
    descuento_porcentaje DECIMAL(5,2), -- Para descuentos en porcentaje
    fechaInicio DATE NOT NULL,
    fechaFin DATE NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_productoPromocion`
-- -----------------------------------------------------

CREATE TABLE productoPromocion(
    idProductoPromocion INT PRIMARY KEY AUTO_INCREMENT,
    idProducto INT NOT NULL,
    idPromocion INT NOT NULL,
    UNIQUE KEY productoPromocion (idProducto, idPromocion),
    FOREIGN KEY (idProducto) REFERENCES producto(idProducto) ON DELETE CASCADE,
    FOREIGN KEY (idPromocion) REFERENCES promocion(idPromocion) ON DELETE CASCADE
);

-----------------------------------------------------
-- Table `clickAndEat`.`tbl_detallePedido`
-- --------------------------------------------------

CREATE TABLE detallePedido (
    idDetallePedido INT PRIMARY KEY AUTO_INCREMENT,
    cantidad INT NOT NULL DEFAULT 1,
    precioUnitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    tipoItem ENUM ('PRODUCTO','PROMOCION') NOT NULL,
    idPedido INT,
    idProducto INT,
    idPromocion INT,
    CHECK ((idProducto IS NOT NULL AND idPromocion IS NULL AND tipoItem= 'PRODUCTO') OR 
           (idProducto IS NULL AND idPromocion IS NOT NULL AND tipoItem= 'PROMOCION')),
    FOREIGN KEY (idPedido) REFERENCES pedido(idPedido) ON DELETE CASCADE,
    FOREIGN KEY (idProducto) REFERENCES producto(idProducto),
    FOREIGN KEY (idPromocion) REFERENCES promocion(idPromocion)
);

-----------------------------------------------------
-- Table `clickAndEat`.`tbl_detallePromocionPedido`
-- --------------------------------------------------
CREATE TABLE detallePromocionPedido (
    idDetallePromocionPedido INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    idDetallePedido INT NOT NULL, -- Referencia al detalle que es promoción
    idProducto INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    FOREIGN KEY (idDetallePedido) REFERENCES detallePedido(idDetallePedido ) ON DELETE CASCADE,
    FOREIGN KEY (idProducto) REFERENCES producto(idProducto),
    INDEX detallePromocion (idDetallePedido)
);



-- -----------------------------------------------------
-- Table `clickAndEat`.`tbl_personalizacionPedido`
-- -------------------------------------------------------
CREATE TABLE personalizacionPedido (
    idPersonalizacionPedido INT PRIMARY KEY AUTO_INCREMENT,
    idDetallePedido INT,
    idPersonalizacion INT,
    FOREIGN KEY (idDetallePedido) REFERENCES detallePedido(idDetallePedido) ON DELETE CASCADE,
    FOREIGN KEY (idPersonalizacion) REFERENCES personalizacion(idPersonalizacion)
);









