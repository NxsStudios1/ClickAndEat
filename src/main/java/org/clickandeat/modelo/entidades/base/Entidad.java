package org.clickandeat.modelo.entidades.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.clickandeat.modelo.baseDatos.hiberImpl.UsuarioHiberImpl;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public abstract class Entidad
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

}

