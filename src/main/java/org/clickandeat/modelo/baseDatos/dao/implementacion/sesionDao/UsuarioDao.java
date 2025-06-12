package org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.baseDatos.hibernate.HibernateUtil;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.hibernate.Session;

public class UsuarioDao extends DaoImpl<Usuario> {
    public UsuarioDao(){
        super(Usuario.class);
    }

    public Usuario findByTelefono(String telefono) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Usuario usuario = session.createQuery(
                            "FROM Usuario WHERE telefono = :tel", Usuario.class)
                    .setParameter("tel", telefono)
                    .uniqueResult();
            if (usuario != null && usuario.getRol() != null) {
                usuario.getRol().getTipo();
            }
            return usuario;
        }
    }


    public Usuario findByTelefonoYContrasena(String telefono, String contrasena) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM tbl_usuario WHERE telefono = :tel AND contrasena = :pass", Usuario.class)
                    .setParameter("tel", telefono)
                    .setParameter("pass", contrasena)
                    .uniqueResult();
        }

        // Puedes agregar métodos personalizados aquí, por ejemplo:
        // public Usuario buscarPorTelefono(String telefono) { ... }

    }
}
