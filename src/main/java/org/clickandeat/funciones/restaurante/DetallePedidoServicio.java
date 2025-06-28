package org.clickandeat.funciones.restaurante;

import org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao.DetallePedidoDao;

public class DetallePedidoServicio {
    private final DetallePedidoDao detallePedidoDao;

    public DetallePedidoServicio(DetallePedidoDao detallePedidoDao){
        this.detallePedidoDao = detallePedidoDao;
    }
}
