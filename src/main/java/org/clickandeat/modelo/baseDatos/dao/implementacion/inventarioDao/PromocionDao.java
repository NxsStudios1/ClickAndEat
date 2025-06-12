package org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.inventario.Promocion;

public class PromocionDao extends DaoImpl<Promocion> {
    public PromocionDao(){
        super(Promocion.class);
    }

    // Puedes agregar métodos personalizados aquí, por ejemplo:
    // public Usuario buscarPorTelefono(String telefono) { ... }
}
