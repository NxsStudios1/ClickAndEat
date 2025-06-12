package org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.comentario.Comentario;

import java.util.List;

public class ComentarioDao extends DaoImpl<Comentario> {
    public ComentarioDao(){
        super(Comentario.class);
    }

    public List<Comentario> findAllConClienteYRol() {
        var session = sessionFactory.openSession();
        try {
            return session.createQuery(
                    "SELECT c FROM Comentario c " +
                            "JOIN FETCH c.cliente cli " +
                            "JOIN FETCH cli.rol r", Comentario.class
            ).getResultList();
        } finally {
            session.close();
        }
    }
}
