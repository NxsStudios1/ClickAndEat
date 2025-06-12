package org.clickandeat.vista.ventana;

import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.entidades.sesion.RolEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioSesionSwing extends JFrame {

    private JTextField tfNombre;
    private JTextField tfTelefono;
    private JPasswordField pfContrasena;
    private JComboBox<RolEnum> cbRol;
    private JButton btnRegistrar;
    private JButton btnLogin;
    private UsuarioServicio usuarioServicio;

    public InicioSesionSwing() {
        usuarioServicio = new UsuarioServicio();
        setTitle("Login y Registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 320);
        setLocationRelativeTo(null); // Centra la ventana

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblTelefono = new JLabel("Teléfono:");
        JLabel lblContrasena = new JLabel("Contraseña:");
        JLabel lblRol = new JLabel("Rol:");

        tfNombre = new JTextField(15);
        tfTelefono = new JTextField(15);
        pfContrasena = new JPasswordField(15);

        cbRol = new JComboBox<>(RolEnum.values());
        cbRol.setSelectedItem(RolEnum.CLIENTE);

        btnRegistrar = new JButton("Registrar");
        btnLogin = new JButton("Iniciar Sesión");

        // Layout
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(lblNombre, gbc);
        gbc.gridx = 1;
        panel.add(tfNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblTelefono, gbc);
        gbc.gridx = 1;
        panel.add(tfTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblContrasena, gbc);
        gbc.gridx = 1;
        panel.add(pfContrasena, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblRol, gbc);
        gbc.gridx = 1;
        panel.add(cbRol, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(btnRegistrar, gbc);
        gbc.gridx = 1;
        panel.add(btnLogin, gbc);

        add(panel);

        // Acciones de botones
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
    }

    private void registrarUsuario() {
        String nombre = tfNombre.getText().trim();
        String telefono = tfTelefono.getText().trim();
        String contrasena = String.valueOf(pfContrasena.getPassword()).trim();
        RolEnum rol = (RolEnum) cbRol.getSelectedItem();

        if (nombre.isEmpty() || telefono.isEmpty() || contrasena.isEmpty() || rol == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = usuarioServicio.registrarUsuario(nombre, telefono, contrasena, rol);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puedes iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "El teléfono ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarSesion() {
        String telefono = tfTelefono.getText().trim();
        String contrasena = String.valueOf(pfContrasena.getPassword()).trim();

        if (telefono.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa teléfono y contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario usuario = usuarioServicio.iniciarSesion(telefono, contrasena);
        if (usuario != null) {
            RolEnum tipoRol = usuario.getRol().getTipo();
            if (tipoRol == RolEnum.ADMINISTRADOR) {
                JOptionPane.showMessageDialog(this, "Bienvenido Administrador: " + usuario.getNombre(), "Login", JOptionPane.INFORMATION_MESSAGE);
                // Aquí puedes abrir el menú de administrador
            } else if (tipoRol == RolEnum.CLIENTE) {
                JOptionPane.showMessageDialog(this, "Bienvenido Cliente: " + usuario.getNombre(), "Login", JOptionPane.INFORMATION_MESSAGE);
                // Aquí puedes abrir el menú de cliente
            }
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        tfNombre.setText("");
        tfTelefono.setText("");
        pfContrasena.setText("");
        cbRol.setSelectedItem(RolEnum.CLIENTE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InicioSesionSwing().setVisible(true);
        });
    }
}