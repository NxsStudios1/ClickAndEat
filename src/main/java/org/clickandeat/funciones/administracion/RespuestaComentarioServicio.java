package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.RespuestaComentarioDao;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;

public class RespuestaComentarioServicio {
    private final RespuestaComentarioDao respuestaComentarioDao;

    public RespuestaComentarioServicio(RespuestaComentarioDao respuestaComentarioDao){
        this.respuestaComentarioDao = respuestaComentarioDao;
    }

    public String validarYGuardar(RespuestaComentario respuestaComentario) {
        if (respuestaComentario.getContenido() == null || respuestaComentario.getContenido().isBlank()) {
            return "El Contenido es Obligatorio.";
        }

        boolean ok = respuestaComentarioDao.guardar(respuestaComentario);
        if (!ok)
            return "Ocurrió un Error al Guardar la Respuesta en la Base de Datos.";
        return null;
    }
}