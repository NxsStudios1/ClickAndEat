package org.clickandeat.vista.ventana;

import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;

import javax.swing.*;
import java.awt.*;

public class MenuAdministradorSwing extends JFrame {
    public MenuAdministradorSwing(Usuario admin, UsuarioServicio usuarioServicio) {
        setTitle("Menú Administrador");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel lblBienvenida = new JLabel("Bienvenido/a, " + admin.getNombre() + " (ADMIN)");
        JButton btnSalir = new JButton("Salir");

        add(lblBienvenida);

        JButton btnInventario = new JButton("Inventario de Ingredientes");
        add(btnInventario);

        btnInventario.addActionListener(e -> {
            InventarioSwing inventario = new InventarioSwing();
            inventario.setVisible(true);
        });

        JButton btnRespuestas = new JButton("Responder Comentarios");
        add(btnRespuestas);

        btnRespuestas.addActionListener(e -> {
            VentanaRespuestaSwing respuestas = new VentanaRespuestaSwing(admin);
            respuestas.setVisible(true);
        });


        add(btnSalir);

        btnSalir.addActionListener(e -> {
            this.dispose();
            new InicioSesionSwing(usuarioServicio).setVisible(true);
        });
    }
}
