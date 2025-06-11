package org.clickandeat.modelo.entidades.inventario;

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
@ToString(callSuper = true, exclude = {"productosIngredientes"})
@Table(name = "ingrediente")
public class Ingrediente extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIngrediente", nullable = false)
    private Integer idIngrediente;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @Column(name = "cantidadPorcion", nullable = false)
    private Double cantidadPorcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidadMedida", nullable = false)
    private UnidadMedidaEnum unidadMedida;

    @Column(name = "stockActual", nullable = false)
    private Integer stockActual = 0;

    @Column(name = "stockMinimo", nullable = false)
    private Integer stockMinimo = 2;

    @Column(name = "precioUnitario", nullable = false)
    private Double precioUnitario;

    @OneToMany(mappedBy = "ingrediente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductoIngrediente> productosIngredientes;
}