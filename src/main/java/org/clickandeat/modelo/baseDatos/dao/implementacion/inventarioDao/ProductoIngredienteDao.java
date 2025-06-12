package org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.inventario.ProductoIngrediente;

public class ProductoIngredienteDao extends DaoImpl<ProductoIngrediente> {
    public ProductoIngredienteDao(){
        super(ProductoIngrediente.class);

    }

    // Puedes agregar métodos personalizados aquí, por ejemplo:
    // public Usuario buscarPorTelefono(String telefono) { ... }
}
