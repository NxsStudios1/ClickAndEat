package org.clickandeat.modelo.entidades.comentario;

import jakarta.persistence.*;
import lombok.*;
import org.clickandeat.modelo.entidades.base.Entidad;
import org.clickandeat.modelo.entidades.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = ("respuestas"))
@Table(name = "tbl_comentario")

public class Comentario extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario", nullable = false)
    private Integer idComentario;

    @Column(name = "asunto", length = 100)
    private String asunto;

    @Column(name = "contenido", length = 2000, nullable = false)
    private String contenido;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoriaComentario")
    private CategoriaComentarioEnum categoriaComentario = CategoriaComentarioEnum.GENERAL;

    @Column(name = "fechaComentario")
    private LocalDateTime fechaComentario = LocalDateTime.now();

    @Column(name = "activo")
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente")
    private Usuario cliente;

    @OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RespuestaComentario> respuestas;

    @PrePersist
    protected void onCreate() {
        if (fechaComentario == null) {
            fechaComentario = LocalDateTime.now();
        }
        if (activo == null) {
            activo = true;
        }
        if (categoriaComentario == null) {
            categoriaComentario = CategoriaComentarioEnum.GENERAL;
        }
    }







}