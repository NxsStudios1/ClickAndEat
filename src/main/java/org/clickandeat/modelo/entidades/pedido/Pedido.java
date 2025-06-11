package org.clickandeat.modelo.entidades.pedido;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.usuario.Usuario;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"detalles"})
@Table(name = "pedido")
public class Pedido extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPedido", nullable = false)
    private Integer idPedido;

    @Column(name = "numeroTicket", length = 20, unique = true, nullable = false)
    private String numeroTicket;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPedidoEnum estado = EstadoPedidoEnum.PENDIENTE;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "fechaPedido")
    private LocalDateTime fechaPedido = LocalDateTime.now();

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodoPagoPrevisto")
    private MetodoPagoEnum metodoPagoPrevisto = MetodoPagoEnum.EFECTIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente")
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles;

    @PrePersist
    protected void onCreate() {
        if (fechaPedido == null) {
            fechaPedido = LocalDateTime.now();
        }
        if (estado == null) {
            estado = EstadoPedidoEnum.PENDIENTE;
        }
        if (metodoPagoPrevisto == null) {
            metodoPagoPrevisto = MetodoPagoEnum.EFECTIVO;
        }
    }
}
