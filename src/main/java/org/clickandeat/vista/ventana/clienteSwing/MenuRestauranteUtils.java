package org.clickandeat.vista.ventana.clienteSwing;

import org.clickandeat.modelo.entidades.inventario.Producto;
import org.clickandeat.modelo.entidades.inventario.Promocion;
import org.clickandeat.modelo.entidades.pedido.DetallePedido;
import org.clickandeat.modelo.entidades.pedido.Pedido;
import org.clickandeat.modelo.entidades.pedido.TipoItemEnum;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.funciones.restaurante.PedidoServicio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuRestauranteUtils {

    public static JPanel crearTarjetaProducto(Producto producto, String fuente, List<DetallePedido> carrito, Runnable actualizarListaCarrito, JFrame parent) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(280, 340));
        tarjeta.setBackground(new Color(255, 255, 255, 235));
        tarjeta.setBorder(BorderFactory.createLineBorder(new Color(181, 224, 202), 3, true));

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icono = new ImageIcon(MenuRestauranteUtils.class.getResource("/recursosGraficos/iconos/producto.jpg"));
        lblImagen.setIcon(new ImageIcon(icono.getImage().getScaledInstance(230, 140, Image.SCALE_SMOOTH)));
        tarjeta.add(lblImagen, BorderLayout.NORTH);

        JPanel datos = new JPanel(new BorderLayout());
        datos.setOpaque(false);
        JLabel lblNombre = new JLabel(producto.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font(fuente, Font.BOLD, 20));
        datos.add(lblNombre, BorderLayout.NORTH);

        JTextArea taDescripcion = new JTextArea(producto.getDescripcion());
        taDescripcion.setLineWrap(true);
        taDescripcion.setWrapStyleWord(true);
        taDescripcion.setEditable(false);
        taDescripcion.setOpaque(false);
        taDescripcion.setFont(new Font(fuente, Font.PLAIN, 15));
        taDescripcion.setBorder(null);
        datos.add(taDescripcion, BorderLayout.CENTER);

        JLabel lblPrecio = new JLabel(String.format("$%.2f", producto.getPrecio()), SwingConstants.CENTER);
        lblPrecio.setFont(new Font(fuente, Font.BOLD, 19));
        lblPrecio.setForeground(new Color(113, 161, 100));
        datos.add(lblPrecio, BorderLayout.SOUTH);

        tarjeta.add(datos, BorderLayout.CENTER);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font(fuente, Font.BOLD, 16));
        btnAgregar.setBackground(new Color(181, 224, 202));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregarProductoAlCarrito(producto, carrito, actualizarListaCarrito));
        JPanel panelBtn = new JPanel();
        panelBtn.setOpaque(false);
        panelBtn.add(btnAgregar);
        tarjeta.add(panelBtn, BorderLayout.SOUTH);

        return tarjeta;
    }

    public static JPanel crearTarjetaPromocion(Promocion promo, String fuente, List<DetallePedido> carrito, Runnable actualizarListaCarrito, JFrame parent) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(280, 340));
        tarjeta.setBackground(new Color(255, 221, 120, 225));
        tarjeta.setBorder(BorderFactory.createLineBorder(new Color(181, 224, 202), 3, true));

        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icono = new ImageIcon(MenuRestauranteUtils.class.getResource("/recursosGraficos/iconos/promocion.jpg"));
        lblImagen.setIcon(new ImageIcon(icono.getImage().getScaledInstance(230, 140, Image.SCALE_SMOOTH)));
        tarjeta.add(lblImagen, BorderLayout.NORTH);

        JPanel datos = new JPanel(new BorderLayout());
        datos.setOpaque(false);
        JLabel lblNombre = new JLabel(promo.getNombre(), SwingConstants.CENTER);
        lblNombre.setFont(new Font(fuente, Font.BOLD, 20));
        datos.add(lblNombre, BorderLayout.NORTH);

        JTextArea taDescripcion = new JTextArea(promo.getDescripcion());
        taDescripcion.setLineWrap(true);
        taDescripcion.setWrapStyleWord(true);
        taDescripcion.setEditable(false);
        taDescripcion.setOpaque(false);
        taDescripcion.setFont(new Font(fuente, Font.PLAIN, 15));
        taDescripcion.setBorder(null);
        datos.add(taDescripcion, BorderLayout.CENTER);

        JLabel lblPrecio = new JLabel(String.format("$%.2f", promo.getPrecioTotalConDescuento()), SwingConstants.CENTER);
        lblPrecio.setFont(new Font(fuente, Font.BOLD, 19));
        lblPrecio.setForeground(new Color(113, 161, 100));
        datos.add(lblPrecio, BorderLayout.SOUTH);

        tarjeta.add(datos, BorderLayout.CENTER);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font(fuente, Font.BOLD, 16));
        btnAgregar.setBackground(new Color(255, 221, 120));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregarPromocionAlCarrito(promo, carrito, actualizarListaCarrito));
        JPanel panelBtn = new JPanel();
        panelBtn.setOpaque(false);
        panelBtn.add(btnAgregar);
        tarjeta.add(panelBtn, BorderLayout.SOUTH);

        return tarjeta;
    }

    public static JPanel crearPanelCarrito(String fuente, JLabel lblTotal, List<DetallePedido> carrito, DefaultListModel<String> listaCarritoModel, Runnable actualizarListaCarrito, PedidoServicio pedidoServicio, Usuario cliente, JFrame parent, String[] observacionesPedidoSetterGetter, Runnable limpiarObservaciones) {
        JPanel panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBackground(new Color(255, 245, 180));
        panelCarrito.setBorder(new javax.swing.border.EmptyBorder(32, 16, 32, 16));

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelTop.setBackground(new Color(255, 245, 180));

        JPanel panelTotalRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelTotalRow.setBackground(new Color(255, 245, 180));
        JLabel lblTotalText = new JLabel("Total: ");
        lblTotalText.setFont(new Font(fuente, Font.BOLD, 28));
        lblTotal.setFont(new Font(fuente, Font.BOLD, 30));
        lblTotal.setForeground(new Color(94, 141, 65));
        panelTotalRow.add(lblTotalText);
        panelTotalRow.add(lblTotal);

        JButton btnConfirmar = new JButton("Confirmar Pedido");
        btnConfirmar.setFont(new Font(fuente, Font.BOLD, 24));
        btnConfirmar.setBackground(new Color(181, 224, 202));
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.addActionListener(evt -> confirmarPedido(
                evt, carrito, listaCarritoModel, lblTotal, pedidoServicio, cliente, parent, observacionesPedidoSetterGetter, limpiarObservaciones, actualizarListaCarrito));

        JButton btnObservaciones = new JButton("Observaciones");
        btnObservaciones.setFont(new Font(fuente, Font.BOLD, 20));
        btnObservaciones.setBackground(new Color(255, 221, 120));
        btnObservaciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnObservaciones.addActionListener(e -> {
            JTextArea taObservaciones = new JTextArea(5, 28);
            taObservaciones.setFont(new Font(fuente, Font.PLAIN, 18));
            taObservaciones.setLineWrap(true);
            taObservaciones.setWrapStyleWord(true);
            taObservaciones.setText(observacionesPedidoSetterGetter[0]);
            JScrollPane scrollPane = new JScrollPane(taObservaciones);
            scrollPane.setPreferredSize(new Dimension(400, 140));
            JLabel lbl = new JLabel("Observaciones para el pedido");
            lbl.setFont(new Font(fuente, Font.BOLD, 17));
            lbl.setForeground(new Color(49, 90, 23));
            JPanel panel = new JPanel(new BorderLayout(0, 10));
            panel.add(lbl, BorderLayout.NORTH);
            panel.add(scrollPane, BorderLayout.CENTER);

            int result = JOptionPane.showOptionDialog(
                    parent,
                    panel,
                    "Observaciones",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    new String[]{"Guardar", "Cancelar"},
                    "Guardar"
            );
            if (result == JOptionPane.OK_OPTION) {
                observacionesPedidoSetterGetter[0] = taObservaciones.getText();
            }
        });

        panelTop.add(panelTotalRow);
        panelTop.add(Box.createVerticalStrut(10));
        panelTop.add(btnConfirmar);
        panelTop.add(Box.createVerticalStrut(10));
        panelTop.add(btnObservaciones);
        panelTop.add(Box.createVerticalStrut(10));

        JList<String> listaCarrito = new JList<>(listaCarritoModel);
        listaCarrito.setFont(new Font(fuente, Font.PLAIN, 18));
        listaCarrito.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listaCarrito);
        scroll.setPreferredSize(new Dimension(320, 220));
        scroll.setMaximumSize(new Dimension(320, 220));
        scroll.setMinimumSize(new Dimension(320, 80));

        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        JTextField tfCantidad = new JTextField(4);
        tfCantidad.setFont(new Font(fuente, Font.PLAIN, 16));
        JButton btnCambiarCantidad = new JButton("Cambiar cantidad");
        btnCambiarCantidad.setFont(new Font(fuente, Font.BOLD, 18));
        btnCambiarCantidad.setBackground(new Color(181, 224, 202));
        btnCambiarCantidad.addActionListener(e -> {
            int idx = listaCarrito.getSelectedIndex();
            if (idx >= 0 && !tfCantidad.getText().isEmpty()) {
                try {
                    int cantidad = Integer.parseInt(tfCantidad.getText());
                    if (cantidad > 0) {
                        carrito.get(idx).setCantidad(cantidad);
                        carrito.get(idx).setSubtotal(carrito.get(idx).getPrecioUnitario() * cantidad);
                        actualizarListaCarrito.run();
                    } else {
                        eliminarItemCarrito(idx, carrito, actualizarListaCarrito);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(parent, "Cantidad inválida.");
                }
            }
        });
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font(fuente, Font.PLAIN, 16));
        btnEliminar.setBackground(new Color(255, 162, 130));
        btnEliminar.addActionListener(e -> {
            int idx = listaCarrito.getSelectedIndex();
            if (idx >= 0) {
                eliminarItemCarrito(idx, carrito, actualizarListaCarrito);
            }
        });
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font(fuente, Font.BOLD, 15));
        panelCantidad.add(lblCantidad);
        panelCantidad.add(tfCantidad);
        panelCantidad.add(btnCambiarCantidad);
        panelCantidad.add(btnEliminar);

        panelCarrito.add(panelTop, BorderLayout.NORTH);
        panelCarrito.add(scroll, BorderLayout.CENTER);
        panelCarrito.add(panelCantidad, BorderLayout.SOUTH);

        return panelCarrito;
    }

    public static void agregarProductoAlCarrito(Producto producto, List<DetallePedido> carrito, Runnable actualizarListaCarrito) {
        for (DetallePedido detalle : carrito) {
            if (detalle.getTipoItem() == TipoItemEnum.PRODUCTO
                    && detalle.getProducto() != null
                    && detalle.getProducto().getId().equals(producto.getId())) {
                detalle.setCantidad(detalle.getCantidad() + 1);
                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                actualizarListaCarrito.run();
                return;
            }
        }
        DetallePedido detalle = new DetallePedido();
        detalle.setTipoItem(TipoItemEnum.PRODUCTO);
        detalle.setProducto(producto);
        detalle.setPromocion(null);
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio());
        carrito.add(detalle);
        actualizarListaCarrito.run();
    }

    public static void agregarPromocionAlCarrito(Promocion promo, List<DetallePedido> carrito, Runnable actualizarListaCarrito) {
        for (DetallePedido detalle : carrito) {
            if (detalle.getTipoItem() == TipoItemEnum.PROMOCION
                    && detalle.getPromocion() != null
                    && detalle.getPromocion().getId().equals(promo.getId())) {
                detalle.setCantidad(detalle.getCantidad() + 1);
                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                actualizarListaCarrito.run();
                return;
            }
        }
        DetallePedido detalle = new DetallePedido();
        detalle.setTipoItem(TipoItemEnum.PROMOCION);
        detalle.setProducto(null);
        detalle.setPromocion(promo);
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(promo.getPrecioTotalConDescuento());
        detalle.setSubtotal(promo.getPrecioTotalConDescuento());
        carrito.add(detalle);
        actualizarListaCarrito.run();
    }

    public static void eliminarItemCarrito(int idx, List<DetallePedido> carrito, Runnable actualizarListaCarrito) {
        if (idx >= 0 && idx < carrito.size()) {
            carrito.remove(idx);
            actualizarListaCarrito.run();
        }
    }

    public static void actualizarListaCarrito(List<DetallePedido> carrito, DefaultListModel<String> listaCarritoModel, JLabel lblTotal) {
        listaCarritoModel.clear();
        for (DetallePedido d : carrito) {
            String nombre = d.getTipoItem() == TipoItemEnum.PRODUCTO ?
                    (d.getProducto() != null ? d.getProducto().getNombre() : "(?)")
                    : (d.getPromocion() != null ? d.getPromocion().getNombre() : "(?)");
            listaCarritoModel.addElement(nombre + "  x" + d.getCantidad() +
                    "   $" + String.format("%.2f", d.getSubtotal()));
        }
        double total = carrito.stream().mapToDouble(DetallePedido::getSubtotal).sum();
        lblTotal.setText("$" + String.format("%.2f", total));
    }

    public static void confirmarPedido(ActionEvent evt, List<DetallePedido> carrito, DefaultListModel<String> listaCarritoModel, JLabel lblTotal, PedidoServicio pedidoServicio, Usuario cliente, JFrame parent, String[] observacionesPedidoSetterGetter, Runnable limpiarObservaciones, Runnable actualizarListaCarrito) {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "El carrito está vacío.");
            return;
        }
        Pedido pedido = new Pedido();
        pedido.setNumeroTicket(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pedido.setEstado(org.clickandeat.modelo.entidades.pedido.EstadoPedidoEnum.PENDIENTE);
        pedido.setTotal(carrito.stream().mapToDouble(DetallePedido::getSubtotal).sum());
        pedido.setFechaPedido(java.time.LocalDateTime.now());
        pedido.setObservaciones(observacionesPedidoSetterGetter[0]);
        pedido.setCliente(cliente);

        for (DetallePedido d : carrito) {
            d.setPedido(pedido);
        }
        pedido.setDetalles(new ArrayList<>(carrito));

        boolean exito = false;
        try {
            exito = pedidoServicio.guardar(pedido);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (exito) {
            // --- CENTRAR TICKET ---
            int width = 52; // Ancho de ticket
            StringBuilder ticket = new StringBuilder();
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ticket.append("=".repeat(width)).append("\n");
            ticket.append(centerText("ANDY BURGER - TICKET", width)).append("\n");
            ticket.append("=".repeat(width)).append("\n");

            ticket.append(centerText("Ticket:  " + pedido.getNumeroTicket(), width)).append("\n");
            ticket.append(centerText("Cliente: " + (cliente != null ? cliente.getNombre() : "Cliente"), width)).append("\n");
            ticket.append(centerText("Fecha:   " + pedido.getFechaPedido().format(formatter), width)).append("\n");

            ticket.append("-".repeat(width)).append("\n");
            ticket.append(centerText("DETALLE", width)).append("\n");
            ticket.append("-".repeat(width)).append("\n");

            for (DetallePedido d : carrito) {
                String nombre = d.getTipoItem() == org.clickandeat.modelo.entidades.pedido.TipoItemEnum.PRODUCTO
                        ? (d.getProducto() != null ? d.getProducto().getNombre() : "(?)")
                        : (d.getPromocion() != null ? d.getPromocion().getNombre() + " (PROMO)" : "(?)");
                String linea = String.format("%s x%-2d $ %7.2f", nombre, d.getCantidad(), d.getSubtotal());
                ticket.append(centerText(linea, width)).append("\n");
            }

            ticket.append("-".repeat(width)).append("\n");
            ticket.append(centerText("TOTAL:    $ " + String.format("%7.2f", pedido.getTotal()), width)).append("\n");

            if (pedido.getObservaciones() != null && !pedido.getObservaciones().trim().isEmpty()) {
                ticket.append("-".repeat(width)).append("\n");
                ticket.append(centerText("OBSERVACIONES:", width)).append("\n");
                // Observaciones pueden ser varias líneas, centramos cada una:
                for (String obsLine : pedido.getObservaciones().split("\n")) {
                    ticket.append(centerText(obsLine, width)).append("\n");
                }
            }
            ticket.append("=".repeat(width)).append("\n");
            ticket.append(centerText("¡Gracias por su visita!", width)).append("\n");
            ticket.append(centerText("Vuelva pronto :)", width)).append("\n");
            ticket.append("=".repeat(width)).append("\n");

            TicketDialog dialog = new TicketDialog(parent, ticket.toString());
            dialog.setVisible(true);

            carrito.clear();
            actualizarListaCarrito.run();
            limpiarObservaciones.run();
        } else {
            JOptionPane.showMessageDialog(parent, "Hubo un error al guardar el pedido.");
        }
    }

    // Función utilitaria para centrar texto en el ticket
    private static String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int left = (width - text.length()) / 2;
        int right = width - text.length() - left;
        return " ".repeat(left) + text + " ".repeat(right);
    }
    }
