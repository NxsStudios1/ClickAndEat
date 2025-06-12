package org.clickandeat.funciones.inicioSesion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao.RolDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao.UsuarioDao;
import org.clickandeat.modelo.entidades.sesion.Rol;
import org.clickandeat.modelo.entidades.sesion.RolEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;

public class UsuarioServicio {
    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final RolDao rolDao = new RolDao();

    // Registro de usuario (de sesión)
    public boolean registrarUsuario(String nombre, String telefono, String contrasena, RolEnum tipoRol) {

        // Verifica si ya existe el usuario por teléfono
        if (usuarioDao.findByTelefono(telefono) != null) {
            return false; // Usuario ya existe
        }
        // Busca el rol, si no existe lo crea
        Rol rol = rolDao.findByTipo(tipoRol);
        if (rol == null) {
            rol = new Rol();
            rol.setTipo(tipoRol);
            rolDao.guardar(rol);
        }

        // Crea y guarda el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setContrasena(contrasena);
        usuario.setRol(rol);

        return usuarioDao.guardar(usuario);
    }

    // Inicio de sesión
    public Usuario iniciarSesion(String telefono, String contrasena) {
        return usuarioDao.findByTelefonoYContrasena(telefono, contrasena);
    }

}
