/*package org.clickandeat.vista.acciones;

import org.clickandeat.util.ReadUtil;

public abstract class ManejoMenus implements Ejecutable
{
    protected Integer opcion;
    protected boolean flag;

    public ManejoMenus()
    {
        flag = true;
    }

    public abstract void despliegaMenu();
    public abstract int valorMinMenu();
    public abstract int valorMaxMenu();
    public abstract void manejoOpcion();

    @Override
    public void run()
    {
        while(flag)
        {
            despliegaMenu();
            opcion = ReadUtil.readInt();
            if(opcion>=valorMinMenu()&&opcion<=valorMaxMenu())
            {
                if(opcion == valorMaxMenu())
                {
                    flag = false;
                }
                else
                {
                    manejoOpcion();
                }
            }
            else
            {
                System.out.println("\n\t❌ ¡ERROR! La opción ingresada no es válida. Intenta nuevamente.\n");
            }
        }
    }

    @Override
    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }
}
*/