package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionDao;
import org.clickandeat.modelo.entidades.inventario.Promocion;
import org.clickandeat.modelo.entidades.inventario.PromocionProducto;

import java.time.LocalDate;
import java.util.List;

public class PromocionServicio {
    private final PromocionDao promocionDao;

    public PromocionServicio(PromocionDao promocionDao){
        this.promocionDao = promocionDao;
    }

    /**
     * Obtiene todas las promociones con sus productos asociados (evita LazyInitializationException)
     */
    public List<Promocion> obtenerTodosConProductos() {
        List<Promocion> lista = promocionDao.findAllWithProductos();
        LocalDate ahora = LocalDate.now();
        for (Promocion p : lista) {
            if (p.getFechaFin().isBefore(ahora) && (p.getActivo() == null || p.getActivo())) {
                p.setActivo(false);
                promocionDao.actualizar(p);
            }
        }
        return lista;
    }

    public boolean guardarPromocion(Promocion promocion, List<PromocionProducto> productosPromo, PromocionProductoServicio promoProdServ) {
        boolean exito = promocionDao.guardar(promocion);
        if (exito) {
            for (PromocionProducto pp : productosPromo) {
                pp.setPromocion(promocion);
                promoProdServ.guardarPromocionProducto(pp);
            }
        }
        return exito;
    }

    public boolean actualizarPromocion(Promocion promocion, List<PromocionProducto> productosPromo, PromocionProductoServicio promoProdServ) {
        boolean exito = promocionDao.actualizar(promocion);
        if (exito) {
            promoProdServ.eliminarPorPromocion(promocion); // elimina los viejos
            for (PromocionProducto pp : productosPromo) {
                pp.setPromocion(promocion);
                promoProdServ.guardarPromocionProducto(pp);
            }
        }
        return exito;
    }

    public boolean eliminarPromocion(Promocion promocion) {
        return promocionDao.eliminar(promocion);
    }
}