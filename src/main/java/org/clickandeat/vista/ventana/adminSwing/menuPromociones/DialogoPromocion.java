package org.clickandeat.vista.ventana.adminSwing.menuPromociones;

import org.clickandeat.funciones.administracion.PromocionProductoServicio;
import org.clickandeat.funciones.administracion.PromocionServicio;
import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.modelo.entidades.inventario.Promocion;
import org.clickandeat.modelo.entidades.inventario.PromocionProducto;
import org.clickandeat.modelo.entidades.inventario.Producto;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class DialogoPromocion {
    // Formulario para Alta
    public static void mostrarDialogoAlta(JFrame parent,
                                          PromocionServicio promocionServicio,
                                          ProductoServicio productoServicio,
                                          PromocionProductoServicio promocionProductoServicio,
                                          PromocionTableModel tableModel) {

        mostrarDialogoFormulario(parent, promocionServicio, productoServicio, promocionProductoServicio, tableModel, null);
    }

    // Formulario para Edición
    public static void mostrarDialogoEdicion(JFrame parent,
                                             PromocionServicio promocionServicio,
                                             ProductoServicio productoServicio,
                                             PromocionProductoServicio promocionProductoServicio,
                                             PromocionTableModel tableModel,
                                             Promocion promo) {

        mostrarDialogoFormulario(parent, promocionServicio, productoServicio, promocionProductoServicio, tableModel, promo);
    }

    // Formulario general con DISEÑO UNIFORME y SCROLL
    private static void mostrarDialogoFormulario(JFrame parent,
                                                 PromocionServicio promocionServicio,
                                                 ProductoServicio productoServicio,
                                                 PromocionProductoServicio promocionProductoServicio,
                                                 PromocionTableModel tableModel,
                                                 Promocion promoEditar) {
        boolean esEdicion = promoEditar != null;

        // Fuentes y colores
        Font tituloFont = new Font("Comic Sans MS", Font.BOLD, 28);
        Font labelFont = new Font("Comic Sans MS", Font.BOLD, 18);
        Font campoFont = new Font("Comic Sans MS", Font.PLAIN, 18);
        Font botonFont = new Font("Comic Sans MS", Font.BOLD, 20);
        Color fondo = new Color(246, 227, 203);
        Color botonBg = new Color(255, 245, 230);
        Color botonFg = new Color(70, 40, 0);

        // Campos principales
        JTextField txtNombre = new JTextField(esEdicion ? promoEditar.getNombre() : "");
        JTextArea txtDescripcion = new JTextArea(esEdicion ? promoEditar.getDescripcion() : "", 3, 24);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(campoFont);

        JTextField txtPrecio = new JTextField(esEdicion ? String.valueOf(promoEditar.getPrecioTotalConDescuento()) : "");
        JTextField txtFechaInicio = new JTextField(esEdicion && promoEditar.getFechaInicio() != null ? promoEditar.getFechaInicio().toString() : "");
        JTextField txtFechaFin = new JTextField(esEdicion && promoEditar.getFechaFin() != null ? promoEditar.getFechaFin().toString() : "");

        // Productos disponibles
        List<Producto> productosDisponibles = productoServicio.obtenerTodos();
        Map<Producto, Double> cantidades = new HashMap<>();
        if (esEdicion && promoEditar.getProductos() != null) {
            for (PromocionProducto pp : promoEditar.getProductos()) {
                cantidades.put(pp.getProducto(), pp.getCantidadProducto());
            }
        }

        // Panel productos con scroll (vertical si hay muchos)
        JPanel panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(fondo);

        JLabel lblSelProd = new JLabel("Selecciona los productos para la promoción (y cantidad):");
        lblSelProd.setFont(labelFont);
        lblSelProd.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelProductos.add(lblSelProd);
        panelProductos.add(Box.createVerticalStrut(7));

        List<JCheckBox> checkboxes = new ArrayList<>();
        List<JTextField> camposCantidades = new ArrayList<>();

        for (Producto prod : productosDisponibles) {
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 2));
            fila.setBackground(fondo);
            JCheckBox cb = new JCheckBox(prod.getNombre());
            cb.setFont(campoFont);
            cb.setBackground(fondo);
            cb.setSelected(cantidades.containsKey(prod));

            JTextField txtCant = new JTextField(7);
            txtCant.setFont(campoFont);
            txtCant.setText(cantidades.containsKey(prod) ? String.valueOf(cantidades.get(prod)) : "");
            txtCant.setEnabled(cb.isSelected());

            cb.addActionListener(e -> txtCant.setEnabled(cb.isSelected()));

            fila.add(cb);
            fila.add(new JLabel("Cantidad:") {{ setFont(labelFont); }});
            fila.add(txtCant);

            checkboxes.add(cb);
            camposCantidades.add(txtCant);

            fila.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelProductos.add(fila);
        }

        // Panel principal de datos (GridBag para etiquetas/campos alineados)
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(fondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 12, 7, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        JLabel lblTitulo = new JLabel(esEdicion ? "Editar Promoción" : "Agregar Promoción");
        lblTitulo.setFont(tituloFont);
        panelCampos.add(lblTitulo, gbc);
        gbc.gridy++;

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(labelFont);
        panelCampos.add(lblNombre, gbc);
        gbc.gridx = 1;
        txtNombre.setFont(campoFont);
        panelCampos.add(txtNombre, gbc);
        gbc.gridx = 0; gbc.gridy++;

        // Descripción (area scrollable)
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(labelFont);
        panelCampos.add(lblDescripcion, gbc);
        gbc.gridx = 1;
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setPreferredSize(new Dimension(240, 60));
        panelCampos.add(scrollDesc, gbc);
        gbc.gridx = 0; gbc.gridy++;

        // Fechas y precio
        JLabel lblFechaInicio = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        lblFechaInicio.setFont(labelFont);
        panelCampos.add(lblFechaInicio, gbc);
        gbc.gridx = 1;
        txtFechaInicio.setFont(campoFont);
        panelCampos.add(txtFechaInicio, gbc);
        gbc.gridx = 0; gbc.gridy++;

        JLabel lblFechaFin = new JLabel("Fecha Fin (YYYY-MM-DD):");
        lblFechaFin.setFont(labelFont);
        panelCampos.add(lblFechaFin, gbc);
        gbc.gridx = 1;
        txtFechaFin.setFont(campoFont);
        panelCampos.add(txtFechaFin, gbc);
        gbc.gridx = 0; gbc.gridy++;

        JLabel lblPrecio = new JLabel("Precio Total Con Descuento:");
        lblPrecio.setFont(labelFont);
        panelCampos.add(lblPrecio, gbc);
        gbc.gridx = 1;
        txtPrecio.setFont(campoFont);
        panelCampos.add(txtPrecio, gbc);
        gbc.gridx = 0; gbc.gridy++;

        // Productos (en scroll si hay muchos)
        gbc.gridwidth = 2;
        panelCampos.add(panelProductos, gbc);
        gbc.gridwidth = 1;

        // Panel principal con scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(fondo);
        mainPanel.add(panelCampos);

        JScrollPane scrollPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getViewport().setBackground(fondo);

        // Botones
        JPanel panelBtns = new JPanel();
        panelBtns.setBackground(fondo);
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancelar");
        for (JButton boton : new JButton[]{btnOk, btnCancel}) {
            boton.setFont(botonFont);
            boton.setBackground(botonBg);
            boton.setForeground(botonFg);
            boton.setFocusPainted(false);
            boton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 180, 120), 2, true),
                    BorderFactory.createEmptyBorder(6, 22, 6, 22)
            ));
            boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        panelBtns.add(btnOk);
        panelBtns.add(btnCancel);

        // Armado final
        JDialog dialog = new JDialog(parent, esEdicion ? "Editar Promoción" : "Agregar Promoción", true);
        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPanel, BorderLayout.CENTER);
        dialog.add(panelBtns, BorderLayout.SOUTH);
        dialog.setSize(600, 600);
        dialog.setMinimumSize(new Dimension(500, 400));
        dialog.setLocationRelativeTo(parent);

        // Listener OK
        btnOk.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String fechaInicioStr = txtFechaInicio.getText().trim();
            String fechaFinStr = txtFechaFin.getText().trim();
            String precioStr = txtPrecio.getText().trim();

            if (nombre.isEmpty() || fechaInicioStr.isEmpty() || fechaFinStr.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Completa todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double precio;
            LocalDate fechaInicio, fechaFin;
            try {
                precio = Double.parseDouble(precioStr);
                fechaInicio = LocalDate.parse(fechaInicioStr);
                fechaFin = LocalDate.parse(fechaFinStr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Formato de fecha o precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Productos seleccionados y cantidades
            List<PromocionProducto> productosPromo = new ArrayList<>();
            for (int i = 0; i < checkboxes.size(); i++) {
                if (checkboxes.get(i).isSelected()) {
                    Producto prod = productosDisponibles.get(i);
                    double cantidad = 0;
                    try {
                        cantidad = Double.parseDouble(camposCantidades.get(i).getText().trim());
                    } catch (Exception ex) {
                    }
                    if (cantidad <= 0) {
                        JOptionPane.showMessageDialog(dialog, "Cantidad inválida para producto: " + prod.getNombre(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    PromocionProducto pp = new PromocionProducto();
                    pp.setProducto(prod);
                    pp.setCantidadProducto(cantidad);
                    productosPromo.add(pp);
                }
            }
            if (productosPromo.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Selecciona al menos un producto para la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean exito;
            if (esEdicion) {
                Promocion promo = promoEditar;
                promo.setNombre(nombre);
                promo.setDescripcion(descripcion);
                promo.setFechaInicio(fechaInicio);
                promo.setFechaFin(fechaFin);
                promo.setPrecioTotalConDescuento(precio);
                promo.setProductos(productosPromo);
                exito = promocionServicio.actualizarPromocion(promo, productosPromo, promocionProductoServicio);
            } else {
                Promocion promo = new Promocion();
                promo.setNombre(nombre);
                promo.setDescripcion(descripcion);
                promo.setFechaInicio(fechaInicio);
                promo.setFechaFin(fechaFin);
                promo.setPrecioTotalConDescuento(precio);
                promo.setActivo(true);
                promo.setProductos(productosPromo);
                exito = promocionServicio.guardarPromocion(promo, productosPromo, promocionProductoServicio);
            }
            if (exito) {
                tableModel.actualizarLista(promocionServicio.obtenerTodosConProductos());
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Error al guardar la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // ----------- Consulta, estilo uniforme a otros menús -----------

    public static void mostrarDialogoConsulta(JFrame parent, Promocion promocion) {
        JDialog dialog = new JDialog(parent, "Consulta de Promoción", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());

        // Panel de datos principales
        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setBackground(new Color(246, 227, 203));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 10, 7, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Etiquetas y valores
        JLabel lblId = new JLabel("ID:");
        JLabel lblIdValor = new JLabel(String.valueOf(promocion.getId()));
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblNombreValor = new JLabel(promocion.getNombre());
        JLabel lblDescripcion = new JLabel("Descripción:");
        JTextArea areaDescripcion = new JTextArea(promocion.getDescripcion());
        areaDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setEditable(false);
        areaDescripcion.setOpaque(false);
        areaDescripcion.setBorder(null);

        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        JLabel lblFechaInicioValor = new JLabel(promocion.getFechaInicio() != null ? promocion.getFechaInicio().toString() : "");
        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        JLabel lblFechaFinValor = new JLabel(promocion.getFechaFin() != null ? promocion.getFechaFin().toString() : "");
        JLabel lblPrecio = new JLabel("Precio:");
        JLabel lblPrecioValor = new JLabel("$" + promocion.getPrecioTotalConDescuento());
        JLabel lblDisponible = new JLabel("Disponible:");
        JLabel lblDisponibleValor = new JLabel((promocion.getActivo() != null && promocion.getActivo()) ? "Sí" : "No");

        // Estilo
        Font labelFont = new Font("Comic Sans MS", Font.BOLD, 18);
        Font valueFont = new Font("Comic Sans MS", Font.PLAIN, 18);
        for (JLabel lbl : new JLabel[]{lblId, lblNombre, lblDescripcion, lblFechaInicio, lblFechaFin, lblPrecio, lblDisponible}) {
            lbl.setFont(labelFont);
        }
        for (JLabel lbl : new JLabel[]{lblIdValor, lblNombreValor, lblFechaInicioValor, lblFechaFinValor, lblPrecioValor, lblDisponibleValor}) {
            lbl.setFont(valueFont);
        }
        areaDescripcion.setFont(valueFont);

        // Agregar campos (incluye área de descripción)
        panelCampos.add(lblId, gbc); gbc.gridx++;
        panelCampos.add(lblIdValor, gbc); gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(lblNombre, gbc); gbc.gridx++;
        panelCampos.add(lblNombreValor, gbc); gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(lblDescripcion, gbc); gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelCampos.add(areaDescripcion, gbc); gbc.gridx = 0; gbc.gridy++; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        panelCampos.add(lblFechaInicio, gbc); gbc.gridx++;
        panelCampos.add(lblFechaInicioValor, gbc); gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(lblFechaFin, gbc); gbc.gridx++;
        panelCampos.add(lblFechaFinValor, gbc); gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(lblPrecio, gbc); gbc.gridx++;
        panelCampos.add(lblPrecioValor, gbc); gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(lblDisponible, gbc); gbc.gridx++;
        panelCampos.add(lblDisponibleValor, gbc); gbc.gridx = 0; gbc.gridy++;

        // Panel de productos
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBackground(new Color(246, 227, 203));
        JLabel lblProductos = new JLabel("  Productos en la promoción:");
        lblProductos.setFont(labelFont);
        JTextArea areaProductos = new JTextArea();
        areaProductos.setEditable(false);
        areaProductos.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        areaProductos.setBackground(new Color(255, 245, 230));
        StringBuilder productos = new StringBuilder();
        if (promocion.getProductos() != null && !promocion.getProductos().isEmpty()) {
            for (PromocionProducto pp : promocion.getProductos()) {
                Producto prod = pp.getProducto();
                productos.append("- ")
                        .append(prod != null ? prod.getNombre() : "Sin nombre")
                        .append(" (Cantidad: ").append(pp.getCantidadProducto()).append(")\n");
            }
        } else {
            productos.append("No hay productos en esta promoción.");
        }
        areaProductos.setText(productos.toString());
        areaProductos.setRows(Math.max(4, promocion.getProductos() != null ? promocion.getProductos().size() : 4));
        JScrollPane scrollProductos = new JScrollPane(areaProductos);

        panelProductos.add(lblProductos, BorderLayout.NORTH);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);

        // Panel principal con ambos paneles (campos + productos)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(246, 227, 203));
        mainPanel.add(panelCampos);
        mainPanel.add(Box.createVerticalStrut(14));
        mainPanel.add(panelProductos);

        // Scroll para todo el contenido, menos el botón
        JScrollPane scrollPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getViewport().setBackground(new Color(246, 227, 203));

        // Botón cerrar
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(new Color(246, 227, 203));
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        btnCerrar.setBackground(new Color(255, 245, 230));
        btnCerrar.setForeground(new Color(70, 40, 0));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 180, 120), 2, true),
                BorderFactory.createEmptyBorder(6, 22, 6, 22)
        ));
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dialog.dispose());
        panelBoton.add(btnCerrar);

        // Armado final
        dialog.add(scrollPanel, BorderLayout.CENTER);
        dialog.add(panelBoton, BorderLayout.SOUTH);

        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}