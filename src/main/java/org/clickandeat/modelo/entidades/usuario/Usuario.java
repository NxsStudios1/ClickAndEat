package org.clickandeat.modelo.entidades.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "tbl_usuario")

public class Usuario extends Entidad {

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "telefono", length = 15, nullable = false)
    private String telefono;

    @Column(name = "contrasena", length = 255, nullable = false)
    private String contrasena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    public RolEnum getRolEnum() {
        return rol != null ? rol.toEnum() : null;
    }

    public void setRolEnum(RolEnum rolEnum) {
        if (rolEnum != null) {
            this.rol = new Rol(rolEnum);
        }
    }

    public boolean isAdministrador() {
        return getRolEnum() == RolEnum.ADMINISTRADOR;
    }

    public boolean isCliente() {
        return getRolEnum() == RolEnum.CLIENTE;
    }
}

