package org.clickandeat.vista.ventana.inicioSwing;

import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.entidades.sesion.RolEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Registro  extends JFrame {

    public Registro(JFrame ventanaLogin, UsuarioServicio usuarioServicio) {
        setTitle("Registro Usuario - AndyBurger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new FondoAndyBurger();
        panel.setLayout(null);

        // Botón "Regresar"

        RoundedPanel regresarPanel = new RoundedPanel(30);
        regresarPanel.setBackground(new Color(255, 191, 73));
        regresarPanel.setBounds(895, 15, 80, 48);
        JLabel lblRegresar = new JLabel("<-", SwingConstants.CENTER);
        lblRegresar.setFont(new Font("Arial", Font.BOLD, 28));
        lblRegresar.setBounds(0, 0, 80, 48);
        lblRegresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        regresarPanel.setLayout(null);
        regresarPanel.add(lblRegresar);
        panel.add(regresarPanel);

        // Acción: regresar a login

        lblRegresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Registro.this.dispose();
                if (ventanaLogin != null) ventanaLogin.setVisible(true);
            }
        });

        // Título "Registro"

        JLabel lblTitulo = new JLabel("Registro - AndyBurger", SwingConstants.CENTER);
        lblTitulo.setOpaque(true);
        lblTitulo.setBackground(new Color(255, 191, 73));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 56));
        lblTitulo.setBounds(230, 60, 540, 80);
        panel.add(lblTitulo);

        // Fondo del formulario

        RoundedPanel formBg = new RoundedPanel(40);
        formBg.setBackground(new Color(255, 219, 119));
        formBg.setBounds(180, 175, 640, 270);
        formBg.setLayout(null);
        panel.add(formBg);

        // Coordenadas y tamaño comunes
        int labelWidth = 180;
        int fieldWidth = 320;
        int height = 42;
        int leftMargin = 40;
        int fieldMargin = 35;
        int yStart = 25;
        int yGap = 56;

        // Etiqueta y campo "Nombre"

        JLabel lblNombre = new JLabel("Nombre", SwingConstants.CENTER);
        lblNombre.setOpaque(true);
        lblNombre.setBackground(new Color(255, 191, 73));
        lblNombre.setFont(new Font("Arial", Font.BOLD, 20));
        lblNombre.setBounds(leftMargin, yStart, labelWidth, height);
        formBg.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 18));
        txtNombre.setBounds(leftMargin + labelWidth + fieldMargin, yStart, fieldWidth, height);
        formBg.add(txtNombre);

        // Etiqueta y campo "Telefono"

        JLabel lblTelefono = new JLabel("Telefono", SwingConstants.CENTER);
        lblTelefono.setOpaque(true);
        lblTelefono.setBackground(new Color(255, 191, 73));
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 20));
        lblTelefono.setBounds(leftMargin, yStart + yGap, labelWidth, height);
        formBg.add(lblTelefono);

        JTextField txtTelefono = new JTextField();
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 18));
        txtTelefono.setBounds(leftMargin + labelWidth + fieldMargin, yStart + yGap, fieldWidth, height);
        formBg.add(txtTelefono);

        // Etiqueta y campo "Contraseña"

        JLabel lblContra = new JLabel("Contraseña", SwingConstants.CENTER);
        lblContra.setOpaque(true);
        lblContra.setBackground(new Color(255, 191, 73));
        lblContra.setFont(new Font("Arial", Font.BOLD, 20));
        lblContra.setBounds(leftMargin, yStart + yGap*2, labelWidth, height);
        formBg.add(lblContra);

        JPasswordField txtContra = new JPasswordField();
        txtContra.setFont(new Font("Arial", Font.PLAIN, 18));
        txtContra.setBounds(leftMargin + labelWidth + fieldMargin, yStart + yGap*2, fieldWidth, height);
        formBg.add(txtContra);

        // Etiqueta y combo "Tipo de Cuenta"

        JLabel lblTipoCuenta = new JLabel("Tipo de Cuenta", SwingConstants.CENTER);
        lblTipoCuenta.setOpaque(true);
        lblTipoCuenta.setBackground(new Color(255, 191, 73));
        lblTipoCuenta.setFont(new Font("Arial", Font.BOLD, 20));
        lblTipoCuenta.setBounds(leftMargin, yStart + yGap*3, labelWidth, height);
        formBg.add(lblTipoCuenta);

        JComboBox<RolEnum> comboTipo = new JComboBox<>(RolEnum.values());
        comboTipo.setFont(new Font("Arial", Font.PLAIN, 18));
        comboTipo.setBounds(leftMargin + labelWidth + fieldMargin, yStart + yGap*3, fieldWidth, height);
        formBg.add(comboTipo);

        // Botón "Registrar"

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(new Color(255, 191, 73));
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 34));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBounds(370, 490, 260, 54);
        btnRegistrar.setBorderPainted(false);
        panel.add(btnRegistrar);

        // Registrar usuario y con Validación

        btnRegistrar.addActionListener(evt -> {
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String contrasena = new String(txtContra.getPassword());
            RolEnum tipoRol = (RolEnum) comboTipo.getSelectedItem();

            if (nombre.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }

            if (telefono.length() < 8) {
                JOptionPane.showMessageDialog(this, "El teléfono debe tener al menos 8 caracteres.");
                return;
            }
            if (contrasena.length() < 8) {
                JOptionPane.showMessageDialog(this, "La contraseña debe tener al menos 8 caracteres.");
                return;
            }

            boolean ok = usuarioServicio.registrarUsuario(nombre, telefono, contrasena, tipoRol);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
                if (ventanaLogin != null) ventanaLogin.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error: teléfono ya registrado.");
            }
        });

        setContentPane(panel);
    }

    // Panel de fondo personalizado

    public class FondoAndyBurger extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(new Color(255, 239, 155));
        }
    }

    // Panel con esquinas redondeadas

    public class RoundedPanel extends JPanel {
        private int cornerRadius;
        public RoundedPanel(int radius) {
            super();
            setOpaque(false);
            this.cornerRadius = radius;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo

            graphics.setColor(getBackground());
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
        }
    }
}