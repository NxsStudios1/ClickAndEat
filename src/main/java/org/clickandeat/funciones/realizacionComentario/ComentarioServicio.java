package org.clickandeat.funciones.realizacionComentario;

import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.ComentarioDao;
import org.clickandeat.modelo.entidades.comentario.Comentario;

public class ComentarioServicio {
    private final ComentarioDao comentarioDao;

    public ComentarioServicio(ComentarioDao comentarioDao) {
        this.comentarioDao = comentarioDao;
    }

    public String validarYGuardar(Comentario comentario) {
        if (comentario.getAsunto() == null || comentario.getAsunto().isBlank()) {
            return "El asunto es obligatorio.";
        }
        if (comentario.getContenido() == null || comentario.getContenido().isBlank()) {
            return "El contenido es obligatorio.";
        }
        if (comentario.getCalificacion() == null || comentario.getCalificacion() < 1 || comentario.getCalificacion() > 5) {
            return "La calificación debe estar entre 1 y 5.";
        }
        if (comentario.getCategoria() == null) {
            return "Debes seleccionar una categoría.";
        }
        if (comentario.getCliente() == null) {
            return "No se pudo identificar el usuario.";
        }
        // Puedes agregar más validaciones si lo deseas

        boolean ok = comentarioDao.guardar(comentario);
        if (!ok)
            return "Ocurrió un error al guardar el comentario en la base de datos.";

        return null;
    }
}
