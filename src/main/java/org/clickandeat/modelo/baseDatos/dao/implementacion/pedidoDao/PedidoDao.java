package org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.pedido.Pedido;

public class PedidoDao extends DaoImpl<Pedido> {
    public PedidoDao(){
        super(Pedido.class);
    }

    // Puedes agregar métodos personalizados aquí, por ejemplo:
    // public Usuario buscarPorTelefono(String telefono) { ... }
}
