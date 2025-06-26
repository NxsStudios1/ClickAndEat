package org.clickandeat.funciones.cliente;

import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.ComentarioDao;
import org.clickandeat.modelo.entidades.comentario.Comentario;

import java.util.List;

public class ComentarioServicio {
    private final ComentarioDao comentarioDao;

    public ComentarioServicio(ComentarioDao comentarioDao) {
        this.comentarioDao = comentarioDao;
    }

    public String validarYGuardar(Comentario comentario) {
        if (comentario.getAsunto() == null || comentario.getAsunto().isBlank()) {
            return "  El Asunto es Obligatorio.";
        }
        if (comentario.getContenido() == null || comentario.getContenido().isBlank()) {
            return "  El Contenido es Obligatorio.";
        }

        boolean ok = comentarioDao.guardar(comentario);
        if (!ok)
            return " Ocurrió un Error al Guardar el Comentario en la Base de Datos.";

        return null;
    }

    public boolean actualizarComentario(Comentario comentario) {
        return comentarioDao.actualizar(comentario);
    }

    public List<Comentario> obtenerTodos() {
        return comentarioDao.findAllConRespuestasYCliente();
    }

    public Comentario buscarPorId(int id) {
        return comentarioDao.findById(id);
    }

    public boolean eliminar(Comentario comentario) {
        return comentarioDao.eliminar(comentario);
    }
}
