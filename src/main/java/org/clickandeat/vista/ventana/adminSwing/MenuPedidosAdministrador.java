package org.clickandeat.vista.ventana.adminSwing;

import org.clickandeat.funciones.restaurante.PedidoServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao.PedidoDao;
import org.clickandeat.modelo.entidades.pedido.DetallePedido;
import org.clickandeat.modelo.entidades.pedido.Pedido;
import org.clickandeat.modelo.entidades.pedido.EstadoPedidoEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.vista.ventana.adminSwing.menuProducto.MenuAdministracion;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuPedidosAdministrador extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final Usuario usuario;
    private final UsuarioServicio usuarioServicio;
    private final PedidoServicio pedidoServicio;
    private final PedidoDao pedidoDao;
    private final JFrame ventanaAnterior;
    private JPanel panelCentral;

    public MenuPedidosAdministrador(Usuario usuario, UsuarioServicio usuarioServicio, JFrame ventanaAnterior) {
        this.usuario = usuario;
        this.usuarioServicio = usuarioServicio;
        this.pedidoDao = new PedidoDao();
        this.pedidoServicio = new PedidoServicio(pedidoDao);
        this.ventanaAnterior = ventanaAnterior;

        setTitle("Gestión de Pedidos - Andy Burger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // === PANEL IZQUIERDO (sidebar) ===
        JPanel panelIzquierdo = crearSidebar();

        // === PANEL DERECHO ===
        JPanel panelDerecho = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 240, 172));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // === PANEL SUPERIOR ===
        JPanel panelSuperior = new JPanel(null) {
            @Override
            public boolean isOpaque() { return true; }
        };
        panelSuperior.setBackground(new Color(181, 224, 202));
        panelSuperior.setPreferredSize(new Dimension(0, 48));
        JLabel lblPanel = new JLabel("Gestión de Pedidos");
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

        // === PANEL CENTRAL: Lista de pedidos agrupados por estado ===
        panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setOpaque(false);

        JScrollPane scrollPedidos = new JScrollPane(panelCentral,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPedidos.setBorder(BorderFactory.createEmptyBorder(24, 36, 24, 36));
        scrollPedidos.getVerticalScrollBar().setUnitIncrement(24);

        // Botón actualizar
        JButton btnActualizar = new JButton("Actualizar lista");
        btnActualizar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 18));
        btnActualizar.setBackground(new Color(181, 224, 202));
        btnActualizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnActualizar.addActionListener(e -> cargarPedidos());

        panelDerecho.add(btnActualizar, BorderLayout.SOUTH);
        panelDerecho.add(scrollPedidos, BorderLayout.CENTER);

        // Cargar pedidos iniciales
        cargarPedidos();

        // Añadir ambos al principal
        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }

    private JPanel crearSidebar() {
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
        lblLogo.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
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
        JButton btnPedidos = null;
        JButton btnComentarios = null;
        JButton btnAdministracionMenu = null;
        for (int i = 0; i < opciones.length; i++) {
            JButton btn = new JButton(opciones[i]);
            btn.setFont(new Font(FUENTE_BONITA, Font.BOLD, 24));
            btn.setBackground(new Color(255, 162, 130));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLACK));
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            btn.setPreferredSize(new Dimension(240, 48));
            gbc.gridy = i;
            panelBotones.add(btn, gbc);

            if (opciones[i].equals("Inventario")) {
                btnInventario = btn;
            }
            if (opciones[i].equals("Pedidos")) {
                btnPedidos = btn;
                btn.setEnabled(false); // Ya estamos en pedidos
            }
            if (opciones[i].equals("Comentarios")) {
                btnComentarios = btn;
            }
            if (opciones[i].equals("Administración Menu")) {
                btnAdministracionMenu = btn;
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

        JLabel lblAdmin = new JLabel(usuario != null ? usuario.getNombre() : "Administrador");
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

        // ACCIONES DE LOS BOTONES LATERALES

        // Inventario
        if (btnInventario != null) {
            btnInventario.addActionListener(e -> {
                this.setVisible(false);
                new MenuInventario(usuario, usuarioServicio, ventanaAnterior).setVisible(true);
            });
        }

        // Pedidos (este mismo menú, no hace nada)
        if (btnPedidos != null) {
            btnPedidos.setEnabled(false);
        }

        // Comentarios
        if (btnComentarios != null) {
            btnComentarios.addActionListener(e -> {
                this.setVisible(false);
                new MenuComentariosAdministrador(usuario, usuarioServicio, ventanaAnterior).setVisible(true);
            });
        }

        // Administración Menú
        if (btnAdministracionMenu != null) {
            btnAdministracionMenu.addActionListener(e -> {
                this.setVisible(false);
                new MenuAdministracion(usuario, usuarioServicio, ventanaAnterior).setVisible(true);
            });
        }

        // Salir
        btnSalir.addActionListener(e -> {
            this.dispose();
            if (ventanaAnterior != null) {
                ventanaAnterior.setVisible(true);
            }
        });

        return panelIzquierdo;
    }

    private void cargarPedidos() {
        panelCentral.removeAll();

        List<Pedido> pedidos = new ArrayList<>(pedidoServicio.obtenerTodos());
        // Agrupar por estado usando LinkedHashMap para mantener el orden de los enums
        Map<EstadoPedidoEnum, List<Pedido>> pedidosPorEstado = new LinkedHashMap<>();
        for (EstadoPedidoEnum estado : EstadoPedidoEnum.values()) {
            pedidosPorEstado.put(estado, new ArrayList<>());
        }
        for (Pedido p : pedidos) {
            pedidosPorEstado.get(p.getEstado()).add(p);
        }

        for (EstadoPedidoEnum estado : EstadoPedidoEnum.values()) {
            List<Pedido> pedidosEstado = pedidosPorEstado.get(estado);
            if (!pedidosEstado.isEmpty()) {
                // Título de estado
                JLabel lblTitulo = new JLabel(nombreSeccionEstado(estado));
                lblTitulo.setFont(new Font(FUENTE_BONITA, Font.BOLD, 26));
                lblTitulo.setForeground(new Color(176, 121, 4));
                lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
                lblTitulo.setBorder(BorderFactory.createEmptyBorder(24, 8, 6, 0));
                panelCentral.add(lblTitulo);

                // Línea separadora ancha
                JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
                separator.setForeground(new Color(255, 162, 130));
                separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 3));
                panelCentral.add(separator);

                for (Pedido pedido : pedidosEstado) {
                    panelCentral.add(Box.createVerticalStrut(10));
                    panelCentral.add(crearPanelPedido(pedido));
                }
            }
        }
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    private String nombreSeccionEstado(EstadoPedidoEnum estado) {
        switch (estado) {
            case PENDIENTE: return "Pedidos Pendientes";
            case PAGADO: return "Pedidos Pagados";
            case CANCELADO: return "Pedidos Cancelados";
            case TERMINADO: return "Pedidos Terminados";
            default: return estado.name();
        }
    }

    private JPanel crearPanelPedido(Pedido pedido) {
        JPanel panelPedido = new JPanel(new BorderLayout(32, 0));
        panelPedido.setBackground(new Color(255, 255, 255, 225));
        panelPedido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 162, 130), 3, true),
                BorderFactory.createEmptyBorder(16, 28, 16, 28)
        ));
        // Altura variable
        panelPedido.setMinimumSize(new Dimension(700, 120));

        // Panel izquierdo: información del pedido
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setOpaque(false);
        panelInfo.setAlignmentY(Component.TOP_ALIGNMENT);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));

        String cliente = pedido.getCliente() != null ? pedido.getCliente().getNombre() : "N/A";
        String ticket = pedido.getNumeroTicket();
        String hora = pedido.getFechaPedido().toLocalTime().toString().substring(0, 5);
        String fecha = pedido.getFechaPedido().toLocalDate().toString();
        String observaciones = pedido.getObservaciones() != null ? pedido.getObservaciones() : "";
        String total = "$" + String.format("%.2f", pedido.getTotal());

        JLabel lblTitulo = new JLabel("Pedido #" + pedido.getId() + " | Cliente: " + cliente);
        lblTitulo.setFont(new Font(FUENTE_BONITA, Font.BOLD, 22));
        lblTitulo.setForeground(new Color(176, 121, 4));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfo.add(lblTitulo);

        JLabel lblTicket = new JLabel("Ticket: " + ticket);
        lblTicket.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 17));
        lblTicket.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfo.add(lblTicket);

        JLabel lblHora = new JLabel("Fecha/Hora: " + fecha + " " + hora);
        lblHora.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 17));
        lblHora.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfo.add(lblHora);

        if (!observaciones.isEmpty()) {
            JLabel lblObs = new JLabel("Observaciones: " + observaciones);
            lblObs.setFont(new Font(FUENTE_BONITA, Font.ITALIC, 14));
            lblObs.setForeground(new Color(80, 80, 80));
            lblObs.setAlignmentX(Component.LEFT_ALIGNMENT);
            lblObs.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
            panelInfo.add(lblObs);
        }

        panelInfo.add(Box.createVerticalStrut(4));

        // Productos/Promociones con mini-tarjeta
        JPanel panelItems = new JPanel();
        panelItems.setLayout(new BoxLayout(panelItems, BoxLayout.Y_AXIS));
        panelItems.setOpaque(false);
        panelItems.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        JLabel lblDetalle = new JLabel("Productos / Promociones:");
        lblDetalle.setFont(new Font(FUENTE_BONITA, Font.BOLD, 16));
        lblDetalle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelItems.add(lblDetalle);

        for (DetallePedido d : pedido.getDetalles()) {
            String item = d.getProducto() != null ?
                    d.getProducto().getNombre() :
                    (d.getPromocion() != null ? d.getPromocion().getNombre() + " (PROMO)" : "Item");

            JLabel lblItem = new JLabel("- " + item + " x" + d.getCantidad() + "  $" + String.format("%.2f", d.getSubtotal()));
            lblItem.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 15));
            lblItem.setAlignmentX(Component.LEFT_ALIGNMENT);

            JPanel itemPanel = new JPanel();
            itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 6, 2));
            itemPanel.setBackground(new Color(255, 250, 220));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 168, 90), 1, true),
                    BorderFactory.createEmptyBorder(2, 8, 2, 8)
            ));
            itemPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            itemPanel.add(lblItem);

            panelItems.add(itemPanel);
        }
        panelInfo.add(panelItems);

        JLabel lblTotal = new JLabel("Total: " + total);
        lblTotal.setFont(new Font(FUENTE_BONITA, Font.BOLD, 17));
        lblTotal.setForeground(new Color(49, 90, 23));
        lblTotal.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelInfo.add(lblTotal);

        panelPedido.add(panelInfo, BorderLayout.WEST);

        // Panel DERECHO: Estado y combo de cambio estado
        JPanel panelEstado = new JPanel();
        panelEstado.setOpaque(false);
        panelEstado.setLayout(new BoxLayout(panelEstado, BoxLayout.Y_AXIS));
        panelEstado.setPreferredSize(new Dimension(220, 180));
        panelEstado.setMaximumSize(new Dimension(260, 180));
        panelEstado.setAlignmentY(Component.CENTER_ALIGNMENT);

        panelEstado.add(Box.createVerticalGlue());

        JPanel panelEstadoLinea = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelEstadoLinea.setOpaque(false);

        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font(FUENTE_BONITA, Font.BOLD, 17));
        panelEstadoLinea.add(lblEstado);

        JComboBox<EstadoPedidoEnum> comboEstado = new JComboBox<>(EstadoPedidoEnum.values());
        comboEstado.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 16));
        comboEstado.setSelectedItem(pedido.getEstado());
        comboEstado.setBackground(Color.WHITE);
        comboEstado.setPreferredSize(new Dimension(120, 32));
        panelEstadoLinea.add(comboEstado);

        JButton btnCambiar = new JButton("Actualizar");
        btnCambiar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 15));
        btnCambiar.setBackground(new Color(181, 224, 202));
        btnCambiar.setPreferredSize(new Dimension(110, 32));
        btnCambiar.addActionListener(e -> {
            EstadoPedidoEnum seleccionado = (EstadoPedidoEnum) comboEstado.getSelectedItem();
            if (seleccionado != pedido.getEstado()) {
                pedido.setEstado(seleccionado);
                try {
                    pedidoServicio.actualizar(pedido);
                    JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.");
                    cargarPedidos();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar estado: " + ex.getMessage());
                }
            }
        });
        panelEstadoLinea.add(btnCambiar);

        panelEstado.add(panelEstadoLinea);
        panelEstado.add(Box.createVerticalGlue());

        panelPedido.add(panelEstado, BorderLayout.EAST);

        return panelPedido;
    }
}