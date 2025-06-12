package org.clickandeat.modelo.baseDatos.hiberImpl.inventario;

import lombok.NoArgsConstructor;
import org.clickandeat.modelo.baseDatos.HiberInterface;
import org.clickandeat.modelo.entidades.inventario.ProductoIngrediente;
import org.clickandeat.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

@NoArgsConstructor
public class ProductoIngredienteHiberImpl implements HiberInterface<ProductoIngrediente> {

    private static ProductoIngredienteHiberImpl productoIngredienteHiber;

    public static ProductoIngredienteHiberImpl getInstance() {
        if(productoIngredienteHiber == null) {
            productoIngredienteHiber = new ProductoIngredienteHiberImpl();
        }
        return productoIngredienteHiber;
    }

    @Override
    public List<ProductoIngrediente> findAll() {
        Session session = HibernateUtil.getSession();
        assert session != null;
        List<ProductoIngrediente> list = session
                .createQuery("FROM ProductoIngrediente", ProductoIngrediente.class)
                .getResultList();

        session.close();
        return list;
    }

    @Override
    public boolean guardar(ProductoIngrediente productoIngrediente) {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.persist(productoIngrediente);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean actualizar(ProductoIngrediente productoIngrediente) {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.merge(productoIngrediente);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public boolean eliminar(ProductoIngrediente productoIngrediente) {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        session.remove(productoIngrediente);
        session.getTransaction().commit();

        session.close();
        return true;
    }

    @Override
    public ProductoIngrediente findById(int id) {
        Session session = HibernateUtil.getSession();
        assert session != null;

        ProductoIngrediente productoIngrediente = session
                .get(ProductoIngrediente.class, id);

        session.close();
        return productoIngrediente;
    }

    // Métodos adicionales específicos para ProductoIngrediente

    /**
     * Busca una relación específica entre un producto e ingrediente
     */
    public ProductoIngrediente findByProductoAndIngrediente(int productoId, int ingredienteId) {
        Session session = HibernateUtil.getSession();
        assert session != null;

        ProductoIngrediente productoIngrediente = session
                .createQuery("FROM ProductoIngrediente pi WHERE pi.producto.id = :productoId AND pi.ingrediente.id = :ingredienteId", ProductoIngrediente.class)
                .setParameter("productoId", productoId)
                .setParameter("ingredienteId", ingredienteId)
                .uniqueResult();

        session.close();
        return productoIngrediente;
    }

    /**
     * Elimina todas las relaciones de un producto específico
     */
    public boolean eliminarByProductoId(int productoId) {
        Session session = HibernateUtil.getSession();
        assert session != null;
        session.beginTransaction();

        int deletedCount = session
                .createQuery("DELETE FROM ProductoIngrediente pi WHERE pi.producto.id = :productoId")
                .setParameter("productoId", productoId)
                .executeUpdate();

        session.getTransaction().commit();
        session.close();

        return deletedCount > 0;
    }

    /**
     * Método para verificar disponibilidad de stock antes de procesar un pedido
     * Verifica si hay suficientes ingredientes para hacer un producto
     */

    public boolean verificarDisponibilidadStock(int productoId, int cantidadProducto) {
        Session session = HibernateUtil.getSession();
        assert session != null;

        List<ProductoIngrediente> ingredientesRequeridos = session
                .createQuery("FROM ProductoIngrediente pi JOIN FETCH pi.ingrediente WHERE pi.producto.id = :productoId", ProductoIngrediente.class)
                .setParameter("productoId", productoId)
                .getResultList();

        session.close();

        // Verificar si cada ingrediente tiene suficiente stock
        for (ProductoIngrediente pi : ingredientesRequeridos) {
            double cantidadRequerida = pi.getCantidadNecesaria() * cantidadProducto;
            if (pi.getIngrediente().getStock() < cantidadRequerida) {
                return false; // No hay suficiente stock
            }
        }

        return true; // Hay suficiente stock para todos los ingredientes
    }
}