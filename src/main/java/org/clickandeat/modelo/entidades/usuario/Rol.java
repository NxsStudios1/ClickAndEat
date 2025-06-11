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

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    public RolEnum toEnum() {
        return RolEnum.getId(this.idRol);
    }

    public Rol(RolEnum rolEnum) {
        this.idRol = rolEnum.getIdRol();
        this.nombre = rolEnum.name();
    }
}
