package org.clickandeat.modelo.entidades.usuario;

public enum RolEnum {
    ADMINISTRADOR (1),
    CLIENTE (2);

    private Integer idRol;

    RolEnum(Integer idRol){
        this.idRol = idRol;
    }

    public static RolEnum getId(Integer idRol){
        return switch (idRol){
            case 1 -> ADMINISTRADOR;
            case 2 -> CLIENTE;
            default -> null;
        };
    }

    public Integer getIdRol(){
        return idRol;
    }
}
