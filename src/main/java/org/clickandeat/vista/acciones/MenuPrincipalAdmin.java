package org.clickandeat.vista.acciones;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MenuPrincipalAdmin extends ManejoMenus{

    private static MenuPrincipalAdmin menuPrincipal;

    public static MenuPrincipalAdmin getInstance()
    {
        if(menuPrincipal==null)
        {
            menuPrincipal = new MenuPrincipalAdmin();
        }
        return menuPrincipal;
    }

    public static void resetInstance()
    {
        menuPrincipal = null;
    }

    @Override
    public void despliegaMenu() {
        System.out.println();
    }

    @Override
    public int valorMinMenu() {
        return 0;
    }

    @Override
    public int valorMaxMenu() {
        return 0;
    }

    @Override
    public void manejoOpcion() {

    }
}
