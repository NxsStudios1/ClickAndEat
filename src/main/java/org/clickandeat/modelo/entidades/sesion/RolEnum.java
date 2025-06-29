package org.clickandeat.modelo.entidades.sesion;

public enum RolEnum {

    CLIENTE(1),
    ADMINISTRADOR(2);

    private final int idRol;

    RolEnum(int idRol){
        this.idRol = idRol;
    }

    public int getIdRol(){
        return idRol;
    }

    public static RolEnum getById(int id){
        for(RolEnum rol: values()){
            if(rol.idRol == id) return rol;
        }
        return null;
    }

}
