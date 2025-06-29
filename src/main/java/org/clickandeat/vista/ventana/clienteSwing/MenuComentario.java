package org.clickandeat.vista.ventana.clienteSwing;

import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.funciones.administracion.PromocionServicio;
import org.clickandeat.funciones.cliente.ComentarioServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.funciones.restaurante.PedidoServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.ComentarioDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao.PedidoDao;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuComentario extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final JPanel panelComentarios;
    private final ComentarioServicio comentarioServicio;
    private final Usuario cliente;
    private final UsuarioServicio usuarioServicio;
    private final JFrame ventanaBienvenida;

    public MenuComentario(Usuario cliente, UsuarioServicio usuarioServicio, JFrame ventanaBienvenida) {
        this.cliente = cliente;
        this.usuarioServicio = usuarioServicio;
        this.ventanaBienvenida = ventanaBienvenida;
        this.comentarioServicio = new ComentarioServicio(new ComentarioDao());

        setTitle("Comentarios AndyBurger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel izuqierdo (sidebar)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(186, 240, 215));
        panelIzquierdo.setPreferredSize(new Dimension(320, 830));
        panelIzquierdo.setLayout(new BorderLayout());

        // Panel de arriba
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

        btnMenu.addActionListener(e -> {
            ProductoServicio productoServicio = new ProductoServicio(new ProductoDao());
            PromocionServicio promocionServicio = new PromocionServicio(new PromocionDao());
            PedidoServicio pedidoServicio = new PedidoServicio(new PedidoDao());

            MenuRestaurante menuRestaurante = new MenuRestaurante(
                    cliente,
                    productoServicio,
                    promocionServicio,
                    pedidoServicio,
                    ventanaBienvenida,
                    usuarioServicio
            );
            menuRestaurante.setVisible(true);
            this.dispose();
        });

        JButton btnComentarios = new JButton("Comentarios");
        btnComentarios.setFont(new Font(FUENTE_BONITA, Font.BOLD, 38));
        btnComentarios.setBackground(new Color(186, 240, 215));
        btnComentarios.setFocusPainted(false);
        btnComentarios.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        btnComentarios.setHorizontalAlignment(SwingConstants.CENTER);
        btnComentarios.setEnabled(false);

        panelBotones.add(btnMenu);
        panelBotones.add(btnComentarios);

        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        // Línea inferior
        JSeparator separatorInf = new JSeparator(SwingConstants.HORIZONTAL);
        separatorInf.setForeground(Color.BLACK);
        separatorInf.setPreferredSize(new Dimension(320, 3));
        panelIzquierdo.add(separatorInf, BorderLayout.SOUTH);

        // Panel usuario
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
        JLabel lblSalir = new JLabel(new ImageIcon(iconoSalir.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH)));
        lblSalir.setBounds(250, 38, 38, 38);
        panelUsuario.add(lblSalir);

        JPanel panelUsuarioCont = new JPanel(new BorderLayout());
        panelUsuarioCont.setOpaque(false);
        panelUsuarioCont.add(panelUsuario, BorderLayout.CENTER);
        panelIzquierdo.add(panelUsuarioCont, BorderLayout.SOUTH);

        // Panel Derecho
        JPanel panelDerecho = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(246, 227, 203));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Barra superior
        JPanel panelSuperior = new JPanel(null) {
            @Override
            public boolean isOpaque() { return true; }
        };
        panelSuperior.setBackground(new Color(190, 235, 255));
        panelSuperior.setPreferredSize(new Dimension(0, 48));

        JLabel lblPanel = new JLabel("Comentarios AndyBurger");
        lblPanel.setFont(new Font(FUENTE_BONITA, Font.BOLD, 28));
        lblPanel.setForeground(Color.BLACK);
        lblPanel.setBounds(panelSuperior.getPreferredSize().width - 360, 8, 360, 32);
        panelSuperior.add(lblPanel);

        panelSuperior.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lblPanel.setBounds(panelSuperior.getWidth() - 360, 8, 360, 32);
            }
        });

        panelDerecho.add(panelSuperior, BorderLayout.NORTH);

        // Panel Central (comentarios)
        panelComentarios = new JPanel();
        panelComentarios.setOpaque(false);
        panelComentarios.setLayout(new BoxLayout(panelComentarios, BoxLayout.Y_AXIS));

        JScrollPane scrollComentarios = new JScrollPane(panelComentarios);
        scrollComentarios.setOpaque(false);
        scrollComentarios.getViewport().setOpaque(false);
        scrollComentarios.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollComentarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);

        // Logo centrado arriba
        JLabel lblLogoCentral = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        lblLogoCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoCentral.setBorder(BorderFactory.createEmptyBorder(12,0,8,0));
        panelCentral.add(lblLogoCentral, BorderLayout.NORTH);

        panelCentral.add(scrollComentarios, BorderLayout.CENTER);

        // Panel botón inferior (centrado)
        JPanel panelBoton = new JPanel(new GridBagLayout());
        panelBoton.setBackground(new Color(247, 246, 210));
        panelBoton.setPreferredSize(new Dimension(0, 70));
        JButton btnAgregarComentario = new JButton("Agregar Comentario");
        btnAgregarComentario.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnAgregarComentario.setBackground(new Color(255, 148, 195));
        btnAgregarComentario.setForeground(Color.BLACK);
        btnAgregarComentario.setFocusPainted(false);
        btnAgregarComentario.setPreferredSize(new Dimension(300, 40));
        // Centrar el botón usando GridBagConstraints
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 0;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        panelBoton.add(btnAgregarComentario, gbcBtn);

        panelDerecho.add(panelCentral, BorderLayout.CENTER);
        panelDerecho.add(panelBoton, BorderLayout.SOUTH);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        // Pantalla grande
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        recargarComentarios();

        btnAgregarComentario.addActionListener(e -> agregarComentarioBonito());

        lblSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new MenuCliente(cliente, usuarioServicio, ventanaBienvenida).setVisible(true);
            }
        });
    }

    private void recargarComentarios() {
        ComentariosClienteUtils.recargarComentarios(panelComentarios, comentarioServicio, cliente, this);
    }

    private void agregarComentarioBonito() {
        ComentariosClienteUtils.agregarComentarioBonito(this, comentarioServicio, cliente, panelComentarios, this);
    }
}