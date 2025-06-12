package org.clickandeat.modelo.baseDatos.dao;

import org.clickandeat.modelo.baseDatos.hibernate.HibernateUtil;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DaoImpl<T extends Entidad> implements DaoInterface<T> {
    private final Class<T> claseEntidad;

    protected final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public DaoImpl(Class<T> claseEntidad) {
        this.claseEntidad = claseEntidad;
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public List<T> findAll() {
        Session session = openSession();
        List<T> lista = null;
        try {
            lista = session.createQuery("FROM " + claseEntidad.getSimpleName(), claseEntidad).list();
        } finally {
            session.close();
        }
        return lista;
    }

    @Override
    public boolean guardar(T t) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            session.save(t);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(T t) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            session.update(t);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(T t) {
        Transaction tx = null;
        try (Session session = openSession()) {
            tx = session.beginTransaction();
            session.delete(t);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public T findById(int id) {
        Session session = openSession();
        try {
            return session.get(claseEntidad, id);
        } finally {
            session.close();
        }
    }
}