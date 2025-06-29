package org.clickandeat.vista.ventana.adminSwing.administracion;

import org.clickandeat.modelo.entidades.inventario.Promocion;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromocionTableModel extends AbstractTableModel {
    private final String[] columnas = {"ID", "Nombre", "Descripción", "Fecha Inicio", "Fecha Fin", "Precio", "Productos", "Disponible"};
    private List<Promocion> promociones = new ArrayList<>();
    private List<Promocion> filtradas = new ArrayList<>();

    public void setPromociones(List<Promocion> lista) {
        promociones = lista != null ? new ArrayList<>(lista) : new ArrayList<>();
        filtradas = new ArrayList<>(promociones);
        fireTableDataChanged();
    }

    public void filtrar(String texto) {
        filtradas.clear();
        for (Promocion p : promociones) {
            if ((p.getNombre() != null && p.getNombre().toLowerCase().contains(texto.toLowerCase())) ||
                    (p.getId() != null && String.valueOf(p.getId()).contains(texto)) ||
                    (p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(texto.toLowerCase()))) {
                filtradas.add(p);
            }
        }
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return filtradas.size(); }
    @Override public int getColumnCount() { return columnas.length; }
    @Override public String getColumnName(int col) { return columnas[col]; }

    @Override public Object getValueAt(int row, int col) {
        Promocion p = filtradas.get(row);
        switch (col) {
            case 0: return p.getId();
            case 1: return p.getNombre();
            case 2: return p.getDescripcion();
            case 3: return p.getFechaInicio();
            case 4: return p.getFechaFin();
            case 5: return String.format("$%.2f", p.getPrecioTotalConDescuento());
            case 6:
                if (p.getProductos() == null) return "";
                return p.getProductos().stream()
                        .map(pp -> pp.getProducto().getNombre() + " (x" + pp.getCantidadProducto() + ")")
                        .collect(Collectors.joining(", "));
            case 7: // disponible
                boolean vigente = !p.getFechaFin().isBefore(LocalDate.now());
                boolean productosDisponibles = p.getProductos() != null && !p.getProductos().isEmpty()
                        && p.getProductos().stream().allMatch(pp ->
                        pp.getProducto() != null && Boolean.TRUE.equals(pp.getProducto().getDisponible()));
                boolean disponible = vigente && productosDisponibles;
                return disponible ? "Sí" : "No";
            default: return "";
        }
    }

    public Promocion getPromocionAt(int row) {
        if (row >= 0 && row < filtradas.size()) return filtradas.get(row);
        return null;
    }

    public void actualizarLista(List<Promocion> nueva) {
        setPromociones(nueva);
    }
}