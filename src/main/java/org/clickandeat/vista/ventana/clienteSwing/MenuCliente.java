package org.clickandeat.vista.ventana.clienteSwing;

import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.funciones.administracion.PromocionServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.funciones.restaurante.PedidoServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao.PedidoDao;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.vista.ventana.inicioSwing.InicioSesionSwing;

import javax.swing.*;
import java.awt.*;

public class MenuCliente extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final Usuario cliente;
    private final UsuarioServicio usuarioServicio;
    private final JFrame ventanaBienvenida;

    // Servicios instanciados aquí:
    private final ProductoServicio productoServicio = new ProductoServicio(new ProductoDao());
    private final PromocionServicio promocionServicio = new PromocionServicio(new PromocionDao());
    private final PedidoServicio pedidoServicio = new PedidoServicio(new PedidoDao());

    public MenuCliente(Usuario cliente, UsuarioServicio usuarioServicio, JFrame ventanaBienvenida) {
        this.cliente = cliente;
        this.usuarioServicio = usuarioServicio;
        this.ventanaBienvenida = ventanaBienvenida;

        setTitle("Panel Principal - Andy Burger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // PANEL IZQUIERDO (sidebar)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(186, 240, 215)); // Verde pastel
        panelIzquierdo.setPreferredSize(new Dimension(320, 830));
        panelIzquierdo.setLayout(new BorderLayout());

        // Panel de arriba (logo más grande)
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setOpaque(false);

        ImageIcon logo = new ImageIcon(getClass().getResource("/recursosGraficos/logos/logo_andyBurger.png"));
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setIcon(new ImageIcon(logo.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panelArriba.add(lblLogo, BorderLayout.CENTER);

        // Línea separadora bajo logo
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        separator.setPreferredSize(new Dimension(320, 3));
        panelArriba.add(separator, BorderLayout.SOUTH);

        panelIzquierdo.add(panelArriba, BorderLayout.NORTH);

        // Panel de botones menú
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 0));
        panelBotones.setOpaque(false);

        JButton btnMenu = new JButton("Menu");
        btnMenu.setFont(new Font(FUENTE_BONITA, Font.BOLD, 38));
        btnMenu.setBackground(new Color(186, 240, 215));
        btnMenu.setFocusPainted(false);
        btnMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        btnMenu.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnComentarios = new JButton("Comentarios");
        btnComentarios.setFont(new Font(FUENTE_BONITA, Font.BOLD, 38));
        btnComentarios.setBackground(new Color(186, 240, 215));
        btnComentarios.setFocusPainted(false);
        btnComentarios.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        btnComentarios.setHorizontalAlignment(SwingConstants.CENTER);

        panelBotones.add(btnMenu);
        panelBotones.add(btnComentarios);

        // Acción para abrir MenuRestaurante
        btnMenu.addActionListener(e -> {
            MenuRestaurante menuRestaurante = new MenuRestaurante(
                    cliente,
                    productoServicio,
                    promocionServicio,
                    pedidoServicio,
                    ventanaBienvenida
            );
            menuRestaurante.setVisible(true);
            this.dispose();
        });

        // Acción para abrir MenuComentariosClienteSwing
        btnComentarios.addActionListener(e -> {
            MenuComentariosCliente ventanaComentarios = new MenuComentariosCliente(
                    cliente, usuarioServicio, ventanaBienvenida
            );
            ventanaComentarios.setVisible(true);
            this.dispose();
        });

        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        // Línea inferior antes del usuario
        JSeparator separatorInf = new JSeparator(SwingConstants.HORIZONTAL);
        separatorInf.setForeground(Color.BLACK);
        separatorInf.setPreferredSize(new Dimension(320, 3));
        panelIzquierdo.add(separatorInf, BorderLayout.SOUTH);

        // Panel usuario abajo
        JPanel panelUsuario = new JPanel(null);
        panelUsuario.setBackground(new Color(186, 240, 215));
        panelUsuario.setPreferredSize(new Dimension(320, 100));

        ImageIcon iconoUser = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoUser = new JLabel(new ImageIcon(iconoUser.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        lblIconoUser.setBounds(24, 18, 64, 64);
        panelUsuario.add(lblIconoUser);

        JLabel lblCliente = new JLabel(cliente != null ? cliente.getNombre() : "Cliente");
        lblCliente.setFont(new Font(FUENTE_BONITA, Font.BOLD, 22));
        lblCliente.setBounds(110, 38, 120, 32);
        panelUsuario.add(lblCliente);

        ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/salir.png"));
        JButton btnSalir = new JButton(new ImageIcon(iconoSalir.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH)));
        btnSalir.setBounds(250, 38, 38, 38);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelUsuario.add(btnSalir);

        btnSalir.addActionListener(e -> {
            this.dispose();
            if (ventanaBienvenida != null) {
                ventanaBienvenida.setVisible(true);
            } else {
                new InicioSesionSwing(usuarioServicio, null).setVisible(true);
            }
        });

        JPanel panelUsuarioCont = new JPanel(new BorderLayout());
        panelUsuarioCont.setOpaque(false);
        panelUsuarioCont.add(panelUsuario, BorderLayout.CENTER);
        panelIzquierdo.add(panelUsuarioCont, BorderLayout.SOUTH);

        // PANEL DERECHO
        JPanel panelDerecho = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(246, 227, 203));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JPanel panelSuperior = new JPanel(null) {
            @Override
            public boolean isOpaque() { return true; }
        };
        panelSuperior.setBackground(new Color(190, 235, 255));
        panelSuperior.setPreferredSize(new Dimension(0, 48));

        JLabel lblPanel = new JLabel("Panel Principal");
        lblPanel.setFont(new Font(FUENTE_BONITA, Font.BOLD, 28));
        lblPanel.setForeground(Color.BLACK);
        lblPanel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPanel.setBounds(panelSuperior.getPreferredSize().width - 310, 8, 300, 36);
        panelSuperior.add(lblPanel);

        panelSuperior.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lblPanel.setBounds(panelSuperior.getWidth() - 310, 8, 300, 36);
            }
        });

        panelDerecho.add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(246, 227, 203));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.insets = new Insets(0, 0, 22, 0);

        JLabel lblBienvenido = new JLabel("¡¡¡ Bienvenido !!!", SwingConstants.CENTER);
        lblBienvenido.setFont(new Font(FUENTE_BONITA, Font.BOLD, 66));
        panelCentral.add(lblBienvenido, gc);

        gc.gridy++;
        JLabel lblNombre = new JLabel("\"" + (cliente != null ? cliente.getNombre() : "Cliente") + "\"", SwingConstants.CENTER);
        lblNombre.setFont(new Font(FUENTE_BONITA, Font.BOLD, 66));
        panelCentral.add(lblNombre, gc);

        gc.gridy++;
        gc.insets = new Insets(46, 0, 46, 0);

        JPanel logoAndyPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(246, 227, 203));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 90, 90);
            }
        };
        logoAndyPanel.setPreferredSize(new Dimension(600, 400));
        logoAndyPanel.setOpaque(false);

        JLabel lblLogoCentral = new JLabel();
        lblLogoCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoCentral.setVerticalAlignment(SwingConstants.CENTER);
        lblLogoCentral.setIcon(new ImageIcon(logo.getImage().getScaledInstance(355, 270, Image.SCALE_SMOOTH)));
        logoAndyPanel.add(lblLogoCentral, BorderLayout.CENTER);

        panelCentral.add(logoAndyPanel, gc);

        gc.gridy++;
        gc.insets = new Insets(56, 0, 0, 0);
        JLabel lblMotivacion = new JLabel("¡Hoy es un gran día para consentir tu antojo!", SwingConstants.CENTER);
        lblMotivacion.setFont(new Font(FUENTE_BONITA, Font.BOLD, 44));
        panelCentral.add(lblMotivacion, gc);

        panelDerecho.add(panelCentral, BorderLayout.CENTER);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }
}