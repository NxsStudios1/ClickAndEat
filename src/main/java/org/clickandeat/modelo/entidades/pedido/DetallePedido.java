package org.clickandeat.modelo.entidades.pedido;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.modelo.entidades.promocion.DetallePromocionPedido;
import org.clickandeat.modelo.entidades.promocion.Promocion;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"personalizaciones", "detallesPromocion"})
@Table(name = "detallePedido")
public class DetallePedido extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetallePedido", nullable = false)
    private Integer idDetallePedido;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad = 1;

    @Column(name = "precioUnitario", precision = 10, scale = 2, nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", precision = 10, scale = 2, nullable = false)
    private BigDecimal subtotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoItem", nullable = false)
    private TipoItemEnum tipoItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPedido")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPromocion")
    private Promocion promocion;

    @OneToMany(mappedBy = "detallePedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalizacionPedido> personalizaciones;

    @OneToMany(mappedBy = "detallePedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePromocionPedido> detallesPromocion;

    @PrePersist
    @PreUpdate
    private void validarConstraints() {
        if (tipoItem == TipoItemEnum.PRODUCTO && (producto == null || promocion != null)) {
            throw new IllegalStateException("Para tipoItem PRODUCTO, debe existir producto y no promocion");
        }
        if (tipoItem == TipoItemEnum.PROMOCION && (promocion == null || producto != null)) {
            throw new IllegalStateException("Para tipoItem PROMOCION, debe existir promocion y no producto");
        }
    }
}