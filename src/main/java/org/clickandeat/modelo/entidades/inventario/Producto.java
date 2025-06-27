package org.clickandeat.modelo.entidades.inventario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_producto")

public class Producto extends Entidad {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 300)
    private String descripcion;

    @Column(nullable = false)
    private Double precio;

    @Column
    private Boolean disponible = true;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private CategoriaProducto categoria;

    @OneToMany(mappedBy = "producto")
    private List<ProductoIngrediente> ingredientes;

    @OneToMany(mappedBy = "producto")
    private List<PromocionProducto> promociones;
}
