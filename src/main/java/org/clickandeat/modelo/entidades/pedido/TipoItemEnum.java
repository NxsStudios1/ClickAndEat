package org.clickandeat.modelo.entidades.pedido;

public enum TipoItemEnum {

    PRODUCTO(1),
    PROMOCION(2);

    private final int idTipo;

    TipoItemEnum(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public static TipoItemEnum getById(int id) {
        for (TipoItemEnum tipo : values()) {
            if (tipo.idTipo == id) return tipo;
        }
        return null;
    }

}