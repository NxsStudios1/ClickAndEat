package org.clickandeat.modelo.baseDatos;

import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class DaoImpl<T extends Entidad> implements DaoInterface<T> {
    private Class<T> clase;


    public DaoImpl(Class<T> clase) {
        this.clase = clase;
    }

    @Override
    public List<T> findAll() {
        Session session = HibernateUtil.getSession();
        List<T> lista = session.createQuery("from " + clase.getSimpleName(), clase).getResultList();
        session.close();
        return lista;
    }

    @Override
    public List<T> findAllWithFetch(String fetchQuery) {
        Session session = HibernateUtil.getSession();
        List<T> lista = session.createQuery(fetchQuery, clase).getResultList();
        session.close();
        return lista;
    }

    @Override
    public boolean guardar(T e) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(e);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean actualizar(T e) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(e);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean eliminar(T e) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(e);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public T findById(int id) {
        Session session = HibernateUtil.getSession();
        T obj = session.get(clase, id);
        session.close();
        return obj;
    }

}