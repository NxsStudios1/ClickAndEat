package org.clickandeat.vista.acciones;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MenuPrincipalClient extends ManejoMenus{

    private static MenuPrincipalClient menuPrincipalClient;

    public static MenuPrincipalClient getInstance(){
        if(menuPrincipalClient == null){
            menuPrincipalClient = new MenuPrincipalClient();
        }
        return menuPrincipalClient;
    }



    @Override
    public void despliegaMenu() {
        System.out.println("\n\t-> Bienvenido Cliente <-");
        System.out.println("¿Qué vamos a hacer hoy?");
        System.out.println("1. Ver Menu");
        System.out.println("2. Hacer Comentarios");
        System.out.println("3. Salir");
        System.out.print(" -> Ingresa tu opción: ");
    }

    @Override
    public int valorMinMenu() {
        return 1;
    }

    @Override
    public int valorMaxMenu() {
        return 3;
    }

    @Override
    public void manejoOpcion() {
        Ejecutable ejecutable = null;
        switch (opcion){
            case 2:

        }
    }
}
