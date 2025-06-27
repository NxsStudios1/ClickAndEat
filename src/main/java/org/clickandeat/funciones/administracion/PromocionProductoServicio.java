package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionProductoDao;

public class PromocionProductoServicio {

    private final PromocionProductoDao promocionProductoDao;

    public PromocionProductoServicio(PromocionProductoDao promocionProductoDao){
        this.promocionProductoDao = promocionProductoDao;
    }

}
