package org.clickandeat.vista.ventana.adminSwing.administracion;

import org.clickandeat.funciones.administracion.CategoriaProductoServicio;
import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.funciones.administracion.PromocionProductoServicio;
import org.clickandeat.funciones.administracion.PromocionServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.CategoriaProductoDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionProductoDao;
import org.clickandeat.modelo.entidades.inventario.CategoriaProducto;
import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.vista.ventana.adminSwing.comentario.MenuComentariosAdministrador;
import org.clickandeat.vista.ventana.adminSwing.inventario.MenuInventario;
import org.clickandeat.vista.ventana.adminSwing.pedidos.MenuPedidos;
import org.clickandeat.vista.ventana.inicioSwing.InicioSesion;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MenuAdministracion extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final Usuario usuario;
    private final UsuarioServicio usuarioServicio;
    private final JFrame menuAdministradorSwing;
    private JPanel panelCentral;
    private JButton btnProducto;
    private JButton btnPromociones;
    private CategoriaTableModel categoriaTableModel;
    private ProductoTableModel productoTableModel;
    private final CategoriaProductoServicio categoriaServicio;
    private final ProductoServicio productoServicio;
    private final PromocionServicio promocionServicio;
    private final PromocionProductoServicio promocionProductoServicio;

    private JTextField txtBuscarCategoria;
    private JTextField txtBuscarProducto;

    public MenuAdministracion(Usuario usuario, UsuarioServicio usuarioServicio, JFrame menuAdministradorSwing) {
        this.usuario = usuario;
        this.usuarioServicio = usuarioServicio;
        this.menuAdministradorSwing = menuAdministradorSwing;
        this.categoriaServicio = new CategoriaProductoServicio(new CategoriaProductoDao());
        this.productoServicio = new ProductoServicio(new ProductoDao());
        this.promocionServicio = new PromocionServicio(new PromocionDao());
        this.promocionProductoServicio = new PromocionProductoServicio(new PromocionProductoDao());

        setTitle("Administración Menú - AndyBurger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Sidebar Izquierdo
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(255, 162, 130));
        panelIzquierdo.setPreferredSize(new Dimension(270, 1000));
        panelIzquierdo.setLayout(new BorderLayout());

        // Logo
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

        // Panel de botones menú
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 0, 14, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] opciones = {"Inventario", "Pedidos", "Comentarios", "Administración Menu"};
        JButton btnInventario = null;
        JButton btnComentarios = null;
        JButton btnAdministracionMenu = null;
        JButton btnPedidos = null;
        for (int i = 0; i < opciones.length; i++) {
            JButton btn = new JButton(opciones[i]);
            btn.setFont(new Font(FUENTE_BONITA, Font.BOLD, 24));
            btn.setBackground(new Color(255, 162, 130));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLACK));
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            btn.setPreferredSize(new Dimension(240, 50));
            gbc.gridy = i;
            panelBotones.add(btn, gbc);

            if ("Inventario".equals(opciones[i])) {
                btnInventario = btn;
            }
            if ("Comentarios".equals(opciones[i])) {
                btnComentarios = btn;
            }
            if ("Pedidos".equals(opciones[i])) {
                btnPedidos = btn;
            }
            if ("Administración Menu".equals(opciones[i])) {
                btn.setEnabled(false);
                btnAdministracionMenu = btn;
            }
        }
        gbc.weighty = 1;
        gbc.gridy = opciones.length;
        panelBotones.add(Box.createVerticalGlue(), gbc);

        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        // Usuario abajo
        JPanel panelUsuario = new JPanel(null);
        panelUsuario.setBackground(new Color(255, 162, 130));
        panelUsuario.setPreferredSize(new Dimension(270, 120));

        ImageIcon iconoUser = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoUser = new JLabel(new ImageIcon(iconoUser.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        lblIconoUser.setBounds(28, 34, 64, 64);
        panelUsuario.add(lblIconoUser);

        JLabel lblAdmin = new JLabel(usuario != null ? usuario.getNombre() : "Administrador");
        lblAdmin.setFont(new Font(FUENTE_BONITA, Font.BOLD, 18));
        lblAdmin.setBounds(110, 60, 150, 30);
        panelUsuario.add(lblAdmin);

        ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/salir.png"));
        JButton btnSalir = new JButton(new ImageIcon(iconoSalir.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH)));
        btnSalir.setBounds(220, 60, 36, 36);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelUsuario.add(btnSalir);

        panelIzquierdo.add(panelUsuario, BorderLayout.SOUTH);

        // Acción salir
        btnSalir.addActionListener(e -> {
            this.dispose();
            if (menuAdministradorSwing != null) {
                menuAdministradorSwing.setVisible(true);
            } else {
                new InicioSesion(usuarioServicio, null).setVisible(true);
            }
        });

        // Acción Inventario
        if (btnInventario != null) {
            btnInventario.addActionListener(e -> {
                this.setVisible(false);
                new MenuInventario(usuario, usuarioServicio, menuAdministradorSwing).setVisible(true);
            });
        }

        // Acción Pedidos
        if (btnPedidos != null) {
            btnPedidos.addActionListener(e -> {
                this.setVisible(false);
                new MenuPedidos(usuario, usuarioServicio, menuAdministradorSwing).setVisible(true);
            });
        }

        // Acción Comentarios
        if (btnComentarios != null) {
            btnComentarios.addActionListener(e -> {
                this.setVisible(false);
                new MenuComentariosAdministrador(usuario, usuarioServicio, menuAdministradorSwing).setVisible(true);
            });
        }

        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        // Panel Derecho
        JPanel panelDerecho = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 240, 172));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JPanel panelSuperior = new JPanel(null) {
            @Override
            public boolean isOpaque() { return true; }
        };
        panelSuperior.setBackground(new Color(181, 224, 202));
        panelSuperior.setPreferredSize(new Dimension(0, 48));

        JLabel lblPanel = new JLabel("Administración Del Menu");
        lblPanel.setFont(new Font(FUENTE_BONITA, Font.BOLD, 26));
        lblPanel.setForeground(Color.BLACK);
        lblPanel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPanel.setBounds(20, 10, 800, 38);
        panelSuperior.add(lblPanel);
        panelSuperior.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int ancho = panelSuperior.getWidth();
                lblPanel.setBounds(20, 10, ancho - 40, 38);
            }
        });
        panelDerecho.add(panelSuperior, BorderLayout.NORTH);

        JPanel panelTabs = new JPanel();
        panelTabs.setBackground(new Color(246, 227, 203));
        panelTabs.setLayout(new FlowLayout(FlowLayout.LEFT, 18, 18));

        btnProducto = new JButton("Producto");
        btnPromociones = new JButton("Promociones");
        Font tabFont = new Font(FUENTE_BONITA, Font.BOLD, 22);
        btnProducto.setFont(tabFont);
        btnPromociones.setFont(tabFont);
        btnProducto.setBackground(Color.WHITE);
        btnPromociones.setBackground(new Color(246, 227, 203));
        btnProducto.setFocusPainted(false);
        btnPromociones.setFocusPainted(false);
        panelTabs.add(btnProducto);
        panelTabs.add(btnPromociones);

        // Panel Central - FlowLayout para separar y alinear
        panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(246, 227, 203));
        mostrarPanelProducto();

        btnProducto.addActionListener(e -> {
            setTabActivo(btnProducto);
            mostrarPanelProducto();
        });
        btnPromociones.addActionListener(e -> {
            setTabActivo(btnPromociones);
            mostrarPanelPromociones();
        });

        JPanel panelSeccion = new JPanel(new BorderLayout());
        panelSeccion.setBackground(new Color(246, 227, 203));
        panelSeccion.add(panelTabs, BorderLayout.NORTH);
        panelSeccion.add(panelCentral, BorderLayout.CENTER);

        panelDerecho.add(panelSeccion, BorderLayout.CENTER);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    private void setTabActivo(JButton btnActivo) {
        btnProducto.setBackground(new Color(246, 227, 203));
        btnPromociones.setBackground(new Color(246, 227, 203));
        btnActivo.setBackground(Color.WHITE);
    }

    // Panel Producto
    private void mostrarPanelProducto() {
        panelCentral.removeAll();

        JPanel panelTablas = new JPanel();
        panelTablas.setLayout(new BoxLayout(panelTablas, BoxLayout.Y_AXIS));
        panelTablas.setOpaque(false);

        //  Categoría Producto
        JPanel panelCategoria = new JPanel();
        panelCategoria.setOpaque(false);
        panelCategoria.setLayout(new BorderLayout());
        panelCategoria.setMaximumSize(new Dimension(1200, 210));

        // Header Categoría
        JPanel headerCategoria = new JPanel(new BorderLayout());
        headerCategoria.setOpaque(false);
        JPanel headerCatCenter = new JPanel();
        headerCatCenter.setOpaque(false);
        JLabel lblCategoria = new JLabel("Categoría Producto");
        lblCategoria.setFont(new Font(FUENTE_BONITA, Font.BOLD, 32));
        lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
        headerCatCenter.add(lblCategoria);
        headerCategoria.add(headerCatCenter, BorderLayout.CENTER);

        JPanel panelBuscarCat = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBuscarCat.setOpaque(false);
        JLabel lblBuscarCat = new JLabel("Buscar:");
        lblBuscarCat.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        txtBuscarCategoria = new JTextField(12);
        txtBuscarCategoria.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        panelBuscarCat.add(lblBuscarCat);
        panelBuscarCat.add(txtBuscarCategoria);
        headerCategoria.add(panelBuscarCat, BorderLayout.EAST);

        categoriaTableModel = new CategoriaTableModel();
        categoriaTableModel.setCategorias(categoriaServicio.obtenerTodos());
        JTable tablaCategoria = new JTable(categoriaTableModel);
        tablaCategoria.setRowHeight(70);
        tablaCategoria.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 14));
        tablaCategoria.getTableHeader().setFont(new Font(FUENTE_BONITA, Font.BOLD, 18));
        tablaCategoria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCategoria.setPreferredScrollableViewportSize(new Dimension(600, 180));
        JScrollPane scrollCategoria = new JScrollPane(tablaCategoria);
        scrollCategoria.setPreferredSize(new Dimension(650, 180));
        JPanel panelTablaCat = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelTablaCat.setOpaque(false);
        panelTablaCat.add(scrollCategoria);

        // Centrar Tecto
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaCategoria.getColumnCount(); i++) {
            tablaCategoria.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            ((DefaultTableCellRenderer) tablaCategoria.getTableHeader().getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);
        }

        JPanel panelBotonesCat = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        panelBotonesCat.setOpaque(false);
        JButton btnAgregarCategoria = new JButton("Agregar");
        btnAgregarCategoria.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        JButton btnEditarCategoria = new JButton("Editar");
        btnEditarCategoria.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        JButton btnEliminarCategoria = new JButton("Eliminar");
        btnEliminarCategoria.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        panelBotonesCat.add(btnAgregarCategoria);
        panelBotonesCat.add(btnEditarCategoria);
        panelBotonesCat.add(btnEliminarCategoria);

        panelCategoria.add(headerCategoria, BorderLayout.NORTH);
        panelCategoria.add(panelTablaCat, BorderLayout.CENTER);
        panelCategoria.add(panelBotonesCat, BorderLayout.SOUTH);

        //  Producto
        JPanel panelProducto = new JPanel();
        panelProducto.setOpaque(false);
        panelProducto.setLayout(new BorderLayout());
        panelProducto.setMaximumSize(new Dimension(2000, 350));

        JPanel headerProducto = new JPanel(new BorderLayout());
        headerProducto.setOpaque(false);
        JPanel headerProdCenter = new JPanel();
        headerProdCenter.setOpaque(false);
        JLabel lblProducto = new JLabel("Producto");
        lblProducto.setFont(new Font(FUENTE_BONITA, Font.BOLD, 32));
        lblProducto.setHorizontalAlignment(SwingConstants.CENTER);
        headerProdCenter.add(lblProducto);
        headerProducto.add(headerProdCenter, BorderLayout.CENTER);

        JPanel panelBuscarProd = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBuscarProd.setOpaque(false);
        JLabel lblBuscarProd = new JLabel("Buscar:");
        lblBuscarProd.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        txtBuscarProducto = new JTextField(12);
        txtBuscarProducto.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        panelBuscarProd.add(lblBuscarProd);
        panelBuscarProd.add(txtBuscarProducto);
        headerProducto.add(panelBuscarProd, BorderLayout.EAST);

        productoTableModel = new ProductoTableModel();
        productoTableModel.setProductos(productoServicio.obtenerTodos());
        JTable tablaProducto = new JTable(productoTableModel);
        tablaProducto.setRowHeight(65);
        tablaProducto.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 14));
        tablaProducto.getTableHeader().setFont(new Font(FUENTE_BONITA, Font.BOLD, 18));
        tablaProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaProducto.setPreferredScrollableViewportSize(new Dimension(1500, 250));
        JScrollPane scrollProducto = new JScrollPane(tablaProducto);
        scrollProducto.setPreferredSize(new Dimension(1550, 400));

        int[] anchoColumnas = {70, 300, 450, 180, 130, 130};
        for (int i = 0; i < anchoColumnas.length && i < tablaProducto.getColumnCount(); i++) {
            tablaProducto.getColumnModel().getColumn(i).setPreferredWidth(anchoColumnas[i]);
            tablaProducto.getColumnModel().getColumn(i).setMinWidth(50);
        }


        JPanel panelTablaProd = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelTablaProd.setOpaque(false);
        panelTablaProd.add(scrollProducto);

        // Centrar Celdas y Headers
        DefaultTableCellRenderer centerRendererProd = new DefaultTableCellRenderer();
        centerRendererProd.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tablaProducto.getColumnCount(); i++) {
            tablaProducto.getColumnModel().getColumn(i).setCellRenderer(centerRendererProd);
            ((DefaultTableCellRenderer) tablaProducto.getTableHeader().getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);
        }

        JPanel panelBotonesProd = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 2));
        panelBotonesProd.setOpaque(false);
        JButton btnAgregarProducto = new JButton("Agregar");
        btnAgregarProducto.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        JButton btnEditarProducto = new JButton("Editar");
        btnEditarProducto.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        JButton btnEliminarProducto = new JButton("Eliminar");
        btnEliminarProducto.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        JButton btnDesactivarProducto = new JButton("Desactivar");
        btnDesactivarProducto.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        panelBotonesProd.add(btnAgregarProducto);
        panelBotonesProd.add(btnEditarProducto);
        panelBotonesProd.add(btnEliminarProducto);
        panelBotonesProd.add(btnDesactivarProducto);

        panelProducto.add(headerProducto, BorderLayout.NORTH);
        panelProducto.add(panelTablaProd, BorderLayout.CENTER);
        panelProducto.add(panelBotonesProd, BorderLayout.SOUTH);

        // Separador visual
        JSeparator separador = new JSeparator(SwingConstants.HORIZONTAL);
        separador.setPreferredSize(new Dimension(1100, 10));

        panelTablas.add(Box.createVerticalStrut(10));
        panelTablas.add(panelCategoria);
        panelTablas.add(Box.createVerticalStrut(4));
        panelTablas.add(separador);
        panelTablas.add(Box.createVerticalStrut(2));
        panelTablas.add(panelProducto);

        panelCentral.setLayout(new BorderLayout());
        panelCentral.add(panelTablas, BorderLayout.NORTH);

        panelCentral.revalidate();
        panelCentral.repaint();

        // Listeners
        txtBuscarCategoria.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                categoriaTableModel.filtrar(txtBuscarCategoria.getText());
            }
        });
        txtBuscarProducto.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                productoTableModel.filtrar(txtBuscarProducto.getText());
            }
        });

        btnAgregarCategoria.addActionListener(e ->
                DialogoProducto.dialogAgregarCategoria(this, categoriaServicio, categoriaTableModel)
        );
        btnEditarCategoria.addActionListener(e ->
                DialogoProducto.dialogEditarCategoria(this, categoriaServicio, categoriaTableModel, tablaCategoria.getSelectedRow())
        );

        btnEliminarCategoria.addActionListener(e ->
                DialogoProducto.dialogEliminarCategoria(this, categoriaServicio, categoriaTableModel, productoServicio, tablaCategoria.getSelectedRow()
                )
        );

        btnAgregarProducto.addActionListener(e ->
                DialogoProducto.dialogAgregarProducto(this, productoServicio, categoriaServicio, productoTableModel)
        );
        btnEditarProducto.addActionListener(e ->
                DialogoProducto.dialogEditarProducto(this, productoServicio, categoriaServicio, productoTableModel, tablaProducto.getSelectedRow())
        );
        btnEliminarProducto.addActionListener(e ->
                DialogoProducto.dialogEliminarProducto(this, productoServicio, productoTableModel, tablaProducto.getSelectedRow())
        );
        btnDesactivarProducto.addActionListener(e -> {
            int row = tablaProducto.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona un producto.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Producto prod = productoTableModel.getProductoAt(row);
            boolean nuevoEstado = !prod.getDisponible();
            String accion = nuevoEstado ? "activar" : "desactivar";
            int conf = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas " + accion + " este producto?",
                    "Confirmación", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                prod.setDisponible(nuevoEstado);
                boolean exito = productoServicio.actualizarProducto(prod);
                if (exito) {
                    productoTableModel.actualizarLista(productoServicio.obtenerTodos());
                } else {
                    JOptionPane.showMessageDialog(this, "Error al " + accion + " el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void mostrarPanelPromociones() {
        panelCentral.removeAll();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.add(
                new PanelPromociones(promocionServicio, productoServicio, promocionProductoServicio),
                BorderLayout.CENTER
        );
        panelCentral.revalidate();
        panelCentral.repaint();
    }

    static class CategoriaTableModel extends AbstractTableModel {
        private final String[] columnas = {"ID", "Nombre"};
        private List<CategoriaProducto> categorias = new ArrayList<>();
        private List<CategoriaProducto> filtradas = new ArrayList<>();
        public void setCategorias(List<CategoriaProducto> lista) {
            categorias = lista != null ? new ArrayList<>(lista) : new ArrayList<>();
            filtradas = new ArrayList<>(categorias);
            fireTableDataChanged();
        }
        public void filtrar(String texto) {
            filtradas.clear();
            for (CategoriaProducto cat : categorias) {
                if (cat.getNombre() != null && cat.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                        (cat.getId() != null && String.valueOf(cat.getId()).contains(texto))) {
                    filtradas.add(cat);
                }
            }
            fireTableDataChanged();
        }
        @Override public int getRowCount() { return filtradas.size(); }
        @Override public int getColumnCount() { return columnas.length; }
        @Override public String getColumnName(int col) { return columnas[col]; }
        @Override public Object getValueAt(int row, int col) {
            CategoriaProducto cat = filtradas.get(row);
            switch (col) {
                case 0: return cat.getId();
                case 1: return cat.getNombre();
                default: return "";
            }
        }
        public CategoriaProducto getCategoriaAt(int row) {
            if (row >= 0 && row < filtradas.size()) return filtradas.get(row);
            return null;
        }
        public void actualizarLista(List<CategoriaProducto> nueva) {
            setCategorias(nueva);
        }
    }

    static class ProductoTableModel extends AbstractTableModel {
        private final String[] columnas = {"ID", "Nombre", "Descripción", "Categoría", "Precio", "Disponibilidad"};
        private List<Producto> productos = new ArrayList<>();
        private List<Producto> filtrados = new ArrayList<>();
        public void setProductos(List<Producto> lista) {
            productos = lista != null ? new ArrayList<>(lista) : new ArrayList<>();
            filtrados = new ArrayList<>(productos);
            fireTableDataChanged();
        }
        public void filtrar(String texto) {
            filtrados.clear();
            for (Producto p : productos) {
                if ((p.getNombre() != null && p.getNombre().toLowerCase().contains(texto.toLowerCase())) ||
                        (p.getId() != null && String.valueOf(p.getId()).contains(texto)) ||
                        (p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(texto.toLowerCase()))) {
                    filtrados.add(p);
                }
            }
            fireTableDataChanged();
        }
        @Override public int getRowCount() { return filtrados.size(); }
        @Override public int getColumnCount() { return columnas.length; }
        @Override public String getColumnName(int col) { return columnas[col]; }
        @Override public Object getValueAt(int row, int col) {
            Producto p = filtrados.get(row);
            switch (col) {
                case 0: return p.getId();
                case 1: return p.getNombre();
                case 2: return p.getDescripcion();
                case 3: return p.getCategoria() != null ? p.getCategoria().getNombre() : "-";
                case 4: return String.format("$%.2f", p.getPrecio());
                case 5: return p.getDisponible() != null && p.getDisponible() ? "Disponible" : "No disponible";
                default: return "";
            }
        }
        public Producto getProductoAt(int row) {
            if (row >= 0 && row < filtrados.size()) return filtrados.get(row);
            return null;
        }
        public void actualizarLista(List<Producto> nueva) {
            setProductos(nueva);
        }
    }
}