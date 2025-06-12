package org.clickandeat.vista.ventana;

import org.clickandeat.funciones.realizacionComentario.ComentarioServicio;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.ComentarioDao;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;

import javax.swing.*;
import java.awt.*;

public class MenuClienteSwing extends JFrame {
    public MenuClienteSwing(Usuario cliente, UsuarioServicio usuarioServicio) {
        setTitle("Menú Cliente");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel lblBienvenida = new JLabel("Bienvenido/a, " + cliente.getNombre());
        JButton btnComentar = new JButton("Realizar Comentario");
        JButton btnSalir = new JButton("Salir");

        add(lblBienvenida);
        add(btnComentar);
        add(btnSalir);

        btnComentar.addActionListener(e -> {
            ComentarioDao comentarioDao = new ComentarioDao();
            ComentarioServicio comentarioServicio = new ComentarioServicio(comentarioDao);
            ComentarioSwing ventanaComentario = new ComentarioSwing(cliente, comentarioServicio);
            ventanaComentario.setVisible(true);
        });

        btnSalir.addActionListener(e -> {
            this.dispose();
            new InicioSesionSwing(usuarioServicio).setVisible(true);
        });
    }
}