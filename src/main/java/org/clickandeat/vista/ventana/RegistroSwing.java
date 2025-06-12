package org.clickandeat.vista.ventana;

import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.entidades.sesion.RolEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistroSwing extends JFrame {
    public RegistroSwing(JFrame loginFrame, UsuarioServicio usuarioServicio) {
        setTitle("Registro de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(250, 230);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JTextField tfNombre = new JTextField();
        JTextField tfTelefono = new JTextField();
        JPasswordField pfContrasena = new JPasswordField();

        // ComboBox para elegir el rol
        JComboBox<RolEnum> cbRol = new JComboBox<>(RolEnum.values());

        JButton btnRegistrar = new JButton("Registrar");

        add(new JLabel("Nombre:"));
        add(tfNombre);
        add(new JLabel("Teléfono:"));
        add(tfTelefono);
        add(new JLabel("Contraseña:"));
        add(pfContrasena);
        add(new JLabel("Tipo de cuenta:"));
        add(cbRol);

        add(new JLabel()); // Espacio vacío
        add(btnRegistrar);

        btnRegistrar.addActionListener((ActionEvent e) -> {
            String nombre = tfNombre.getText();
            String telefono = tfTelefono.getText();
            String contrasena = new String(pfContrasena.getPassword());
            RolEnum tipoRol = (RolEnum) cbRol.getSelectedItem();

            boolean ok = usuarioServicio.registrarUsuario(nombre, telefono, contrasena, tipoRol);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                loginFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar usuario.");
            }
        });
    }
}