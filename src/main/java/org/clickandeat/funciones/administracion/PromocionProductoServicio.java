package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionProductoDao;
import org.clickandeat.modelo.entidades.inventario.Promocion;
import org.clickandeat.modelo.entidades.inventario.PromocionProducto;

import java.util.List;

public class PromocionProductoServicio {

    private final PromocionProductoDao promocionProductoDao;

    public PromocionProductoServicio(PromocionProductoDao promocionProductoDao){
        this.promocionProductoDao = promocionProductoDao;
    }

    public List<PromocionProducto> obtenerTodos() {
        return promocionProductoDao.findAll();
    }

    public boolean guardarPromocionProducto(PromocionProducto pp) {
        return promocionProductoDao.guardar(pp);
    }

    public boolean actualizarPromocionProducto(PromocionProducto pp) {
        return promocionProductoDao.actualizar(pp);
    }

    public boolean eliminarPromocionProducto(PromocionProducto pp) {
        return promocionProductoDao.eliminar(pp);
    }

    public List<PromocionProducto> obtenerPorPromocion(Promocion promocion) {
        // Si tienes un método específico en el DAO, úsalo; si no, filtra manualmente
        List<PromocionProducto> all = promocionProductoDao.findAll();
        return all.stream()
                .filter(pp -> pp.getPromocion() != null && pp.getPromocion().getId().equals(promocion.getId()))
                .toList();
    }

    public boolean eliminarPorPromocion(Promocion promocion) {
        List<PromocionProducto> list = obtenerPorPromocion(promocion);
        boolean ok = true;
        for (PromocionProducto pp : list) {
            ok &= promocionProductoDao.eliminar(pp);
        }
        return ok;
    }

    public List<PromocionProducto> obtenerPorProductoId(Integer idProducto) {
        List<PromocionProducto> all = promocionProductoDao.findAll();
        return all.stream()
                .filter(pp -> pp.getProducto() != null && pp.getProducto().getId().equals(idProducto))
                .toList();
    }
}