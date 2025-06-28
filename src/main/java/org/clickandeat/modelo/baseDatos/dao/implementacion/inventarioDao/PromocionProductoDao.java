package org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.inventario.PromocionProducto;

public class PromocionProductoDao extends DaoImpl<PromocionProducto> {
    public PromocionProductoDao(){
        super(PromocionProducto.class);
    }

}
