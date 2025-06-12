package org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.comentario.Comentario;

public class ComentarioDao extends DaoImpl<Comentario> {
    public ComentarioDao(){
        super(Comentario.class);
    }

    // Puedes agregar métodos personalizados aquí, por ejemplo:
    // public Usuario buscarPorTelefono(String telefono) { ... }
}
