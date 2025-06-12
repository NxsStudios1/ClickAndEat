package org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao;

import org.clickandeat.modelo.baseDatos.dao.DaoImpl;
import org.clickandeat.modelo.entidades.inventario.Ingrediente;

public class IngredienteDao extends DaoImpl<Ingrediente> {
    public IngredienteDao(){
        super(Ingrediente.class);
    }



}
