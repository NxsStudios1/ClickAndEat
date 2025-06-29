package org.clickandeat.funciones.restaurante;

import org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao.PedidoDao;
import org.clickandeat.modelo.entidades.pedido.Pedido;

import java.util.List;

public class PedidoServicio {
    private final PedidoDao pedidoDao;

    public PedidoServicio(PedidoDao pedidoDao){
        this.pedidoDao = pedidoDao;
    }

    public boolean guardar(Pedido pedido) {
        return pedidoDao.guardar(pedido);
    }

    public boolean actualizar(Pedido pedido) {
        return pedidoDao.actualizar(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoDao.obtenerTodos();
    }
}