package org.clickandeat.modelo.sql.hibernateimpl.usuario;

import org.clickandeat.modelo.entidades.usuario.RolEnum;
import org.clickandeat.modelo.entidades.usuario.Usuario;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioHiberImplTest {

    @Test
    void findAll() {
            List<Usuario> list = UsuarioHiberImpl.getInstance().findAll();
            assertNotNull(list);
            list.forEach(System.out::println);

    }

    @Test
    void save() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Miguel");
        usuario.setRolEnum(RolEnum.ADMINISTRADOR);
        usuario.setId(1);
        usuario.setTelefono("4454546741");
        usuario.setContrasena("hola");

        assertTrue(UsuarioHiberImpl.getInstance().save(usuario));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void findById() {
    }
}