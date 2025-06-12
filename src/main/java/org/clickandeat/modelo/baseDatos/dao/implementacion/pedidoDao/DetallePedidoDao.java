package org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.pedido.DetallePedido;

public class DetallePedidoDao extends DaoImpl<DetallePedido> {
    public DetallePedidoDao(){
        super(DetallePedido.class);
    }
    // Puedes agregar métodos personalizados aquí, por ejemplo:
    // public Usuario buscarPorTelefono(String telefono) { ... }

}
