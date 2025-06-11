package org.clickandeat.modelo.entidades.comentario;

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
@Table(name = "respuestaComentario")
public class RespuestaComentario extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRespuestaComentario", nullable = false)
    private Integer idRespuestaComentario;

    @Column(name = "contenido", length = 2000, nullable = false)
    private String contenido;

    @Column(name = "fechaRespuesta", nullable = false)
    private LocalDateTime fechaRespuesta = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idComentario", nullable = false)
    private Comentario comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idAdministrador", nullable = false)
    private Usuario administrador;

    // Método para validar que el usuario sea administrador
    @PrePersist
    @PreUpdate
    private void validarAdministrador() {
        if (administrador != null && !administrador.isAdministrador()) {
            throw new IllegalArgumentException(" *** Solo los administradores pueden responder comentarios *** ");
        }
    }
}