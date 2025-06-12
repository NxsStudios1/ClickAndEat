package org.clickandeat.modelo.baseDatos.hiberImpl.inventario;

import lombok.NoArgsConstructor;
import org.clickandeat.modelo.baseDatos.HiberInterface;
import org.clickandeat.modelo.entidades.inventario.Ingrediente;
import org.clickandeat.modelo.entidades.inventario.UnidadMedidaEnum;
import org.clickandeat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

@NoArgsConstructor
public class IngredienteHiberImpl implements HiberInterface<Ingrediente>
{
    private static IngredienteHiberImpl ingredienteHiber;

    public static IngredienteHiberImpl getInstance()
    {
        if(ingredienteHiber == null)
        {
            ingredienteHiber = new IngredienteHiberImpl();
        }
        return ingredienteHiber;
    }

    @Override
    public List<Ingrediente> findAll()
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        List<Ingrediente> list = session
                .createQuery("FROM Ingrediente", Ingrediente.class)
                .getResultList();

        session.close();
        return list;
    }

    @Override
    public boolean guardar(Ingrediente ingrediente)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.persist(ingrediente);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean actualizar(Ingrediente ingrediente)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.merge(ingrediente);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean eliminar(Ingrediente ingrediente)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.remove(ingrediente);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public Ingrediente findById(int id)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Ingrediente ingrediente = session
                .get(Ingrediente.class, id);

        session.close();
        return ingrediente;
    }

    // Métodos específicos para Ingrediente

    public List<Ingrediente> findByNombre(String nombre)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Ingrediente> query = session.createQuery(
                "FROM Ingrediente WHERE nombre LIKE :nombre", Ingrediente.class);
        query.setParameter("nombre", "%" + nombre + "%");

        List<Ingrediente> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Busca ingredientes por unidad de medida
     */
    public List<Ingrediente> findByUnidadMedida(UnidadMedidaEnum unidadMedida)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Ingrediente> query = session.createQuery(
                "FROM Ingrediente WHERE unidadMedida = :unidadMedida", Ingrediente.class);
        query.setParameter("unidadMedida", unidadMedida);

        List<Ingrediente> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Busca ingredientes con stock bajo (menor al mínimo)
     */
    public List<Ingrediente> findIngredientesStockBajo()
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Ingrediente> query = session.createQuery(
                "FROM Ingrediente WHERE stockActual < stockMinimo", Ingrediente.class);

        List<Ingrediente> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Busca ingredientes con stock suficiente (mayor a 0)
     */
    public List<Ingrediente> findIngredientesDisponibles()
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Query<Ingrediente> query = session.createQuery(
                "FROM Ingrediente WHERE stockActual > 0", Ingrediente.class);

        List<Ingrediente> list = query.getResultList();
        session.close();
        return list;
    }

    /**
     * Actualiza el stock de un ingrediente
     */
    public boolean actualizarStock(int ingredienteId, int nuevasCantidad)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        Ingrediente ingrediente = session.get(Ingrediente.class, ingredienteId);
        if (ingrediente != null) {
            ingrediente.setStockActual(nuevasCantidad);
            session.merge(ingrediente);
            session.getTransaction().commit();
            session.close();
            return true;
        }

        session.getTransaction().rollback();
        session.close();
        return false;
    }

    /**
     * Reduce el stock de un ingrediente (para cuando se hace un pedido)
     */
    public boolean reducirStock(int ingredienteId, int cantidadAReducir)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        Ingrediente ingrediente = session.get(Ingrediente.class, ingredienteId);
        if (ingrediente != null && ingrediente.getStockActual() >= cantidadAReducir) {
            ingrediente.setStockActual(ingrediente.getStockActual() - cantidadAReducir);
            session.merge(ingrediente);
            session.getTransaction().commit();
            session.close();
            return true;
        }

        session.getTransaction().rollback();
        session.close();
        return false;
    }

    /**
     * Verifica si hay stock suficiente para un ingrediente
     */

    public boolean hayStockSuficiente(int ingredienteId, int cantidadRequerida)
    {
        Session session = HibernateUtil.getSession();
        assert session != null;

        Ingrediente ingrediente = session.get(Ingrediente.class, ingredienteId);
        session.close();

        return ingrediente != null && ingrediente.getStockActual() >= cantidadRequerida;
    }
}
