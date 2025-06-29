package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoDao;
import org.clickandeat.modelo.entidades.inventario.Producto;
import java.util.List;

public class ProductoServicio {
    private final ProductoDao productoDao;

    public ProductoServicio(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    public List<Producto> obtenerTodos() {
        return productoDao.findAll();
    }

    public boolean guardarProducto(Producto producto) {
        return productoDao.guardar(producto);
    }

    public boolean actualizarProducto(Producto producto) {
        return productoDao.actualizar(producto);
    }

    public boolean eliminarProducto(Producto producto) {
        return productoDao.eliminar(producto);
    }

}