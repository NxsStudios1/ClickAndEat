package org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.pedido.Pedido;

import java.util.List;
import org.hibernate.Session;

public class PedidoDao extends DaoImpl<Pedido> {
    public PedidoDao(){
        super(Pedido.class);
    }

    // Este método ahora carga los detalles con JOIN FETCH:
    public List<Pedido> obtenerTodos() {
        Session session = openSession();
        List<Pedido> lista = null;
        try {
            lista = session.createQuery(
                    "SELECT DISTINCT p FROM Pedido p LEFT JOIN FETCH p.detalles",
                    Pedido.class
            ).list();
        } finally {
            session.close();
        }
        return lista;
    }
}