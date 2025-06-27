package org.clickandeat.vista.ventana.adminSwing;

import org.clickandeat.funciones.administracion.CategoriaProductoServicio;
import org.clickandeat.funciones.administracion.InventarioServicio;
import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.CategoriaProductoDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.IngredienteDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoDao;
import org.clickandeat.modelo.entidades.inventario.Ingrediente;
import org.clickandeat.modelo.entidades.inventario.UnidadMedidaEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.vista.ventana.adminSwing.menu.MenuAdministracionSwing;
import org.clickandeat.vista.ventana.inicioSwing.InicioSesionSwing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MenuInventarioSwing extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";

    private final Usuario usuario;
    private final UsuarioServicio usuarioServicio;
    private final JFrame menuAdministradorSwing; // Referencia al menú principal
    private final InventarioServicio inventarioServicio;
    private final DefaultTableModel model;
    private final JTable tabla;

    public MenuInventarioSwing(Usuario usuario, UsuarioServicio usuarioServicio, JFrame menuAdministradorSwing) {
        this.usuario = usuario;
        this.usuarioServicio = usuarioServicio;
        this.menuAdministradorSwing = menuAdministradorSwing;
        this.inventarioServicio = new InventarioServicio(new IngredienteDao());

        setTitle("Inventario de Ingredientes - Andy Burger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // PANEL IZQUIERDO (sidebar)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(255, 162, 130));
        panelIzquierdo.setPreferredSize(new Dimension(270, 768));
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

        // Panel de botones menú (centrados)
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
                btn.setEnabled(false);
                btnInventario = btn;
            }
            if ("Comentarios".equals(opciones[i])) {
                btnComentarios = btn;
            }
            if ("Administración Menu".equals(opciones[i])) {
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
        panelUsuario.setPreferredSize(new Dimension(270, 85));

        ImageIcon iconoUser = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoUser = new JLabel(new ImageIcon(iconoUser.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH)));
        lblIconoUser.setBounds(15, 14, 56, 56);
        panelUsuario.add(lblIconoUser);

        JLabel lblAdmin = new JLabel(usuario != null ? usuario.getNombre() : "Administrador");
        lblAdmin.setFont(new Font(FUENTE_BONITA, Font.BOLD, 18));
        lblAdmin.setBounds(85, 32, 130, 30);
        panelUsuario.add(lblAdmin);

        ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/salir.png"));
        JButton btnSalir = new JButton(new ImageIcon(iconoSalir.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH)));
        btnSalir.setBounds(205, 32, 32, 32);
        btnSalir.setBorderPainted(false);
        btnSalir.setFocusPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelUsuario.add(btnSalir);

        // Acción para regresar SIEMPRE al menú administrador
        btnSalir.addActionListener(e -> {
            this.dispose();
            if (menuAdministradorSwing != null) {
                menuAdministradorSwing.setVisible(true);
            } else {
                new InicioSesionSwing(usuarioServicio, null).setVisible(true);
            }
        });

        panelIzquierdo.add(panelUsuario, BorderLayout.SOUTH);

        // ---- ACCIÓN PARA IR A COMENTARIOS ----
        if (btnComentarios != null) {
            btnComentarios.addActionListener(e -> {
                this.setVisible(false);
                new MenuComentariosAdministradorSwing(usuario, usuarioServicio, menuAdministradorSwing).setVisible(true);
            });
        }
        // ---- ACCIÓN PARA IR A MENÚ ADMINISTRACIÓN ----
        if (btnAdministracionMenu != null) {
            btnAdministracionMenu.addActionListener(e -> {
                this.setVisible(false);
                new MenuAdministracionSwing(usuario, usuarioServicio, menuAdministradorSwing).setVisible(true);
            });
        }
        // ---------------------------------------

        // PANEL DERECHO
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
        panelSuperior.setPreferredSize(new Dimension(0, 56));

        JLabel lblPanel = new JLabel("Inventario de Ingredientes");
        lblPanel.setFont(new Font(FUENTE_BONITA, Font.BOLD, 32));
        lblPanel.setForeground(Color.BLACK);
        lblPanel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPanel.setBounds(panelSuperior.getPreferredSize().width - 600, 4, 590, 48);
        panelSuperior.add(lblPanel);

        panelSuperior.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lblPanel.setBounds(panelSuperior.getWidth() - 600, 4, 590, 48);
            }
        });

        panelDerecho.add(panelSuperior, BorderLayout.NORTH);

        String[] columnas = {
                "ID", "Nombre", "Descripción", "Cantidad", "Unidad Medida", "Stock Actual", "Precio Unitario"
        };

        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(model);
        tabla.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        tabla.setRowHeight(36);
        tabla.getTableHeader().setFont(new Font(FUENTE_BONITA, Font.BOLD, 18));
        tabla.getTableHeader().setBackground(new Color(255, 221, 120));
        tabla.getTableHeader().setForeground(Color.BLACK);

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < columnas.length; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(520);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(175);

        tabla.setBackground(new Color(240, 240, 240));
        JScrollPane scroll = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        scroll.getViewport().setBackground(new Color(240, 240, 240));

        JPanel panelBotonesTabla = new JPanel();
        panelBotonesTabla.setBackground(new Color(255, 245, 180));
        panelBotonesTabla.setLayout(new FlowLayout(FlowLayout.CENTER, 36, 18));

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnAgregar.setBackground(new Color(181, 224, 202));
        btnAgregar.setFocusPainted(false);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnEditar.setBackground(new Color(255, 221, 120));
        btnEditar.setFocusPainted(false);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnEliminar.setBackground(new Color(255, 162, 130));
        btnEliminar.setFocusPainted(false);

        JButton btnConsulta = new JButton("Consulta");
        btnConsulta.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnConsulta.setBackground(new Color(181, 224, 202));
        btnConsulta.setFocusPainted(false);

        panelBotonesTabla.add(btnAgregar);
        panelBotonesTabla.add(btnEditar);
        panelBotonesTabla.add(btnEliminar);
        panelBotonesTabla.add(btnConsulta);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setOpaque(false);
        panelCentral.add(scroll, BorderLayout.CENTER);
        panelCentral.add(panelBotonesTabla, BorderLayout.SOUTH);

        panelDerecho.add(panelCentral, BorderLayout.CENTER);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        cargarIngredientesEnTabla();

        btnAgregar.addActionListener(e -> agregarIngrediente());
        btnEditar.addActionListener(e -> editarIngrediente());
        btnEliminar.addActionListener(e -> eliminarIngrediente());
        btnConsulta.addActionListener(e -> consultarIngrediente());
    }


    private void cargarIngredientesEnTabla() {
        model.setRowCount(0);
        List<Ingrediente> lista = inventarioServicio.obtenerTodos();
        for (Ingrediente ing : lista) {
            model.addRow(new Object[]{
                    ing.getId(),
                    ing.getNombre(),
                    ing.getDescripcion(),
                    ing.getCantidadPorcion(),
                    ing.getUnidadMedida() != null ? ing.getUnidadMedida().name() : "",
                    ing.getStockActual(),
                    ing.getPrecioUnitario()
            });
        }
    }

    private void agregarIngrediente() {
        Ingrediente nuevo = mostrarDialogoIngrediente(null);
        if (nuevo != null) {
            try {
                if (inventarioServicio.guardarIngrediente(nuevo)) {
                    cargarIngredientesEnTabla();
                    JOptionPane.showMessageDialog(this, "Ingrediente agregado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar ingrediente.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
    }

    private void editarIngrediente() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) model.getValueAt(fila, 0);
            Ingrediente original = inventarioServicio.buscarIngredientePorId(id);
            if (original != null) {
                Ingrediente editado = mostrarDialogoIngrediente(original);
                if (editado != null) {
                    editado.setId(id);
                    try {
                        if (inventarioServicio.actualizarIngrediente(editado)) {
                            cargarIngredientesEnTabla();
                            JOptionPane.showMessageDialog(this, "Ingrediente actualizado correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al actualizar ingrediente.");
                        }
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un ingrediente para editar.");
        }
    }

    private void eliminarIngrediente() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) model.getValueAt(fila, 0);
            Ingrediente ing = inventarioServicio.buscarIngredientePorId(id);
            if (ing != null) {
                int op = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar el ingrediente " + ing.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (op == JOptionPane.YES_OPTION) {
                    if (inventarioServicio.eliminarIngrediente(ing)) {
                        cargarIngredientesEnTabla();
                        JOptionPane.showMessageDialog(this, "Ingrediente eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar ingrediente.");
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, " Selecciona un ingrediente para eliminar .");
        }
    }

    private void consultarIngrediente() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            int id = (int) model.getValueAt(fila, 0);
            Ingrediente ing = inventarioServicio.buscarIngredientePorId(id);
            if (ing != null) {
                JTextArea taDescripcion = new JTextArea(ing.getDescripcion() != null ? ing.getDescripcion() : "");
                taDescripcion.setWrapStyleWord(true);
                taDescripcion.setLineWrap(true);
                taDescripcion.setEditable(false);
                taDescripcion.setBackground(new Color(255, 253, 210));
                taDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
                taDescripcion.setRows(6);

                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
                panel.add(new JLabel("ID:"), gbc); gbc.gridx++;
                panel.add(new JLabel(String.valueOf(ing.getId())), gbc);

                gbc.gridx = 0; gbc.gridy++;
                panel.add(new JLabel("Nombre:"), gbc); gbc.gridx++;
                panel.add(new JLabel(ing.getNombre()), gbc);

                gbc.gridx = 0; gbc.gridy++;
                panel.add(new JLabel("Cantidad:"), gbc); gbc.gridx++;
                panel.add(new JLabel(String.valueOf(ing.getCantidadPorcion())), gbc);

                gbc.gridx = 0; gbc.gridy++;
                panel.add(new JLabel("Unidad de Medida:"), gbc); gbc.gridx++;
                panel.add(new JLabel(ing.getUnidadMedida() != null ? ing.getUnidadMedida().name() : ""), gbc);

                gbc.gridx = 0; gbc.gridy++;
                panel.add(new JLabel("Stock Actual:"), gbc); gbc.gridx++;
                panel.add(new JLabel(String.valueOf(ing.getStockActual())), gbc);

                gbc.gridx = 0; gbc.gridy++;
                panel.add(new JLabel("Precio Unitario:"), gbc); gbc.gridx++;
                panel.add(new JLabel(String.valueOf(ing.getPrecioUnitario())), gbc);

                gbc.gridx = 0; gbc.gridy++;
                gbc.gridwidth = 2;
                panel.add(new JLabel("Descripción:"), gbc);

                gbc.gridy++;
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                JScrollPane scrollDesc = new JScrollPane(taDescripcion);
                scrollDesc.setPreferredSize(new Dimension(400, 120));
                panel.add(scrollDesc, gbc);

                JOptionPane.showMessageDialog(this, panel, "Consulta de Ingrediente", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un ingrediente para consultar.");
        }
    }

    private Ingrediente mostrarDialogoIngrediente(Ingrediente original) {
        Ingrediente resultado = null;
        boolean datosValidos = false;

        JTextField tfNombre = new JTextField(original != null ? original.getNombre() : "");
        JTextArea taDescripcion = new JTextArea(original != null ? original.getDescripcion() : "", 5, 25);
        taDescripcion.setLineWrap(true);
        taDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(taDescripcion);
        scrollDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDescripcion.setPreferredSize(new Dimension(250, 80));
        JTextField tfCantidad = new JTextField(original != null ? String.valueOf(original.getCantidadPorcion()) : "");
        UnidadMedidaEnum[] opcionesUnidad = UnidadMedidaEnum.values();
        JComboBox<UnidadMedidaEnum> cbUnidad = new JComboBox<>(opcionesUnidad);
        if (original != null && original.getUnidadMedida() != null)
            cbUnidad.setSelectedItem(original.getUnidadMedida());
        JTextField tfStock = new JTextField(original != null ? String.valueOf(original.getStockActual()) : "");
        JTextField tfPrecio = new JTextField(original != null ? String.valueOf(original.getPrecioUnitario()) : "");

        while (!datosValidos) {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            panel.add(new JLabel("Nombre:"), gbc);
            gbc.gridx++;
            panel.add(tfNombre, gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Descripción:"), gbc);
            gbc.gridx++;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            panel.add(scrollDescripcion, gbc);
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Cantidad:"), gbc);
            gbc.gridx++;
            panel.add(tfCantidad, gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Unidad de Medida:"), gbc);
            gbc.gridx++;
            panel.add(cbUnidad, gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Stock Actual:"), gbc);
            gbc.gridx++;
            panel.add(tfStock, gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Precio Unitario:"), gbc);
            gbc.gridx++;
            panel.add(tfPrecio, gbc);

            int res = JOptionPane.showConfirmDialog(this, panel, original == null ? "Agregar Ingrediente" : "Editar Ingrediente", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION) {
                return null;
            }
            try {
                Ingrediente ing = new Ingrediente();
                ing.setNombre(tfNombre.getText().trim());
                ing.setDescripcion(taDescripcion.getText().trim());
                ing.setCantidadPorcion(Double.parseDouble(tfCantidad.getText().trim()));
                ing.setUnidadMedida((UnidadMedidaEnum) cbUnidad.getSelectedItem());
                ing.setStockActual(Double.parseDouble(tfStock.getText().trim()));
                ing.setPrecioUnitario(Double.parseDouble(tfPrecio.getText().trim()));
                if (original != null) ing.setId(original.getId());

                String error = inventarioServicio.validarIngrediente(ing);
                if (error != null) {
                    JOptionPane.showMessageDialog(this, error);
                } else {
                    resultado = ing;
                    datosValidos = true;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifica los campos numéricos (Cantidad, Stock, Precio).");
            }
        }
        return resultado;
    }
}