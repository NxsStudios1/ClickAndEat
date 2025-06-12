package org.clickandeat.vista.ventana;

import org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.IngredienteDao;
import org.clickandeat.modelo.entidades.inventario.Ingrediente;
import org.clickandeat.modelo.entidades.inventario.UnidadMedidaEnum;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventarioSwing extends JFrame {
    private final IngredienteDao ingredienteDao;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public InventarioSwing() {
        ingredienteDao = new IngredienteDao();

        setTitle("Inventario de Ingredientes");
        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabla
        tableModel = new DefaultTableModel(new Object[]{
                "ID", "Nombre", "Descripción", "Cantidad/Porción", "Unidad", "Stock actual", "Precio Unitario"
        }, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarIngredientes();

        btnAgregar.addActionListener(e -> mostrarFormulario(null));
        btnEditar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = (Integer) tableModel.getValueAt(row, 0);
                Ingrediente ing = ingredienteDao.findById(id);
                mostrarFormulario(ing);
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un ingrediente para editar.");
            }
        });
        btnEliminar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Integer id = (Integer) tableModel.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar ingrediente?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Ingrediente ing = ingredienteDao.findById(id);
                    if (ing != null) {
                        ingredienteDao.eliminar(ing);
                        cargarIngredientes();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un ingrediente para eliminar.");
            }
        });
    }

    private void cargarIngredientes() {
        tableModel.setRowCount(0);
        List<Ingrediente> ingredientes = ingredienteDao.findAll();
        for (Ingrediente ing : ingredientes) {
            tableModel.addRow(new Object[]{
                    ing.getId(),
                    ing.getNombre(),
                    ing.getDescripcion(),
                    ing.getCantidadPorcion(),
                    ing.getUnidadMedida(),
                    ing.getStockActual(),
                    ing.getPrecioUnitario()
            });
        }
    }

    private void mostrarFormulario(Ingrediente ingrediente) {
        boolean esNuevo = (ingrediente == null);
        JTextField tfNombre = new JTextField(esNuevo ? "" : ingrediente.getNombre());
        JTextField tfDescripcion = new JTextField(esNuevo ? "" : ingrediente.getDescripcion());
        JSpinner spCantidadPorcion = new JSpinner(new SpinnerNumberModel(esNuevo ? 0.0 : ingrediente.getCantidadPorcion(), 0.0, 100000.0, 0.1));
        JComboBox<UnidadMedidaEnum> cbUnidad = new JComboBox<>(UnidadMedidaEnum.values());
        if (!esNuevo) cbUnidad.setSelectedItem(ingrediente.getUnidadMedida());
        JSpinner spStock = new JSpinner(new SpinnerNumberModel(esNuevo ? 0.0 : ingrediente.getStockActual(), 0.0, 100000.0, 0.1));
        JSpinner spPrecio = new JSpinner(new SpinnerNumberModel(esNuevo ? 0.0 : ingrediente.getPrecioUnitario(), 0.0, 100000.0, 0.1));

        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.add(new JLabel("Nombre:"));
        panel.add(tfNombre);
        panel.add(new JLabel("Descripción:"));
        panel.add(tfDescripcion);
        panel.add(new JLabel("Cantidad por porción:"));
        panel.add(spCantidadPorcion);
        panel.add(new JLabel("Unidad de medida:"));
        panel.add(cbUnidad);
        panel.add(new JLabel("Stock actual:"));
        panel.add(spStock);
        panel.add(new JLabel("Precio unitario:"));
        panel.add(spPrecio);

        int result = JOptionPane.showConfirmDialog(this, panel,
                esNuevo ? "Agregar ingrediente" : "Editar ingrediente", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = tfNombre.getText().trim();
            String descripcion = tfDescripcion.getText().trim();
            double cantidadPorcion = (Double) spCantidadPorcion.getValue();
            UnidadMedidaEnum unidad = (UnidadMedidaEnum) cbUnidad.getSelectedItem();
            double stock = (Double) spStock.getValue();
            double precio = (Double) spPrecio.getValue();

            if (nombre.isEmpty() || descripcion.isEmpty() || unidad == null) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }
            if (cantidadPorcion <= 0 || stock < 0 || precio < 0) {
                JOptionPane.showMessageDialog(this, "Valores numéricos deben ser positivos.");
                return;
            }

            if (esNuevo) {
                Ingrediente ing = new Ingrediente();
                ing.setNombre(nombre);
                ing.setDescripcion(descripcion);
                ing.setCantidadPorcion(cantidadPorcion);
                ing.setUnidadMedida(unidad);
                ing.setStockActual(stock);
                ing.setPrecioUnitario(precio);
                ingredienteDao.guardar(ing);
            } else {
                ingrediente.setNombre(nombre);
                ingrediente.setDescripcion(descripcion);
                ingrediente.setCantidadPorcion(cantidadPorcion);
                ingrediente.setUnidadMedida(unidad);
                ingrediente.setStockActual(stock);
                ingrediente.setPrecioUnitario(precio);
                ingredienteDao.actualizar(ingrediente);
            }
            cargarIngredientes();
        }
    }
}