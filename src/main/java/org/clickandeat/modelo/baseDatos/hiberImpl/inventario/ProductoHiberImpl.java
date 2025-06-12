package org.clickandeat.modelo.baseDatos.hiberImpl.inventario;

import lombok.NoArgsConstructor;
import org.clickandeat.modelo.baseDatos.HiberInterface;
import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.modelo.entidades.inventario.CategoriaProducto;
import org.clickandeat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
public class ProductoHiberImpl implements HiberInterface<Producto>
{
    private static ProductoHiberImpl productoHiber;

    public static ProductoHiberImpl getInstance()
    {
        if(productoHiber == null)
        {
            productoHiber = new ProductoHiberImpl();
        }
        return productoHiber;
    }

    @Override
    public List<Producto> findAll()
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        List<Producto> list = session
                .createQuery("FROM Producto ORDER BY nombre", Producto.class)
                .getResultList();

        session.close();
        return list;
    }

    @Override
    public boolean guardar(Producto producto)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.persist(producto);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean actualizar(Producto producto)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.merge(producto);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean eliminar(Producto producto)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.remove(producto);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Producto findById(int id)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Producto producto = session
                .get(Producto.class, id);

        session.close();
        return producto;
    }

    // ================= MÉTODOS ESPECÍFICOS PARA INVENTARIO =================

    /**
     * Busca productos por nombre (búsqueda parcial, útil para filtros en Swing)
     */

    public List<Producto> findByNombre(String nombre)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Producto> query = session.createQuery(
                "FROM Producto WHERE nombre LIKE :nombre ORDER BY nombre", Producto.class);
        query.setParameter("nombre", "%" + nombre + "%");

        List<Producto> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Obtiene productos disponibles por categoría (para el menú del cliente)
     */
    public List<Producto> findProductosDisponiblesPorCategoria(int categoriaId)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Producto> query = session.createQuery(
                "FROM Producto WHERE categoria.idCategoria = :categoriaId AND disponible = true ORDER BY nombre", Producto.class);
        query.setParameter("categoriaId", categoriaId);

        List<Producto> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Obtiene solo productos disponibles (para mostrar en el menú al cliente)
     */
    public List<Producto> findProductosDisponibles()
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Producto> query = session.createQuery(
                "FROM Producto WHERE disponible = true ORDER BY categoria.nombre, nombre", Producto.class);

        List<Producto> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Busca productos por rango de precios (útil para filtros)
     */

    public List<Producto> findByRangoPrecios(Double precioMin, Double precioMax)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Producto> query = session.createQuery(
                "FROM Producto WHERE precio BETWEEN :precioMin AND :precioMax ORDER BY precio", Producto.class);
        query.setParameter("precioMin", precioMin);
        query.setParameter("precioMax", precioMax);

        List<Producto> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Cambia la disponibilidad de un producto (activar/desactivar)
     */
    public boolean cambiarDisponibilidad(int productoId, boolean disponible)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        Producto producto = session.get(Producto.class, productoId);
        if (producto != null) {
            producto.setDisponible(disponible);
            session.merge(producto);
            session.getTransaction().commit();
            session.close();
            return true;
        }

        session.getTransaction().rollback();
        session.close();
        return false;
    }

    /**
     * Actualiza el precio de un producto
     */

    public boolean actualizarPrecio(int productoId, Double nuevoPrecio)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        Producto producto = session.get(Producto.class, productoId);
        if (producto != null && nuevoPrecio > 0) {
            producto.setPrecio(nuevoPrecio);
            session.merge(producto);
            session.getTransaction().commit();
            session.close();
            return true;
        }

        session.getTransaction().rollback();
        session.close();
        return false;
    }

    /**
     * Verifica si un producto puede ser preparado (todos sus ingredientes tienen stock)
     */
    public boolean puedeSerPreparado(int productoId)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        // Consulta que verifica si algún ingrediente no tiene stock suficiente
        Query<Long> query = session.createQuery(
                "SELECT COUNT(pi) FROM ProductoIngrediente pi " +
                        "WHERE pi.producto.idProducto = :productoId " +
                        "AND pi.ingrediente.stockActual < pi.cantidadNecesaria", Long.class);
        query.setParameter("productoId", productoId);

        Long ingredientesSinStock = query.getSingleResult();
        session.close();

        return ingredientesSinStock == 0;
    }

    /**
     * Obtiene el total de productos disponibles
     */

    public Long contarProductosDisponibles()
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Long> query = session.createQuery(
                "SELECT COUNT(p) FROM Producto p WHERE p.disponible = true", Long.class);

        Long count = query.getSingleResult();
        session.close();
        return count;
    }
}