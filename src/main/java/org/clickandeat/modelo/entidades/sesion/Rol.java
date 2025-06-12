package org.clickandeat.modelo.entidades.sesion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_rol")

public class Rol extends Entidad {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, unique = true)
    private RolEnum tipo;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

}

