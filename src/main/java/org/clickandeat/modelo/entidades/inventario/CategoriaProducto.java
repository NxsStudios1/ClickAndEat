package org.clickandeat.modelo.entidades.inventario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_categoriaProducto")

public class CategoriaProducto extends Entidad {

    @Column(nullable = false, length = 50)
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    @Override
    public String toString() {
        return nombre;
    }

}
