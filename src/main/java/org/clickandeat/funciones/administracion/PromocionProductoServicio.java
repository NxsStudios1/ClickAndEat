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

    public List<PromocionProducto> obtenerPorPromocion(Promocion promocion) {
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

}