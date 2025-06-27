package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoIngredienteDao;

public class ProductoIngredienteServicio {
    private final ProductoIngredienteDao productoIngredienteDao;

    public ProductoIngredienteServicio(ProductoIngredienteDao productoIngredienteDao){
        this.productoIngredienteDao = productoIngredienteDao;
    }
}
