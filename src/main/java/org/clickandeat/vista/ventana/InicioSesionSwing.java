package org.clickandeat.vista.ventana;

import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.entidades.sesion.RolEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class InicioSesionSwing extends JFrame {
    private UsuarioServicio usuarioServicio;

    public InicioSesionSwing(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;

        setTitle("Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 220);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 6, 6));

        JTextField tfTelefono = new JTextField();
        JPasswordField pfContrasena = new JPasswordField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");

        add(new JLabel("Teléfono:"));
        add(tfTelefono);
        add(new JLabel("Contraseña:"));
        add(pfContrasena);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);
        add(panelBotones);

        btnLogin.addActionListener((ActionEvent e) -> {
            String telefono = tfTelefono.getText();
            String contrasena = new String(pfContrasena.getPassword());
            Usuario usuario = usuarioServicio.iniciarSesion(telefono, contrasena);
            if (usuario != null) {
                // Decide el menú según el rol
                if (usuario.getRol() != null && usuario.getRol().getTipo() == RolEnum.CLIENTE) {
                    JOptionPane.showMessageDialog(this, "Bienvenido/a " + usuario.getNombre() + " (Cliente)");
                    this.dispose();
                    new MenuClienteSwing(usuario, usuarioServicio).setVisible(true);
                } else if (usuario.getRol() != null && usuario.getRol().getTipo() == RolEnum.ADMINISTRADOR) {
                    JOptionPane.showMessageDialog(this, "Bienvenido/a " + usuario.getNombre() + " (Administrador)");
                    this.dispose();
                    new MenuAdministradorSwing(usuario, usuarioServicio).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Rol no reconocido.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Teléfono o contraseña incorrectos.");
            }
        });

        btnRegistro.addActionListener((ActionEvent e) -> {
            new RegistroSwing(this, usuarioServicio).setVisible(true);
            this.setVisible(false);
        });
    }
}