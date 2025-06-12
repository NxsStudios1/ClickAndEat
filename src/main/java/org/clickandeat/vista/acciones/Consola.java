/*package org.clickandeat.vista.acciones;


import lombok.NoArgsConstructor;
import org.clickandeat.funciones.inicioSesion.Login;
import org.clickandeat.funciones.inicioSesion.SignUp;

@NoArgsConstructor
public class Consola extends ManejoMenus
{
    private static Consola consola;

    public static Consola getInstance()
    {
        if(consola==null)
        {
            consola = new Consola();
        }
        return consola;
    }

    @Override
    public void despliegaMenu()
    {
        System.out.println("\n\t==============================");
        System.out.println("\t Click And Eat: Inicio Sesion ");
        System.out.println("\t==============================");
        System.out.println("\t 1️⃣  - Registro");
        System.out.println("\t 2️⃣  - Iniciar sesión");
        System.out.println("\t 3️⃣  - Regresar");
        System.out.print("\n\t👉 Ingresa tu opción: ");
    }

    @Override
    public int valorMinMenu()
    {
        return 1;
    }

    @Override
    public int valorMaxMenu()
    {
        return 3;
    }

   @Override
    public void manejoOpcion() {
       Ejecutable ejecutable = null;
       switch (opcion) {
           case 1:
               ejecutable = SignUp.getInstance();
               break;
           case 2:
               ejecutable = Login.getInstance();
               break;
           default:
               break;
       }
       if (ejecutable != null) {
           ejecutable.setFlag(true);
           ejecutable.run();
       }
   }
}
*/
