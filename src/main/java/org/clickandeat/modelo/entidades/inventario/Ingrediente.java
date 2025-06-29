package org.clickandeat.modelo.entidades.inventario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_ingrediente")

public class Ingrediente extends Entidad {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false)
    private Double cantidadPorcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadMedidaEnum unidadMedida;

    @Column(nullable = false)
    private Double stockActual;

    @Column(nullable = false)
    private Double precioUnitario;

}