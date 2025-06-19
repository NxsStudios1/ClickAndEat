package org.clickandeat.vista.ventana;

import org.clickandeat.funciones.inicioSesion.UsuarioServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaBienvenida extends JFrame {
    public VentanaBienvenida(UsuarioServicio usuarioServicio) {
        setTitle("Click And Eat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 480);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new FondoAndyBurger();
        panel.setLayout(null);

        ImageIcon logoAndyBurger = new ImageIcon(getClass().getResource("/recursosGraficos/logos/logo_andyBurger.png"));

        // Panel "Salir" arriba derecha
        JPanel salirPanel = new JPanel(null);
        salirPanel.setBackground(new Color(255, 194, 92));
        salirPanel.setBounds(570, 0, 130, 44);
        JLabel lblSalir = new JLabel("Salir");
        lblSalir.setFont(new Font("Arial", Font.BOLD, 20));
        lblSalir.setBounds(40, 2, 80, 36);
        lblSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salirPanel.add(lblSalir);
        panel.add(salirPanel);

        // Acción para salir
        lblSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        // Título "Bienvenido"
        JLabel lblBienvenido = new JLabel("Bienvenido");
        lblBienvenido.setOpaque(true);
        lblBienvenido.setBackground(new Color(255, 194, 92));
        lblBienvenido.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenido.setFont(new Font("Arial", Font.BOLD, 44));
        lblBienvenido.setBounds(170, 44, 350, 60);
        panel.add(lblBienvenido);

        // Logo Andy Burger en el centro
        JLabel logoLabel = new JLabel(new ImageIcon(logoAndyBurger.getImage().getScaledInstance(270, 270, Image.SCALE_SMOOTH)));
        logoLabel.setBounds(215, 120, 270, 220);
        panel.add(logoLabel);

        // Botón "Continuar"
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.setBackground(new Color(255, 194, 92));
        btnContinuar.setFont(new Font("Arial", Font.BOLD, 34));
        btnContinuar.setBounds(200, 360, 300, 56);
        btnContinuar.setFocusPainted(false);
        panel.add(btnContinuar);

        // Acción para continuar
        btnContinuar.addActionListener(e -> {
            new InicioSesionSwing(usuarioServicio, VentanaBienvenida.this).setVisible(true);
            setVisible(false);
        });

        setContentPane(panel);
    }

    // Panel de fondo personalizado
    class FondoAndyBurger extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(new Color(255, 237, 140)); // Fondo amarillo claro
        }
    }
}