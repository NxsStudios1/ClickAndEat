package org.clickandeat.modelo.entidades.usuario;

import jakarta.persistence.*;
import lombok.*;

import org.clickandeat.modelo.entidades.base.Entidad;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "TBL_ROL")

public class Rol extends Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRol", nullable = false)
    private Integer idRol;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, unique = true)
    private RolEnum rol;

    public Rol(RolEnum rol) {
        this.rol = rol;
    }

    public RolEnum toEnum() {
        return this.rol;
    }
}
