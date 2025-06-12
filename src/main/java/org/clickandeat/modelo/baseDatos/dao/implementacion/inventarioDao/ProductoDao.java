package org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.inventario.Producto;

public class ProductoDao extends DaoImpl <Producto> {
    public ProductoDao(){
        super(Producto.class);
    }

    // Puedes agregar métodos personalizados aquí, por ejemplo:
    // public Usuario buscarPorTelefono(String telefono) { ... }
}
