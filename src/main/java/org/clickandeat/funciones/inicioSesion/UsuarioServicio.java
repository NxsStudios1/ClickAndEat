package org.clickandeat.funciones.inicioSesion;

import org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao.RolDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao.UsuarioDao;
import org.clickandeat.modelo.entidades.sesion.Rol;
import org.clickandeat.modelo.entidades.sesion.RolEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;

public class UsuarioServicio {
    private final UsuarioDao usuarioDao;
    private final RolDao rolDao;

    public UsuarioServicio(RolDao rolDao, UsuarioDao usuarioDao) {
        this.rolDao = rolDao;
        this.usuarioDao = usuarioDao;
    }

    public boolean registrarUsuario(String nombre, String telefono, String contrasena, RolEnum tipoRol) {
        if (usuarioDao.findByTelefono(telefono) != null) {
            return false; // Usuario ya existe
        }
        Rol rol = rolDao.findByTipo(tipoRol);
        if (rol == null) {
            rol = new Rol();
            rol.setTipo(tipoRol);
            rolDao.guardar(rol);
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setTelefono(telefono);
        usuario.setContrasena(contrasena);
        usuario.setRol(rol);

        return usuarioDao.guardar(usuario);
    }

    public Usuario iniciarSesion(String telefono, String contrasena) {
        Usuario usuario = usuarioDao.findByTelefono(telefono);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            return usuario;
        }
        return null;
    }
}