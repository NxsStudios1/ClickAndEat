package org.clickandeat.funciones.inicioSesion;

import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.usuario.Usuario;
import org.clickandeat.modelo.entidades.usuario.RolEnum;
import org.clickandeat.modelo.baseDatos.hiberImpl.usuario.UsuarioHiberImpl;
import org.clickandeat.util.ReadUtil;

import java.util.List;

@NoArgsConstructor
public class Login implements org.clickandeat.vista.acciones.Ejecutable
{
    private static Login login;
    private boolean flag;
    private Usuario usuarioLogueado;

    public static Login getInstance()
    {
        if(login == null)
        {
            login = new Login();
        }
        return login;
    }

    @Override
    public void run()
    {
        System.out.println("\n=== INICIAR SESIÓN ===");

        List<Usuario> usuarios = UsuarioHiberImpl.getInstance().findAll();

        System.out.print("> Ingresa tu teléfono: ");
        String telefono = ReadUtil.read();

        System.out.print("> Ingresa tu contraseña: ");
        String contrasena = ReadUtil.read();

        // Buscar usuario por teléfono
        Usuario usuario = usuarios.stream()
                .filter(u -> u.getTelefono().equals(telefono))
                .findFirst()
                .orElse(null);

        if(usuario != null && usuario.getContrasena().equals(contrasena))
        {
            usuarioLogueado = usuario;

            System.out.println("✅ ¡Bienvenido, " + usuario.getNombre() + "!");
            System.out.println("👤 Tipo de cuenta: " +
                    (usuario.getRolEnum() == RolEnum.ADMINISTRADOR ? "Administrador" : "Cliente"));
        }
        else
        {
            System.out.println("❌ Teléfono o contraseña incorrectos.");
        }
    }

    @Override
    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    // Getters para acceso al usuario logueado
    public Usuario getUsuarioLogueado()
    {
        return usuarioLogueado;
    }

    public boolean isLogueado()
    {
        return usuarioLogueado != null;
    }

    public boolean isAdministrador()
    {
        return isLogueado() && usuarioLogueado.isAdministrador();
    }

    public boolean isCliente()
    {
        return isLogueado() && usuarioLogueado.isCliente();
    }
}