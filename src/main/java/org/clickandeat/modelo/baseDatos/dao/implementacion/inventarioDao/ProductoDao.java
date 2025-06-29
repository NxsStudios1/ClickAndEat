package org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.inventario.Producto;

import java.util.List;

import static org.clickandeat.modelo.baseDatos.hibernate.HibernateUtil.getSession;

public class ProductoDao extends DaoImpl <Producto> {

    public ProductoDao(){
        super(Producto.class);
    }

}
