package org.clickandeat.modelo.entidades.inventario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_productoIngrediente")

public class ProductoIngrediente extends Entidad {

    @Column(nullable = false)
    private Double cantidadNecesaria;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idIngrediente", nullable = false)
    private Ingrediente ingrediente;

}
