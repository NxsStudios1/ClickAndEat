package org.clickandeat.modelo.entidades.promocion;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"productos"})
@Table(name = "promocion")
public class Promocion extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPromocion", nullable = false)
    private Integer idPromocion;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 300)
    private String descripcion;

    @Column(name = "descuento_porcentaje")
    private Double descuentoPorcentaje;

    @Column(name = "fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fechaFin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "activo")
    private Boolean activo = true;

    @OneToMany(mappedBy = "promocion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoPromocion> productos;
}