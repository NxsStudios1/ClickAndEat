package org.clickandeat.modelo.entidades.pedido;

public enum EstadoPedidoEnum {
    PENDIENTE(1),
    EN_PROCESO(2),
    TERMINADO(3),
    PAGADO(4),
    CANCELADO(5);

    private final int idEstado;

    EstadoPedidoEnum(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public static EstadoPedidoEnum getById(int id) {
        for (EstadoPedidoEnum estado : values()) {
            if (estado.idEstado == id) return estado;
        }
        return null;
    }
}
