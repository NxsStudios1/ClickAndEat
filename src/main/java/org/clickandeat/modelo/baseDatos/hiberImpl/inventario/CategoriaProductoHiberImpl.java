package org.clickandeat.modelo.baseDatos.hiberImpl.inventario;

import lombok.NoArgsConstructor;
import org.clickandeat.modelo.baseDatos.HiberInterface;
import org.clickandeat.modelo.entidades.inventario.CategoriaProducto;
import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

@NoArgsConstructor
public class CategoriaProductoHiberImpl implements HiberInterface<CategoriaProducto>
{
    private static CategoriaProductoHiberImpl categoriaProductoHiber;

    public static CategoriaProductoHiberImpl getInstance()
    {
        if(categoriaProductoHiber == null)
        {
            categoriaProductoHiber = new CategoriaProductoHiberImpl();
        }
        return categoriaProductoHiber;
    }

    @Override
    public List<CategoriaProducto> findAll()
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        List<CategoriaProducto> list = session
                .createQuery("FROM CategoriaProducto ORDER BY nombre", CategoriaProducto.class)
                .getResultList();

        session.close();
        return list;
    }

    @Override
    public boolean guardar(CategoriaProducto categoriaProducto)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.persist(categoriaProducto);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean actualizar(CategoriaProducto categoriaProducto)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.merge(categoriaProducto);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean eliminar(CategoriaProducto categoriaProducto)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.remove(categoriaProducto);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public CategoriaProducto findById(int id)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        CategoriaProducto categoriaProducto = session
                .get(CategoriaProducto.class, id);

        session.close();
        return categoriaProducto;
    }

    // ================= MÉTODOS ESPECÍFICOS PARA GESTIÓN DE CATEGORÍAS =================

    /**
     * Busca categoría por nombre exacto (útil para validaciones)
     */

    public CategoriaProducto findByNombreExacto(String nombre)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<CategoriaProducto> query = session.createQuery(
                "FROM CategoriaProducto WHERE nombre = :nombre", CategoriaProducto.class);
        query.setParameter("nombre", nombre);

        CategoriaProducto categoria = query.uniqueResult();
        session.close();
        return categoria;
    }

    /**
     * Verifica si existe una categoría con el mismo nombre (evitar duplicados)
     */

    public boolean existeCategoriaPorNombre(String nombre)
    {
        return findByNombreExacto(nombre) != null;
    }

    /**
     * Verifica si existe una categoría con el mismo nombre, excluyendo un ID específico
     * (útil para validar al actualizar)
     */

    public boolean existeCategoriaPorNombre(String nombre, int excluirId)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Long> query = session.createQuery(
                "SELECT COUNT(c) FROM CategoriaProducto c WHERE c.nombre = :nombre AND c.idCategoria != :excluirId", Long.class);
        query.setParameter("nombre", nombre);
        query.setParameter("excluirId", excluirId);

        Long count = query.getSingleResult();
        session.close();
        return count > 0;
    }
}