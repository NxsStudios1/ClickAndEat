/*package org.clickandeat.vista.acciones;

import lombok.NoArgsConstructor;
import org.clickandeat.vista.ventana.VentanaPrincipal;

@NoArgsConstructor
public class SeleccionEjecutable extends ManejoMenus
{
    private static SeleccionEjecutable seleccionEjecutable;

    public static SeleccionEjecutable getInstance()
    {
        if(seleccionEjecutable==null)
        {
            seleccionEjecutable = new SeleccionEjecutable();
        }
        return seleccionEjecutable;
    }

    @Override
    public void despliegaMenu()
    {
        System.out.println("\n\t🍽️===========================================🍽️");
        System.out.println("\t         ¡Bienvenido a 🍔 Click and Eat!");
        System.out.println("\t🍽️===========================================🍽️");
        System.out.println("\t¿Cómo deseas ingresar?");
        System.out.println("\t 1️⃣  - Consola");
        System.out.println("\t 2️⃣  - Ventana");
        System.out.println("\t 3️⃣  - Salir");
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
    public void manejoOpcion()
    {
        Ejecutable ejecutable = null;
        switch(opcion)
        {
            case 1:
                ejecutable = Consola.getInstance();
                break;
            case 2:
                ejecutable = VentanaPrincipal.getInstance();
                break;
            default:
                break;
        }
        if(ejecutable!=null)
        {
            ejecutable.setFlag(true);
            ejecutable.run();
        }
    }
}*/