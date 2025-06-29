package org.clickandeat.vista.ventana.adminSwing.administracion;

import org.clickandeat.funciones.administracion.PromocionProductoServicio;
import org.clickandeat.funciones.administracion.PromocionServicio;
import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.modelo.entidades.inventario.Promocion;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelPromociones extends JPanel {
    private final PromocionServicio promocionServicio;
    private final ProductoServicio productoServicio;
    private final PromocionProductoServicio promocionProductoServicio;

    private PromocionTableModel tableModel;
    private JTextField txtBuscar;
    private JTable tabla;

    public PanelPromociones(PromocionServicio promocionServicio, ProductoServicio productoServicio, PromocionProductoServicio promocionProductoServicio) {
        this.promocionServicio = promocionServicio;
        this.productoServicio = productoServicio;
        this.promocionProductoServicio = promocionProductoServicio;

        setLayout(new BorderLayout());
        setBackground(new Color(246, 227, 203));

        JLabel lblTitulo = new JLabel("Gestión de Promociones");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(32, 0, 10, 0));

        // Barra de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        panelBusqueda.setOpaque(false);
        txtBuscar = new JTextField(18);
        txtBuscar.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);

        // Tabla
        tableModel = new PromocionTableModel();
        tableModel.setPromociones(promocionServicio.obtenerTodosConProductos());
        tabla = new JTable(tableModel);
        tabla.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        tabla.setRowHeight(36);
        tabla.getTableHeader().setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Centrado
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            ((DefaultTableCellRenderer) tabla.getTableHeader().getDefaultRenderer())
                    .setHorizontalAlignment(JLabel.CENTER);
        }

        // Ajuste de anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);   // Id
        tabla.getColumnModel().getColumn(1).setPreferredWidth(160);  // Nombre
        tabla.getColumnModel().getColumn(2).setPreferredWidth(200);  // Descripción
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);  // Fecha Inicio
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);  // Fecha Fin
        tabla.getColumnModel().getColumn(5).setPreferredWidth(65);   // Precio
        tabla.getColumnModel().getColumn(6).setPreferredWidth(220);  // Productos
        tabla.getColumnModel().getColumn(7).setPreferredWidth(60);   // Disponible

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(1100, 320));

        // Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 12));
        panelBotones.setOpaque(false);

        Font botonFont = new Font("Comic Sans MS", Font.BOLD, 20);
        Color botonBg = new Color(255, 245, 230);
        Color botonFg = new Color(70, 40, 0);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnConsulta = new JButton("Consulta");
        for (JButton boton : new JButton[]{btnAgregar, btnEditar, btnEliminar, btnConsulta}) {
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
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsulta);

        add(lblTitulo, BorderLayout.NORTH);
        add(panelBusqueda, BorderLayout.BEFORE_FIRST_LINE);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Listeners
        txtBuscar.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                tableModel.filtrar(txtBuscar.getText());
            }
        });

        btnAgregar.addActionListener(e -> {
            DialogoPromocion.mostrarDialogoAlta(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    promocionServicio, productoServicio, promocionProductoServicio, tableModel
            );
        });

        btnEditar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una promoción para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Promocion promo = tableModel.getPromocionAt(row);
            DialogoPromocion.mostrarDialogoEdicion(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    promocionServicio, productoServicio, promocionProductoServicio, tableModel, promo
            );
        });

        btnEliminar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una promoción para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Promocion promo = tableModel.getPromocionAt(row);
            int conf = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar esta promoción?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                boolean exito = promocionServicio.eliminarPromocion(promo);
                if (exito) {
                    tableModel.actualizarLista(promocionServicio.obtenerTodosConProductos());
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnConsulta.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Selecciona una promoción para consultar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Promocion promo = tableModel.getPromocionAt(row);
            DialogoPromocion.mostrarDialogoConsulta(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    promo
            );
        });
    }
}