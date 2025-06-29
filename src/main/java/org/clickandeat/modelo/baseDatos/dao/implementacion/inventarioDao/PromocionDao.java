package org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.inventario.Promocion;
import org.hibernate.Session;

import java.util.List;

public class PromocionDao extends DaoImpl<Promocion> {
    public PromocionDao(){
        super(Promocion.class);
    }

    public List<Promocion> findAllWithProductos() {
        Session session = openSession();
        try {
            return session.createQuery(
                    "SELECT DISTINCT p FROM Promocion p " +
                            "LEFT JOIN FETCH p.productos pp " +
                            "LEFT JOIN FETCH pp.producto",
                    Promocion.class
            ).list();
        } finally {
            session.close();
        }
    }

}