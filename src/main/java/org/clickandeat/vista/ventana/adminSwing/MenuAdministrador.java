package org.clickandeat.vista.ventana.adminSwing;

import org.clickandeat.funciones.administracion.CategoriaProductoServicio;
import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.funciones.restaurante.PedidoServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.CategoriaProductoDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao.PedidoDao;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.vista.ventana.adminSwing.menuProducto.MenuAdministracion;
import org.clickandeat.vista.ventana.inicioSwing.InicioSesionSwing;

import javax.swing.*;
import java.awt.*;

public class MenuAdministrador extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final Usuario admin;
    private final UsuarioServicio usuarioServicio;
    private final JFrame ventanaAnterior;

    // Servicios para pasar a otras ventanas
    private final CategoriaProductoServicio categoriaServicio;
    private final ProductoServicio productoServicio;
    private final PedidoServicio pedidoServicio;

    public MenuAdministrador(Usuario admin, UsuarioServicio usuarioServicio, JFrame ventanaAnterior) {
        this.admin = admin;
        this.usuarioServicio = usuarioServicio;
        this.ventanaAnterior = ventanaAnterior;

        // Crear los servicios una sola vez
        this.categoriaServicio = new CategoriaProductoServicio(new CategoriaProductoDao());
        this.productoServicio = new ProductoServicio(new ProductoDao());
        this.pedidoServicio = new PedidoServicio(new PedidoDao());

        setTitle("Panel Principal - Andy Burger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // PANEL IZQUIERDO (sidebar)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(255, 162, 130));
        panelIzquierdo.setPreferredSize(new Dimension(270, 830));
        panelIzquierdo.setLayout(new BorderLayout());

        // Panel de arriba (logo + linea)
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setOpaque(false);

        ImageIcon logo = new ImageIcon(getClass().getResource("/recursosGraficos/logos/logo_andyBurger.png"));
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setIcon(new ImageIcon(logo.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH)));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));
        panelArriba.add(lblLogo, BorderLayout.CENTER);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        separator.setPreferredSize(new Dimension(270, 4));
        panelArriba.add(separator, BorderLayout.SOUTH);

        panelIzquierdo.add(panelArriba, BorderLayout.NORTH);

        // Panel de botones menú (GridBagLayout para centrar)
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] opciones = {"Inventario", "Pedidos", "Comentarios", "Administración Menu"};
        JButton btnInventario = null;
        JButton btnComentarios = null;
        JButton btnPedidos = null;
        JButton btnAdministracionMenu = null;
        for (int i=0; i<opciones.length; i++) {
            JButton btn = new JButton(opciones[i]);
            btn.setFont(new Font(FUENTE_BONITA, Font.BOLD, 24));
            btn.setBackground(new Color(255, 162, 130));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLACK));
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            btn.setPreferredSize(new Dimension(240, 48));
            gbc.gridy = i;
            panelBotones.add(btn, gbc);

            switch (opciones[i]) {
                case "Inventario":
                    btnInventario = btn;
                    break;
                case "Pedidos":
                    btnPedidos = btn;
                    break;
                case "Comentarios":
                    btnComentarios = btn;
                    break;
                case "Administración Menu":
                    btnAdministracionMenu = btn;
                    break;
            }
        }
        gbc.weighty = 1;
        gbc.gridy = opciones.length;
        panelBotones.add(Box.createVerticalGlue(), gbc);

        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        // Panel usuario abajo
        JPanel panelUsuario = new JPanel(null);
        panelUsuario.setBackground(new Color(255, 162, 130));
        panelUsuario.setPreferredSize(new Dimension(270, 120));

        ImageIcon iconoUser = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoUser = new JLabel(new ImageIcon(iconoUser.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        lblIconoUser.setBounds(28, 34, 64, 64);
        panelUsuario.add(lblIconoUser);

        JLabel lblAdmin = new JLabel(admin != null ? admin.getNombre() : "Administrador");
        lblAdmin.setFont(new Font(FUENTE_BONITA, Font.BOLD, 13));
        lblAdmin.setBounds(110, 60, 150, 30);
        panelUsuario.add(lblAdmin);

        ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/salir.png"));
        JButton btnSalir = new JButton(new ImageIcon(iconoSalir.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH)));
        btnSalir.setBounds(220, 60, 36, 36);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelUsuario.add(btnSalir);

        panelIzquierdo.add(panelUsuario, BorderLayout.SOUTH);

        // Acción cerrar sesión
        btnSalir.addActionListener(e -> {
            this.dispose();
            if (ventanaAnterior != null) {
                ventanaAnterior.setVisible(true);
            } else {
                new InicioSesionSwing(usuarioServicio, null).setVisible(true);
            }
        });

        // PANEL DERECHO - Fondo amarillo claro
        JPanel panelDerecho = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255,240,172));
                g.fillRect(0,0,getWidth(),getHeight());
            }
        };

        // BARRA SUPERIOR verde claro
        JPanel panelSuperior = new JPanel(null) {
            @Override
            public boolean isOpaque() { return true; }
        };
        panelSuperior.setBackground(new Color(181, 224, 202));
        panelSuperior.setPreferredSize(new Dimension(0, 48));
        JLabel lblPanel = new JLabel("Panel Principal");
        lblPanel.setFont(new Font("Arial", Font.BOLD, 26));
        lblPanel.setForeground(Color.BLACK);
        lblPanel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPanel.setBounds(panelSuperior.getPreferredSize().width - 320, 8, 1200, 36);
        panelSuperior.add(lblPanel);

        panelSuperior.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lblPanel.setBounds(panelSuperior.getWidth() - 320, 8, 300, 36);
            }
        });

        panelDerecho.add(panelSuperior, BorderLayout.NORTH);

        // PANEL CENTRAL (contenido centrado)
        JPanel panelCentral = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255,240,172));
                g.fillRect(0,0,getWidth(),getHeight());
            }
        };

        panelCentral.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0; gc.insets = new Insets(0,0,22,0);

        JLabel lblBienvenido = new JLabel("¡¡¡ Bienvenido !!!", SwingConstants.CENTER);
        lblBienvenido.setFont(new Font(FUENTE_BONITA, Font.BOLD, 66));
        panelCentral.add(lblBienvenido, gc);

        gc.gridy++;
        JLabel lblNombreAdmin = new JLabel("\"" + (admin != null ? admin.getNombre() : "Administrador") + "\"", SwingConstants.CENTER);
        lblNombreAdmin.setFont(new Font(FUENTE_BONITA, Font.BOLD, 66));
        panelCentral.add(lblNombreAdmin, gc);

        gc.gridy++;
        gc.insets = new Insets(36,0,36,0);

        JPanel logoAndyPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 235, 120));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 70, 70);
            }
        };
        logoAndyPanel.setPreferredSize(new Dimension(600, 380));
        logoAndyPanel.setOpaque(false);

        JLabel lblLogoCentral = new JLabel();
        lblLogoCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoCentral.setVerticalAlignment(SwingConstants.CENTER);
        lblLogoCentral.setIcon(new ImageIcon(logo.getImage().getScaledInstance(355, 270, Image.SCALE_SMOOTH)));
        logoAndyPanel.add(lblLogoCentral, BorderLayout.CENTER);

        JSeparator separatorCentral = new JSeparator(SwingConstants.HORIZONTAL);
        separatorCentral.setForeground(new Color(190, 190, 190));
        separatorCentral.setPreferredSize(new Dimension(600, 4));
        logoAndyPanel.add(separatorCentral, BorderLayout.SOUTH);

        panelCentral.add(logoAndyPanel, gc);

        gc.gridy++;
        gc.insets = new Insets(46,0,0,0);
        JLabel lblMotivacion = new JLabel("¡ Hoy es un Gran Dia para Vender !", SwingConstants.CENTER);
        lblMotivacion.setFont(new Font(FUENTE_BONITA, Font.BOLD, 56));
        panelCentral.add(lblMotivacion, gc);

        panelDerecho.add(panelCentral, BorderLayout.CENTER);

        // Añadir ambos al principal
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        // Acción del botón Inventario
        if (btnInventario != null) {
            btnInventario.addActionListener(e -> {
                this.setVisible(false);
                new MenuInventario(admin, usuarioServicio, this).setVisible(true);
            });
        }

        // Acción del botón Pedidos (AQUÍ SE AGREGA LA ACCIÓN)
        if (btnPedidos != null) {
            btnPedidos.addActionListener(e -> {
                this.setVisible(false);
                new MenuPedidosAdministrador(admin, usuarioServicio, this).setVisible(true);
            });
        }

        // Acción del botón Comentarios (administrador)
        if (btnComentarios != null) {
            btnComentarios.addActionListener(e -> {
                this.setVisible(false);
                new MenuComentariosAdministrador(admin, usuarioServicio, this).setVisible(true);
            });
        }

        // Acción del botón Administración Menu
        if (btnAdministracionMenu != null) {
            btnAdministracionMenu.addActionListener(e -> {
                this.setVisible(false);
                // Pasa las referencias necesarias para navegación circular
                new MenuAdministracion(admin, usuarioServicio, this).setVisible(true);

            });
        }
    }
}