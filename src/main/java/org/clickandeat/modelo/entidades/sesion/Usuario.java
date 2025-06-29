package org.clickandeat.modelo.entidades.sesion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.comentario.Comentario;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;
import org.clickandeat.modelo.entidades.pedido.Pedido;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_usuario")
public class Usuario extends Entidad {

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(name = "contrasena" , nullable = false)
    private String contrasena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "cliente")
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "administrador")
    private List<RespuestaComentario> respuestas;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;

    @Override
    public String toString() {
        return nombre + " (" + telefono + ")";
    }

}