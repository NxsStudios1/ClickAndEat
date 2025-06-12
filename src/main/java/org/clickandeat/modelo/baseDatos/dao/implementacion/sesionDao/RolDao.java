package org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.baseDatos.hibernate.HibernateUtil;
import org.clickandeat.modelo.entidades.sesion.Rol;
import org.clickandeat.modelo.entidades.sesion.RolEnum;
import org.hibernate.Session;

public class RolDao extends DaoImpl<Rol> {
    public RolDao(){
        super(Rol.class);
    }

    public Rol findByTipo(RolEnum tipo) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM tbl_rol WHERE tipo = :tipo", Rol.class)
                    .setParameter("tipo", tipo)
                    .uniqueResult();
        }
    }

    // Puedes agregar métodos personalizados aquí, por ejemplo:
    // public Usuario buscarPorTelefono(String telefono) { ... }
}
