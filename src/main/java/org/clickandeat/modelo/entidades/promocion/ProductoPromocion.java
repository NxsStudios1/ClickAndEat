package org.clickandeat.modelo.entidades.promocion;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.inventario.Producto;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "productoPromocion",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idProducto", "idPromocion"}))
public class ProductoPromocion extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProductoPromocion", nullable = false)
    private Integer idProductoPromocion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPromocion", nullable = false)
    private Promocion promocion;
}