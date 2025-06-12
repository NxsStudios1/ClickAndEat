package org.clickandeat.modelo.entidades.inventario;

public enum UnidadMedidaEnum {
    GRAMOS(1),
    LITROS(2),
    MILILITROS(3),
    UNIDADES(4),
    KILOGRAMOS(5);

    private final int idUnidad;

    UnidadMedidaEnum(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public static UnidadMedidaEnum getById(int id) {
        for (UnidadMedidaEnum um : values()) {
            if (um.idUnidad == id) return um;
        }
        return null;
    }
}
