package org.clickandeat.modelo.baseDatos.hiberImpl;

import lombok.NoArgsConstructor;

import org.clickandeat.modelo.baseDatos.HiberInterface;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.usuario.Usuario;
import org.clickandeat.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

@NoArgsConstructor
public class UsuarioHiberImpl implements HiberInterface<Usuario>
{
    private static UsuarioHiberImpl usuarioHiber;

    public static UsuarioHiberImpl getInstance()
    {
        if(usuarioHiber==null)
        {
            usuarioHiber = new UsuarioHiberImpl();
        }
        return usuarioHiber;
    }

    @Override
    public List<Usuario> findAll()
    {
        Session session = org.clickandeat.util.HibernateUtil.getSession();
        assert session != null;
        List<Usuario> list = session
                .createQuery("FROM usuario", Usuario.class)
                .getResultList();

        session.close();
        return list;
    }

    @Override
    public boolean guardar(Usuario usuario)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.persist(usuario);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean actualizar(Usuario usuario)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.merge(usuario);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean eliminar(Usuario usuario)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.remove(usuario);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Usuario findById(int id)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Usuario usuario = session
                .get(Usuario.class, id);

        session.close();
        return usuario;
    }
}