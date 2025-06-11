package org.clickandeat.modelo.entidades.promocion;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.modelo.entidades.pedido.DetallePedido;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "detallePromocionPedido",
        indexes = @Index(name = "idx_detalle_promocion", columnList = "idDetallePedido"))

public class DetallePromocionPedido extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetallePromocionPedido", nullable = false)
    private Integer idDetallePromocionPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDetallePedido", nullable = false)
    private DetallePedido detallePedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad = 1;
}