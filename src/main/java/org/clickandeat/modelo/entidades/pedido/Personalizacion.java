package org.clickandeat.modelo.entidades.pedido;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"personalizacionesPedido"})
@Table(name = "personalizacion")
public class Personalizacion extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersonalizacion", nullable = false)
    private Integer idPersonalizacion;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "tipo", length = 30, nullable = false)
    private String tipo;

    @Column(name = "precio_adicional", precision = 10, scale = 2)
    private BigDecimal precioAdicional = BigDecimal.ZERO;

    @OneToMany(mappedBy = "personalizacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonalizacionPedido> personalizacionesPedido;
}