package org.clickandeat.modelo.entidades.comentario;

public enum CategoriaComentarioEnum {

    COMIDA(1),
    SERVICIO(2),
    AMBIENTE(3),
    TIEMPO_ESPERA(4),
    GENERAL(5);

    private final int idCategoria;

    CategoriaComentarioEnum(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public static CategoriaComentarioEnum getById(int id) {
        for (CategoriaComentarioEnum cat : values()) {
            if (cat.idCategoria == id) return cat;
        }
        return null;
    }
}
