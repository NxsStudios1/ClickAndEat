package org.clickandeat.vista.ventana.adminSwing.administracion;

import org.clickandeat.funciones.administracion.CategoriaProductoServicio;
import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.modelo.entidades.inventario.CategoriaProducto;
import org.clickandeat.modelo.entidades.inventario.Producto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DialogoProducto {

    public static void dialogAgregarCategoria(JFrame parent, CategoriaProductoServicio categoriaServicio, MenuAdministracion.CategoriaTableModel categoriaTableModel) {
        JTextField txtNombre = new JTextField();

        JPanel panel = crearPanelDialogo();
        GridBagConstraints gbc = crearGridBag();
        JLabel lblTitulo = crearTitulo("Agregar Categoría");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER); // CENTRAR TITULO
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 2; gbc.insets = new Insets(24, 20, 24, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Nombre :", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        txtNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        txtNombre.setPreferredSize(new Dimension(400, 35));
        panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.insets = new Insets(36, 40, 18, 40);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(246, 227, 203));
        panel.setOpaque(false);
        mainPanel.add(panel);

        JScrollPane scrollPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getViewport().setBackground(new Color(246, 227, 203));

        JDialog dialog = new JDialog(parent, "Agregar Categoría", true);
        JPanel panelBtns = crearPanelBotones(dialog, () -> {
            String nombre = txtNombre.getText().trim();
            if (!nombre.isEmpty()) {
                CategoriaProducto nueva = new CategoriaProducto(nombre, null);
                boolean exito = categoriaServicio.guardarCategoria(nueva);
                if (exito) {
                    categoriaTableModel.actualizarLista(categoriaServicio.obtenerTodos());
                    return true;
                } else {
                    JOptionPane.showMessageDialog(parent, "Error al guardar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(parent, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        });
        panel.add(panelBtns, gbc);

        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPanel, BorderLayout.CENTER);
        dialog.add(panelBtns, BorderLayout.SOUTH);
        dialog.setSize(600, 320);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void dialogEditarCategoria(JFrame parent, CategoriaProductoServicio categoriaServicio, MenuAdministracion.CategoriaTableModel categoriaTableModel, int row) {
        CategoriaProducto cat = categoriaTableModel.getCategoriaAt(row);
        if (cat == null) {
            JOptionPane.showMessageDialog(parent, "Selecciona una categoría para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JTextField txtNombre = new JTextField(cat.getNombre());

        JPanel panel = crearPanelDialogo();
        GridBagConstraints gbc = crearGridBag();
        JLabel lblTitulo = crearTitulo("Editar Categoría");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER); // CENTRAR TITULO
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 2; gbc.insets = new Insets(24, 20, 24, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Nombre :", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        txtNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        txtNombre.setPreferredSize(new Dimension(400, 35));
        panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.insets = new Insets(36, 40, 18, 40);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(246, 227, 203));
        panel.setOpaque(false);
        mainPanel.add(panel);

        JScrollPane scrollPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getViewport().setBackground(new Color(246, 227, 203));

        JDialog dialog = new JDialog(parent, "Editar Categoría", true);
        JPanel panelBtns = crearPanelBotones(dialog, () -> {
            String nombre = txtNombre.getText().trim();
            if (!nombre.isEmpty()) {
                cat.setNombre(nombre);
                boolean exito = categoriaServicio.actualizarCategoria(cat);
                if (exito) {
                    categoriaTableModel.actualizarLista(categoriaServicio.obtenerTodos());
                    return true;
                } else {
                    JOptionPane.showMessageDialog(parent, "Error al actualizar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(parent, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        });
        panel.add(panelBtns, gbc);

        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPanel, BorderLayout.CENTER);
        dialog.add(panelBtns, BorderLayout.SOUTH);
        dialog.setSize(600, 320);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void dialogEliminarCategoria(
            JFrame parent,
            CategoriaProductoServicio categoriaServicio,
            MenuAdministracion.CategoriaTableModel categoriaTableModel,
            ProductoServicio productoServicio,
            int row
    ) {
        CategoriaProducto cat = categoriaTableModel.getCategoriaAt(row);
        if (cat == null) {
            JOptionPane.showMessageDialog(parent, "Selecciona una categoría para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int conf = JOptionPane.showConfirmDialog(
                parent,
                "¿Seguro que deseas eliminar esta categoría?",
                "¿¿ Eliminar Categoria ??", JOptionPane.YES_NO_OPTION
        );
        if (conf == JOptionPane.YES_OPTION) {
            boolean tieneProductos = productoServicio.obtenerTodos().stream()
                    .anyMatch(p -> p.getCategoria() != null && p.getCategoria().getId() == cat.getId());

            if (tieneProductos) {
                JOptionPane.showMessageDialog(
                        parent,
                        "¡No puedes eliminar la categoría porque tiene productos asociados!.\nElimina o reasigna sus productos primero.",
                        "¡¡ No se puede eliminar !!",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            boolean exito = categoriaServicio.eliminarCategoria(cat);
            if (exito) {
                categoriaTableModel.actualizarLista(categoriaServicio.obtenerTodos());
            } else {
                JOptionPane.showMessageDialog(parent, "Error al eliminar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static void dialogAgregarProducto(JFrame parent, ProductoServicio productoServicio, CategoriaProductoServicio categoriaServicio, MenuAdministracion.ProductoTableModel productoTableModel) {
        List<CategoriaProducto> categorias = categoriaServicio.obtenerTodos();
        if (categorias == null || categorias.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Primero debes agregar una categoría.", "Sin categorías", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Nombre y descripción como JTextArea para permitir scroll
        JTextArea txtNombre = new JTextArea();
        txtNombre.setLineWrap(true);
        txtNombre.setWrapStyleWord(true);
        txtNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        JScrollPane scrollNombre = new JScrollPane(txtNombre);
        scrollNombre.setPreferredSize(new Dimension(400, 45));
        scrollNombre.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150), 1, true));

        JTextArea txtDescripcion = new JTextArea();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(400, 65));
        scrollDescripcion.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150), 1, true));

        JTextField txtPrecio = new JTextField();
        txtPrecio.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        txtPrecio.setPreferredSize(new Dimension(400, 35));
        JComboBox<CategoriaProducto> cbCategoria = new JComboBox<>(categorias.toArray(new CategoriaProducto[0]));
        cbCategoria.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        cbCategoria.setPreferredSize(new Dimension(400, 35));

        JPanel panel = crearPanelDialogo();
        GridBagConstraints gbc = crearGridBag();

        // Título centrado
        JLabel lblTitulo = crearTitulo("Agregar Producto");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 2; gbc.insets = new Insets(24, 20, 24, 20);
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Nombre :", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(scrollNombre, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Descripción:", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(scrollDescripcion, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Precio:", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(txtPrecio, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Categoría:", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(cbCategoria, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.insets = new Insets(36, 40, 18, 40);

        // Scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(246, 227, 203));
        panel.setOpaque(false);
        mainPanel.add(panel);

        JScrollPane scrollPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getViewport().setBackground(new Color(246, 227, 203));

        JDialog dialog = new JDialog(parent, "Agregar Producto", true);
        JPanel panelBtns = crearPanelBotones(dialog, () -> {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            double precio = 0.0;
            try { precio = Double.parseDouble(precioStr); } catch (Exception ex) {}
            CategoriaProducto catProd = (CategoriaProducto) cbCategoria.getSelectedItem();
            if (nombre.isEmpty() || catProd == null) {
                JOptionPane.showMessageDialog(parent, "Faltan datos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            Producto nuevo = new Producto(nombre, descripcion, precio, true, catProd, null);
            boolean exito = productoServicio.guardarProducto(nuevo);
            if (exito) {
                productoTableModel.actualizarLista(productoServicio.obtenerTodos());
                return true;
            } else {
                JOptionPane.showMessageDialog(parent, "Error al guardar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        });
        panel.add(panelBtns, gbc);

        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPanel, BorderLayout.CENTER);
        dialog.add(panelBtns, BorderLayout.SOUTH);
        dialog.setSize(900, 500); // Ventana más grande
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void dialogEditarProducto(JFrame parent, ProductoServicio productoServicio, CategoriaProductoServicio categoriaServicio, MenuAdministracion.ProductoTableModel productoTableModel, int row) {
        Producto prod = productoTableModel.getProductoAt(row);
        if (prod == null) {
            JOptionPane.showMessageDialog(parent, "Selecciona un producto para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List<CategoriaProducto> categorias = categoriaServicio.obtenerTodos();

        // Nombre y descripción como JTextArea para permitir scroll
        JTextArea txtNombre = new JTextArea(prod.getNombre());
        txtNombre.setLineWrap(true);
        txtNombre.setWrapStyleWord(true);
        txtNombre.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        JScrollPane scrollNombre = new JScrollPane(txtNombre);
        scrollNombre.setPreferredSize(new Dimension(400, 45));
        scrollNombre.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150), 1, true));
        scrollNombre.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JTextArea txtDescripcion = new JTextArea(prod.getDescripcion());
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(400, 65));
        scrollDescripcion.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 150), 1, true));
        scrollDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JTextField txtPrecio = new JTextField(String.valueOf(prod.getPrecio()));
        txtPrecio.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        txtPrecio.setPreferredSize(new Dimension(400, 35));
        JComboBox<CategoriaProducto> cbCategoria = new JComboBox<>(categorias.toArray(new CategoriaProducto[0]));
        if (prod.getCategoria() != null) cbCategoria.setSelectedItem(prod.getCategoria());
        cbCategoria.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        cbCategoria.setPreferredSize(new Dimension(400, 35));

        JPanel panel = crearPanelDialogo();
        GridBagConstraints gbc = crearGridBag();

        // Título centrado
        JLabel lblTitulo = crearTitulo("Editar Producto");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0; gbc.gridx = 0; gbc.gridwidth = 2; gbc.insets = new Insets(24, 20, 24, 20);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Nombre :", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(scrollNombre, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Descripción:", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(scrollDescripcion, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Precio:", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(txtPrecio, gbc);

        gbc.gridy++; gbc.gridx = 0; gbc.insets = new Insets(18, 40, 0, 10);
        panel.add(crearLabel("Categoría:", 22), gbc);
        gbc.gridx = 1; gbc.insets = new Insets(18, 10, 0, 40);
        panel.add(cbCategoria, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2; gbc.insets = new Insets(36, 40, 18, 40);

        // Scroll
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(246, 227, 203));
        panel.setOpaque(false);
        mainPanel.add(panel);

        JScrollPane scrollPanel = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollPanel.getViewport().setBackground(new Color(246, 227, 203));

        JDialog dialog = new JDialog(parent, "Editar Producto", true);
        JPanel panelBtns = crearPanelBotones(dialog, () -> {
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            double precio = 0.0;
            try { precio = Double.parseDouble(precioStr); } catch (Exception ex) {}
            CategoriaProducto catProd = (CategoriaProducto) cbCategoria.getSelectedItem();
            if (nombre.isEmpty() || catProd == null) {
                JOptionPane.showMessageDialog(parent, "Faltan datos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            prod.setNombre(nombre);
            prod.setDescripcion(descripcion);
            prod.setPrecio(precio);
            prod.setCategoria(catProd);
            boolean exito = productoServicio.actualizarProducto(prod);
            if (exito) {
                productoTableModel.actualizarLista(productoServicio.obtenerTodos());
                return true;
            } else {
                JOptionPane.showMessageDialog(parent, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        });
        panel.add(panelBtns, gbc);

        dialog.setLayout(new BorderLayout());
        dialog.add(scrollPanel, BorderLayout.CENTER);
        dialog.add(panelBtns, BorderLayout.SOUTH);
        dialog.setSize(900, 500); // Ventana más grande
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    public static void dialogEliminarProducto(JFrame parent, ProductoServicio productoServicio, MenuAdministracion.ProductoTableModel productoTableModel, int row) {
        Producto prod = productoTableModel.getProductoAt(row);
        if (prod == null) {
            JOptionPane.showMessageDialog(parent, "Selecciona un producto para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int conf = JOptionPane.showConfirmDialog(parent, "¿Seguro que deseas eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            boolean exito = productoServicio.eliminarProducto(prod);
            if (exito) {
                productoTableModel.actualizarLista(productoServicio.obtenerTodos());
            } else {
                JOptionPane.showMessageDialog(parent, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Helpers para los diálogos
    private static JPanel crearPanelDialogo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(246, 227, 203));
        return panel;
    }
    private static GridBagConstraints crearGridBag() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(16, 20, 0, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }
    private static JLabel crearTitulo(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        return lbl;
    }
    private static JLabel crearLabel(String text, int fontSize) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Comic Sans MS", Font.BOLD, fontSize));
        return lbl;
    }
    private static JPanel crearPanelBotones(JDialog dialog, BotonDialogoCallback callbackOk) {
        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        panelBtns.setOpaque(false);
        JButton btnOk = new JButton("OK");
        btnOk.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        btnOk.setBackground(new Color(255, 245, 230));
        btnOk.setForeground(new Color(70, 40, 0));
        btnOk.setFocusPainted(false);
        btnOk.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 180, 120), 2, true),
                BorderFactory.createEmptyBorder(6, 22, 6, 22)
        ));
        btnOk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnOk.setPreferredSize(new Dimension(120, 45));
        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        btnCancel.setBackground(new Color(255, 245, 230));
        btnCancel.setForeground(new Color(70, 40, 0));
        btnCancel.setFocusPainted(false);
        btnCancel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 180, 120), 2, true),
                BorderFactory.createEmptyBorder(6, 22, 6, 22)
        ));
        btnCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCancel.setPreferredSize(new Dimension(130, 45));

        btnOk.addActionListener(e -> {
            boolean cerrar = callbackOk.onOk();
            if (cerrar && dialog != null) dialog.dispose();
        });
        btnCancel.addActionListener(e -> {
            if (dialog != null) dialog.dispose();
        });

        panelBtns.add(btnOk);
        panelBtns.add(btnCancel);

        return panelBtns;
    }

    private interface BotonDialogoCallback {
        boolean onOk();
    }
}