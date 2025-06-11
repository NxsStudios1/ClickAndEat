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
@Table(name = "tbl_comentario")

public class Comentario extends Entidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario", nullable = false)
    private Integer idComentario;

    @Column(name = "asunto", length = 100, nullable = false)
    private String asunto;

    @Column(name = "contenido", length = 2000, nullable = false)
    private String contenido;

    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoriaComentario", nullable = false)
    private CategoriaComentario categoriaComentario = CategoriaComentario.GENERAL;

    public enum CategoriaComentario {
        COMIDA, SERVICIO, AMBIENTE, TIEMPO_ESPERA, GENERAL
    }

    @Column(name = "fechaComentario", nullable = false)
    private LocalDateTime fechaComentario = LocalDateTime.now();

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", nullable = false)
    private Usuario cliente;

    @PrePersist
    @PreUpdate
    private void validarCalificacion (){
        if(calificacion != null && (calificacion < 1 || calificacion >5 )){
            throw new IllegalArgumentException(" *** Calificacion debe de ser entre 1 y 5 ***");
        }
    }







}