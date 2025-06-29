package org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;
import org.clickandeat.modelo.entidades.comentario.Comentario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RespuestaComentarioDao extends DaoImpl<RespuestaComentario> {

    public RespuestaComentarioDao() {
        super(RespuestaComentario.class);
    }

    public void eliminarPorComentarioId(int comentarioId) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM RespuestaComentario r WHERE r.comentario.id = :id")
                    .setParameter("id", comentarioId)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void eliminarComentarioPorId(int comentarioId) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM Comentario c WHERE c.id = :id")
                    .setParameter("id", comentarioId)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<Comentario> obtenerComentariosSinFiltrar() {
        try (Session session = openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT c FROM Comentario c LEFT JOIN FETCH c.respuestas ORDER BY c.fechaComentario DESC",
                    Comentario.class
            ).list();
        }
    }

}