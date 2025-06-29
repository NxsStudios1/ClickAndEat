package org.clickandeat.inicio;

import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao.RolDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.sesionDao.UsuarioDao;
import org.clickandeat.vista.ventana.inicioSwing.VentanaBienvenida;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(" \uD83C\uDF54 -- ¡ Iniciando Click And Eat ! -- \uD83C\uDF54");
        RolDao rolDao = new RolDao();
        UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioServicio usuarioServicio = new UsuarioServicio(rolDao, usuarioDao);

        SwingUtilities.invokeLater(() -> {
            new VentanaBienvenida(usuarioServicio).setVisible(true);
        });

    }
}