package org.clickandeat.modelo.entidades.pedido;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.modelo.entidades.inventario.Promocion;
import org.clickandeat.modelo.entidades.base.Entidad;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_detallePedido")

public class DetallePedido extends Entidad {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoItemEnum tipoItem;

    @Column(nullable = false)
    private Integer cantidad = 1;

    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idPromocion")
    private Promocion promocion;

    @ManyToOne
    @JoinColumn(name = "idPedido", nullable = false)
    private Pedido pedido;

}
