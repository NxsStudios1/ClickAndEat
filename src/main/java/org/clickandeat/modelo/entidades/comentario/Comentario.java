package org.clickandeat.modelo.entidades.comentario;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_comentario")
public class Comentario extends Entidad {

    @Column(nullable = false, length = 100)
    private String asunto;

    @Column(nullable = false, length = 2000)
    private String contenido;

    @Column(nullable = false)
    private Integer calificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaComentarioEnum categoria;

    @Column(name = "fechaComentario")
    private LocalDateTime fechaComentario;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Usuario cliente;

    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL)
    private List<RespuestaComentario> respuestas;

    @Override
    public String toString() {
        return  "📝 Asunto: " + asunto +
                "\n⭐ Calificación: " + calificacion +
                "\n🏷️ Categoría: " + categoria +
                "\n📅 Fecha: " + (fechaComentario != null ? fechaComentario.toString().replace('T', ' ') : "Sin fecha") +
                "\n👤 Cliente: " + cliente.getNombre() +
                "\n";
    }
}