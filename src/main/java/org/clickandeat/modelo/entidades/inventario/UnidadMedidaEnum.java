package org.clickandeat.modelo.entidades.inventario;

public enum UnidadMedidaEnum {
    GRAMOS("Gramos"),
    LITROS("litros"),
    MILILITROS("mililitros"),
    UNIDADES("Unidades"),
    KILOGRAMOS("Kilogramos");

    private final String valor;

    UnidadMedidaEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}