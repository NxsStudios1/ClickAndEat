package org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;

import java.util.List;

public class RespuestaComentarioDao extends DaoImpl<RespuestaComentario> {
    public RespuestaComentarioDao(){
        super(RespuestaComentario.class);
    }

    public RespuestaComentario buscarPorComentarioId(Long idComentario) {
        // Asegúrate que sessionFactory es visible aquí
        var session = sessionFactory.openSession();
        try {
            List<RespuestaComentario> resultados = session.createQuery(
                            "FROM RespuestaComentario WHERE comentario.id = :idComentario", RespuestaComentario.class)
                    .setParameter("idComentario", idComentario)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            session.close();
        }
    }

}
