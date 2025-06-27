package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionDao;

public class PromocionServicio {
    private final PromocionDao promocionDao;

    public PromocionServicio(PromocionDao promocionDao){
        this.promocionDao = promocionDao;
    }
}
