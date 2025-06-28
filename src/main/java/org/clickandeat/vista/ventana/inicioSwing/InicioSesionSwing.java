package org.clickandeat.vista.ventana.inicioSwing;

import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.entidades.sesion.RolEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.vista.ventana.adminSwing.MenuAdministrador;
import org.clickandeat.vista.ventana.clienteSwing.MenuCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InicioSesionSwing extends JFrame {
    private final UsuarioServicio usuarioServicio;
    private final JFrame ventanaBienvenida;

    public InicioSesionSwing (UsuarioServicio usuarioServicio, JFrame ventanaBienvenida) {
        this.usuarioServicio = usuarioServicio;
        this.ventanaBienvenida = ventanaBienvenida;
        setTitle("Inicio Sesión - AndyBurger");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new FondoAndyBurger();
        panel.setLayout(null);

        // Botón "Regresar" arriba derecha
        JPanel regresarPanel = new JPanel(null);
        regresarPanel.setBackground(new Color(255, 191, 73));
        regresarPanel.setBounds(720, 10, 70, 32);
        JLabel lblRegresar = new JLabel("←", SwingConstants.CENTER);
        lblRegresar.setFont(new Font("Arial", Font.BOLD, 20));
        lblRegresar.setBounds(0, 0, 70, 32);
        lblRegresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        regresarPanel.add(lblRegresar);
        panel.add(regresarPanel);

        lblRegresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                InicioSesionSwing.this.dispose();
                if (ventanaBienvenida != null) ventanaBienvenida.setVisible(true);
            }
        });

        // Panel centrar recuadro (hamburguesa + formulario)
        JPanel centerPanel = new JPanel(null);
        centerPanel.setOpaque(false);
        centerPanel.setBounds(130, 50, 560, 370);

        // Imagen hamburguesa
        ImageIcon imgBurger = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/hamburguesa.png"));
        JLabel burgerLabel = new JLabel(new ImageIcon(imgBurger.getImage().getScaledInstance(90, 70, Image.SCALE_SMOOTH)));
        burgerLabel.setBounds(235, 0, 90, 70);
        centerPanel.add(burgerLabel);

        // Título "Inicio Sesión"
        JLabel lblTitulo = new JLabel("Inicio Sesión", SwingConstants.CENTER);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(255, 191, 73));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitulo.setBounds(110, 80, 340, 48);
        centerPanel.add(lblTitulo);

        // Etiqueta "Telefono"
        JLabel lblTelefono = new JLabel("Telefono", SwingConstants.CENTER);
        lblTelefono.setOpaque(true);
        lblTelefono.setBackground(new Color(255, 191, 73));
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 20));
        lblTelefono.setBounds(40, 150, 150, 40);
        centerPanel.add(lblTelefono);

        // Campo telefono
        JTextField txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTelefono.setBounds(210, 150, 300, 40);
        centerPanel.add(txtTelefono);

        // Etiqueta "Contraseña"
        JLabel lblContra = new JLabel("Contraseña", SwingConstants.CENTER);
        lblContra.setOpaque(true);
        lblContra.setBackground(new Color(255, 191, 73));
        lblContra.setFont(new Font("Arial", Font.BOLD, 20));
        lblContra.setBounds(40, 200, 150, 40);
        centerPanel.add(lblContra);

        // Campo contraseña
        JPasswordField txtContra = new JPasswordField();
        txtContra.setFont(new Font("Arial", Font.PLAIN, 16));
        txtContra.setBounds(210, 200, 300, 40);
        centerPanel.add(txtContra);

        // Botón "Iniciar"
        JButton btnIniciar = new JButton("Iniciar");
        btnIniciar.setBackground(new Color(255, 191, 73));
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 24));
        btnIniciar.setBounds(180, 270, 200, 48);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setBorderPainted(false);
        centerPanel.add(btnIniciar);

        panel.add(centerPanel);

        // Etiqueta de registro abajo
        JLabel lblRegistro = new JLabel("¿ No te has Registrado ? Haz Click para Registrarte.", SwingConstants.CENTER);
        lblRegistro.setOpaque(true);
        lblRegistro.setBackground(new Color(255, 191, 73));
        lblRegistro.setFont(new Font("Arial", Font.BOLD, 14));
        lblRegistro.setBounds(190, 430, 440, 28);
        lblRegistro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(lblRegistro);

        // Acción: REGISTRO
        lblRegistro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegistroSwing(InicioSesionSwing.this, usuarioServicio).setVisible(true);
                setVisible(false);
            }
        });

        // Acción: INICIAR SESIÓN
        btnIniciar.addActionListener(e -> {
            String telefono = txtTelefono.getText();
            String contrasena = new String(txtContra.getPassword());
            Usuario usuario = usuarioServicio.iniciarSesion(telefono, contrasena);
            if (usuario != null) {
                if (usuario.getRol() != null && usuario.getRol().getTipo() == RolEnum.CLIENTE) {
                    JOptionPane.showMessageDialog(this, "Bienvenido/a " + usuario.getNombre() + " (Cliente)");
                    this.dispose();
                    new MenuCliente(usuario, usuarioServicio, ventanaBienvenida).setVisible(true);
                } else if (usuario.getRol() != null && usuario.getRol().getTipo() == RolEnum.ADMINISTRADOR) {
                    JOptionPane.showMessageDialog(this, "Bienvenido/a " + usuario.getNombre() + " (Administrador)");
                    this.dispose();
                    new MenuAdministrador(usuario, usuarioServicio,ventanaBienvenida).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Rol no Reconocido .");
                }
            } else {
                JOptionPane.showMessageDialog(this, " ❌ --> Teléfono o Contraseña Incorrectos.");
            }
        });

        setContentPane(panel);
    }

    // Panel de fondo personalizado
    class FondoAndyBurger extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(new Color(255, 239, 155)); // Fondo amarillo claro
        }
    }
}