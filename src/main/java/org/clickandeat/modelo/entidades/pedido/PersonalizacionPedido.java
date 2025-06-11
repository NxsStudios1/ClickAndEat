package org.clickandeat.modelo.entidades.pedido;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "personalizacionPedido")
public class PersonalizacionPedido extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersonalizacionPedido", nullable = false)
    private Integer idPersonalizacionPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idDetallePedido")
    private DetallePedido detallePedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersonalizacion")
    private Personalizacion personalizacion;
}