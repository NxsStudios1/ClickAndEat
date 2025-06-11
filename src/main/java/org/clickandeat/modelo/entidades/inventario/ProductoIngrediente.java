package org.clickandeat.modelo.entidades.inventario;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "productoIngrediente",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idProducto", "idIngrediente"}))

public class ProductoIngrediente extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProductoIngrediente", nullable = false)
    private Integer idProductoIngrediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idIngrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(name = "cantidadNecesaria", nullable = false)
    private Double cantidadNecesaria;
}