package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.CategoriaProductoDao;
import org.clickandeat.modelo.entidades.inventario.CategoriaProducto;

import java.util.List;

public class CategoriaProductoServicio {
    private final CategoriaProductoDao categoriaProductoDao;

    public CategoriaProductoServicio(CategoriaProductoDao categoriaProductoDao) {
        this.categoriaProductoDao = categoriaProductoDao;

    }

    public List<CategoriaProducto> obtenerTodos() {
        return categoriaProductoDao.findAll();
    }

    public boolean guardarCategoria(CategoriaProducto categoria) {
        return categoriaProductoDao.guardar(categoria);
    }

    public boolean actualizarCategoria(CategoriaProducto categoria) {
        return categoriaProductoDao.actualizar(categoria);
    }

    public boolean eliminarCategoria(CategoriaProducto categoria) {
        return categoriaProductoDao.eliminar(categoria);
    }

}