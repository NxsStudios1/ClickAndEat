package org.clickandeat.funciones.administracion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.IngredienteDao;
import org.clickandeat.modelo.entidades.inventario.Ingrediente;

import java.util.List;

public class InventarioServicio {
    private final IngredienteDao ingredienteDao;

    public InventarioServicio(IngredienteDao ingredienteDao) {
        this.ingredienteDao = ingredienteDao;
    }

    public String validarIngrediente(Ingrediente ingrediente) {
        if (ingrediente.getNombre() == null || ingrediente.getNombre().isBlank()) {
            return "  El Nombre del Ingrediente es Obligatorio.";
        }
        return null;
    }

    public boolean guardarIngrediente(Ingrediente ingrediente) {
        String error = validarIngrediente(ingrediente);
        if (error != null) throw new IllegalArgumentException(error);
        return ingredienteDao.guardar(ingrediente);
    }

    public boolean actualizarIngrediente(Ingrediente ingrediente) {
        String error = validarIngrediente(ingrediente);
        if (error != null) throw new IllegalArgumentException(error);
        return ingredienteDao.actualizar(ingrediente);
    }

    public boolean eliminarIngrediente(Ingrediente ingrediente) {
        return ingredienteDao.eliminar(ingrediente);
    }

    public Ingrediente buscarIngredientePorId(int id) {
        return ingredienteDao.findById(id);
    }

    public List<Ingrediente> obtenerTodos() {
        return ingredienteDao.findAll();
    }
}