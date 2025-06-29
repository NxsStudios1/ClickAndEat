package org.clickandeat.modelo.baseDatos.dao;

import org.clickandeat.modelo.entidades.base.Entidad;

import java.util.List;

public interface DaoInterface<T extends Entidad>{
    List<T> findAll( );
    boolean guardar( T t );
    boolean actualizar( T t );
    boolean eliminar( T t );
    T findById( int id );

}
