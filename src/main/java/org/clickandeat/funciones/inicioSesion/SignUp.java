package org.clickandeat.funciones.inicioSesion;

import lombok.NoArgsConstructor;
import org.clickandeat.modelo.entidades.usuario.Usuario;
import org.clickandeat.modelo.entidades.usuario.RolEnum;
import org.clickandeat.modelo.baseDatos.hiberImpl.usuario.UsuarioHiberImpl;
import org.clickandeat.util.ReadUtil;

import java.util.List;

@NoArgsConstructor
public class SignUp implements org.clickandeat.vista.acciones.Ejecutable
{
    private static SignUp signUp;
    private boolean flag;

    public static SignUp getInstance()
    {
        if(signUp==null)
        {
            signUp = new SignUp();
        }
        return signUp;
    }

    @Override
    public void run()
    {
        List<Usuario> usuarios = UsuarioHiberImpl.getInstance().findAll();
        Usuario usuario = new Usuario();
        boolean flag1 = true, flag2 = true, flag3 = true;

        while(flag1)
        {
            System.out.print("> Ingresa tu nombre completo: ");
            String nombre = ReadUtil.read();

            if(nombre == null || nombre.trim().isEmpty())
            {
                System.out.println("❌ El nombre no puede estar vacío.");
            }
            else
            {
                usuario.setNombre(nombre.trim());
                flag1 = false;
            }
        }

        while(flag2)
        {
            System.out.print("> Ingresa tu número de teléfono: ");
            String telefono = ReadUtil.read();
            boolean telefonoExists = usuarios.stream().anyMatch(u->u.getTelefono().equals(telefono));

            if(telefonoExists)
            {
                System.out.println("❌ El número de teléfono está en uso.");
            }
            else if(telefono == null || telefono.trim().isEmpty())
            {
                System.out.println("❌ El teléfono no puede estar vacío.");
            }
            else if(telefono.length() > 15)
            {
                System.out.println("❌ El teléfono no puede tener más de 15 caracteres.");
            }
            else
            {
                usuario.setTelefono(telefono.trim());
                flag2 = false;
            }
        }

        while(flag3)
        {
            System.out.print("> Elige una contraseña, ¡no la olvides!: ");
            String contrasena = ReadUtil.read();

            if(contrasena == null || contrasena.trim().isEmpty())
            {
                System.out.println("❌ La contraseña no puede estar vacía.");
            }
            else if(contrasena.length() < 6)
            {
                System.out.println("❌ La contraseña debe tener al menos 6 caracteres.");
            }
            else
            {
                usuario.setContrasena(contrasena);
                flag3 = false;
            }
        }

        // Selección de rol
        boolean flag4 = true;
        while(flag4)
        {
            System.out.println("\n> Selecciona tu tipo de cuenta:");
            System.out.println("1. Cliente");
            System.out.println("2. Administrador");
            System.out.print("Ingresa tu opción (1 o 2): ");
            String opcion = ReadUtil.read();

            if(opcion.equals("1"))
            {
                usuario.setRolEnum(RolEnum.CLIENTE);
                System.out.println("✅ Cuenta de Cliente seleccionada.");
                flag4 = false;
            }
            else if(opcion.equals("2"))
            {
                usuario.setRolEnum(RolEnum.ADMINISTRADOR);
                System.out.println("✅ Cuenta de Administrador seleccionada.");
                flag4 = false;
            }
            else
            {
                System.out.println("❌ Opción inválida. Por favor ingresa 1 o 2.");
            }
        }

        boolean guardado = UsuarioHiberImpl.getInstance().guardar(usuario);

        if(guardado)
        {
            System.out.println("✅ Usuario generado con éxito.");
            System.out.println("📱 Tu teléfono será tu identificador para iniciar sesión.");
            System.out.println("👤 Tipo de cuenta: " + (usuario.getRolEnum() == RolEnum.CLIENTE ? "Cliente" : "Administrador"));
        }
        else
        {
            System.out.println("❌ Error al guardar el usuario. Intenta nuevamente.");
        }
    }

    @Override
    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
}