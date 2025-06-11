package org.clickandeat.modelo.entidades.inventario;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.usuario.Usuario;

import java.time.LocalDateTime;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "tbl_ingrediente")

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
    private UnidadMedida unidadMedida;

    public enum UnidadMedida {
        GRAMOS("gramos", "g"),
        LITROS("litros", "L"),
        MILILITROS("mililitros", "mL"),
        UNIDADES("unidades", "unidad"),
        KILOGRAMOS("kilogramos", "kg");

        private final String nombre;
        private final String abreviacion;

        UnidadMedida(String nombre, String abreviacion) {
            this.nombre = nombre;
            this.abreviacion = abreviacion;
        }

        public String getNombre() {
            return nombre;
        }

        public String getAbreviacion() {
            return abreviacion;
        }

        @Override
        public String toString() {
            return nombre;
        }

        @Column(name = "stockActual", nullable = false)
        private Double stockActual = 0.0;

        @Column(name = "stockMinimo", nullable = false)
        private Double stockMinimo = 2.0;

        @Column(name = "precioUnitario", nullable = false)
        private Double precioUnitario;
    }
}




