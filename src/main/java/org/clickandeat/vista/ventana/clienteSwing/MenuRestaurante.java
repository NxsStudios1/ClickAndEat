package org.clickandeat.vista.ventana.clienteSwing;

import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.funciones.administracion.PromocionServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.funciones.restaurante.PedidoServicio;
import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.modelo.entidades.inventario.Promocion;
import org.clickandeat.modelo.entidades.pedido.DetallePedido;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class MenuRestaurante extends JFrame {

    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final Usuario cliente;
    private final ProductoServicio productoServicio;
    private final PromocionServicio promocionServicio;
    private final PedidoServicio pedidoServicio;
    private final JFrame ventanaBienvenida;
    private final UsuarioServicio usuarioServicio;
    private JScrollPane scrollPanelMenu;
    private final List<DetallePedido> carrito = new ArrayList<>();
    private final DefaultListModel<String> listaCarritoModel = new DefaultListModel<>();
    private final JLabel lblTotal = new JLabel("$0.00");
    private String observacionesPedido = "";
    private JTextField txtBuscar;
    private JPanel panelMenu;
    private List<Producto> productosDisponibles;
    private List<Promocion> promocionesActivas;

    public MenuRestaurante(Usuario cliente, ProductoServicio productoServicio, PromocionServicio promocionServicio, PedidoServicio pedidoServicio, JFrame ventanaBienvenida) {
        this(cliente, productoServicio, promocionServicio, pedidoServicio, ventanaBienvenida, null);
    }

    public MenuRestaurante(Usuario cliente, ProductoServicio productoServicio, PromocionServicio promocionServicio, PedidoServicio pedidoServicio, JFrame ventanaBienvenida, UsuarioServicio usuarioServicio) {
        this.cliente = cliente;
        this.productoServicio = productoServicio;
        this.promocionServicio = promocionServicio;
        this.pedidoServicio = pedidoServicio;
        this.ventanaBienvenida = ventanaBienvenida;
        this.usuarioServicio = usuarioServicio;

        setTitle("Menú Restaurante - Andy Burger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel Izquierdo (sidebar)
        JPanel panelIzquierdo = crearSidebar();

        // Panel Derecho : Carrito
        String[] observacionesRef = new String[]{observacionesPedido};
        JPanel panelCarrito = MenuRestauranteUtils.crearPanelCarrito(
                FUENTE_BONITA,
                lblTotal,
                carrito,
                listaCarritoModel,
                this::actualizarListaCarrito,
                pedidoServicio,
                cliente,
                this,
                observacionesRef,
                () -> observacionesPedido = ""
        );

        // Panel Central: Menú de productos y promociones ---
        panelMenu = crearPanelMenu();

        scrollPanelMenu = new JScrollPane(panelMenu,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Panel Buscador
        JPanel panelCentroConBuscador = new JPanel();
        panelCentroConBuscador.setLayout(new BorderLayout());
        panelCentroConBuscador.setBackground(panelMenu.getBackground());

        JPanel panelBuscador = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBuscador.setBackground(panelMenu.getBackground());
        JLabel lblBuscar = new JLabel("Buscar: ");
        lblBuscar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 16));
        btnBuscar.setBackground(new Color(181, 224, 202));
        btnBuscar.addActionListener(e -> filtrarMenu());
        txtBuscar.addActionListener(e -> filtrarMenu());
        panelBuscador.add(lblBuscar);
        panelBuscador.add(txtBuscar);
        panelBuscador.add(btnBuscar);

        panelCentroConBuscador.add(panelBuscador, BorderLayout.NORTH);
        panelCentroConBuscador.add(scrollPanelMenu, BorderLayout.CENTER);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelCentroConBuscador, BorderLayout.CENTER);
        panelPrincipal.add(panelCarrito, BorderLayout.EAST);

        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);

        SwingUtilities.invokeLater(() -> scrollPanelMenu.getVerticalScrollBar().setValue(0));
    }

    private JPanel crearSidebar() {
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(186, 240, 215));
        panelIzquierdo.setPreferredSize(new Dimension(320, 830));
        panelIzquierdo.setLayout(new BorderLayout());

        // Logo
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setOpaque(false);
        ImageIcon logo = new ImageIcon(getClass().getResource("/recursosGraficos/logos/logo_andyBurger.png"));
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setIcon(new ImageIcon(logo.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH)));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        panelArriba.add(lblLogo, BorderLayout.CENTER);
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        separator.setPreferredSize(new Dimension(320, 3));
        panelArriba.add(separator, BorderLayout.SOUTH);
        panelIzquierdo.add(panelArriba, BorderLayout.NORTH);

        // Botones de menú
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 0));
        panelBotones.setOpaque(false);
        JButton btnMenu = new JButton("Menú");
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
        btnComentarios.addActionListener(e -> {
            MenuComentario ventanaComentarios = new MenuComentario(cliente, usuarioServicio, ventanaBienvenida);
            ventanaComentarios.setVisible(true);
            this.dispose();
        });

        panelBotones.add(btnMenu);
        panelBotones.add(btnComentarios);

        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        JSeparator separatorInf = new JSeparator(SwingConstants.HORIZONTAL);
        separatorInf.setForeground(Color.BLACK);
        separatorInf.setPreferredSize(new Dimension(320, 3));
        panelIzquierdo.add(separatorInf, BorderLayout.SOUTH);

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
        btnSalir.addActionListener(e -> {
            this.dispose();
            MenuCliente menuCliente = new MenuCliente(
                    cliente,
                    usuarioServicio,
                    ventanaBienvenida
            );
            menuCliente.setVisible(true);
        });

        panelUsuario.add(btnSalir);

        JPanel panelUsuarioCont = new JPanel(new BorderLayout());
        panelUsuarioCont.setOpaque(false);
        panelUsuarioCont.add(panelUsuario, BorderLayout.CENTER);
        panelIzquierdo.add(panelUsuarioCont, BorderLayout.SOUTH);
        return panelIzquierdo;
    }

    private JPanel crearPanelMenu() {
        panelMenu = new JPanel(new GridBagLayout());
        panelMenu.setBackground(new Color(246, 227, 203));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0; gbc.gridy = 0;

        if (promocionesActivas == null) {
            promocionesActivas = promocionServicio.obtenerTodosConProductos().stream()
                    .filter(promo -> Boolean.TRUE.equals(promo.getActivo()))
                    .collect(Collectors.toList());
        }
        if (productosDisponibles == null) {
            productosDisponibles = productoServicio.obtenerTodos().stream()
                    .filter(p -> Boolean.TRUE.equals(p.getDisponible()))
                    .collect(Collectors.toList());
        }

        JLabel lblPromociones = new JLabel("Promociones");
        lblPromociones.setFont(new Font(FUENTE_BONITA, Font.BOLD, 34));
        lblPromociones.setForeground(new Color(176, 121, 4));
        GridBagConstraints gbcTituloPromo = (GridBagConstraints) gbc.clone();
        gbcTituloPromo.gridwidth = 3;
        gbcTituloPromo.gridx = 0;
        gbcTituloPromo.gridy = 0;
        gbcTituloPromo.insets = new Insets(10, 0, 10, 0);
        panelMenu.add(lblPromociones, gbcTituloPromo);

        gbc.gridy = 1;
        gbc.gridx = 0;

        int colPromo = 0;
        for (Promocion promo : promocionesActivas) {
            JPanel tarjeta = MenuRestauranteUtils.crearTarjetaPromocion(
                    promo,
                    FUENTE_BONITA,
                    carrito,
                    this::actualizarListaCarrito,
                    this
            );
            panelMenu.add(tarjeta, gbc);
            colPromo++;
            gbc.gridx++;
            if (colPromo > 2) {
                colPromo = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
        if (colPromo != 0) {
            gbc.gridy++;
            gbc.gridx = 0;
        }

        JLabel lblProductos = new JLabel("Productos");
        lblProductos.setFont(new Font(FUENTE_BONITA, Font.BOLD, 34));
        lblProductos.setForeground(new Color(49, 90, 23));
        GridBagConstraints gbcTituloProd = (GridBagConstraints) gbc.clone();
        gbcTituloProd.gridwidth = 3;
        gbcTituloProd.gridx = 0;
        gbcTituloProd.gridy = gbc.gridy;
        gbcTituloProd.insets = new Insets(30, 0, 10, 0);
        panelMenu.add(lblProductos, gbcTituloProd);

        gbc.gridy++;
        gbc.gridx = 0;

        int colProd = 0;
        for (Producto producto : productosDisponibles) {
            JPanel tarjeta = MenuRestauranteUtils.crearTarjetaProducto(
                    producto,
                    FUENTE_BONITA,
                    carrito,
                    this::actualizarListaCarrito,
                    this
            );
            panelMenu.add(tarjeta, gbc);
            colProd++;
            gbc.gridx++;
            if (colProd > 2) {
                colProd = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }

        return panelMenu;
    }

    private void filtrarMenu() {
        String texto = txtBuscar.getText().trim().toLowerCase();
        List<Promocion> promocionesFiltradas;
        List<Producto> productosFiltrados;
        if (texto.isEmpty()) {
            promocionesFiltradas = promocionesActivas;
            productosFiltrados = productosDisponibles;
        } else {
            promocionesFiltradas = promocionesActivas.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(texto))
                    .collect(Collectors.toList());
            productosFiltrados = productosDisponibles.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(texto))
                    .collect(Collectors.toList());
        }

        // Reconstruye el panelMenu
        panelMenu.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel lblPromociones = new JLabel("Promociones");
        lblPromociones.setFont(new Font(FUENTE_BONITA, Font.BOLD, 34));
        lblPromociones.setForeground(new Color(176, 121, 4));
        GridBagConstraints gbcTituloPromo = (GridBagConstraints) gbc.clone();
        gbcTituloPromo.gridwidth = 3;
        gbcTituloPromo.gridx = 0;
        gbcTituloPromo.gridy = 0;
        gbcTituloPromo.insets = new Insets(10, 0, 10, 0);
        panelMenu.add(lblPromociones, gbcTituloPromo);

        gbc.gridy = 1;
        gbc.gridx = 0;

        int colPromo = 0;
        for (Promocion promo : promocionesFiltradas) {
            JPanel tarjeta = MenuRestauranteUtils.crearTarjetaPromocion(
                    promo,
                    FUENTE_BONITA,
                    carrito,
                    this::actualizarListaCarrito,
                    this
            );
            panelMenu.add(tarjeta, gbc);
            colPromo++;
            gbc.gridx++;
            if (colPromo > 2) {
                colPromo = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
        if (colPromo != 0) {
            gbc.gridy++;
            gbc.gridx = 0;
        }

        JLabel lblProductos = new JLabel("Productos");
        lblProductos.setFont(new Font(FUENTE_BONITA, Font.BOLD, 34));
        lblProductos.setForeground(new Color(49, 90, 23));
        GridBagConstraints gbcTituloProd = (GridBagConstraints) gbc.clone();
        gbcTituloProd.gridwidth = 3;
        gbcTituloProd.gridx = 0;
        gbcTituloProd.gridy = gbc.gridy;
        gbcTituloProd.insets = new Insets(30, 0, 10, 0);
        panelMenu.add(lblProductos, gbcTituloProd);
        gbc.gridy++;
        gbc.gridx = 0;

        int colProd = 0;
        for (Producto producto : productosFiltrados) {
            JPanel tarjeta = MenuRestauranteUtils.crearTarjetaProducto(
                    producto,
                    FUENTE_BONITA,
                    carrito,
                    this::actualizarListaCarrito,
                    this
            );
            panelMenu.add(tarjeta, gbc);
            colProd++;
            gbc.gridx++;
            if (colProd > 2) {
                colProd = 0;
                gbc.gridx = 0;
                gbc.gridy++;
            }
        }
        panelMenu.revalidate();
        panelMenu.repaint();
    }

    private void actualizarListaCarrito() {
        MenuRestauranteUtils.actualizarListaCarrito(carrito, listaCarritoModel, lblTotal);
    }

}