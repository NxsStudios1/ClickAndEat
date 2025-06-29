package org.clickandeat.vista.ventana.clienteSwing;

import org.clickandeat.funciones.cliente.ComentarioServicio;
import org.clickandeat.modelo.entidades.comentario.CategoriaComentarioEnum;
import org.clickandeat.modelo.entidades.comentario.Comentario;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComentariosClienteUtils {
    private static final String FUENTE_BONITA = "Comic Sans MS";

    public static void recargarComentarios(JPanel panelComentarios, ComentarioServicio comentarioServicio, Usuario cliente, MenuComentario parent) {
        panelComentarios.removeAll();
        List<Comentario> lista = comentarioServicio.obtenerTodos();
        if (lista.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay comentarios aún. ¡Sé el primero en comentar!");
            lblVacio.setFont(new Font(FUENTE_BONITA, Font.ITALIC, 22));
            lblVacio.setHorizontalAlignment(SwingConstants.CENTER);
            panelComentarios.add(Box.createVerticalStrut(40));

            JPanel centrarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            centrarPanel.setOpaque(false);
            centrarPanel.add(lblVacio);
            panelComentarios.add(centrarPanel);
        } else {
            // Mostrar de más reciente a más antiguo
            for (int i = lista.size() - 1; i >= 0; i--) {
                Comentario c = lista.get(i);
                panelComentarios.add(Box.createVerticalStrut(40));
                JPanel centrarPanel = new JPanel();
                centrarPanel.setOpaque(false);
                centrarPanel.setLayout(new BoxLayout(centrarPanel, BoxLayout.X_AXIS));
                centrarPanel.add(Box.createHorizontalGlue());
                centrarPanel.add(crearPanelComentario(c));
                centrarPanel.add(Box.createHorizontalGlue());
                panelComentarios.add(centrarPanel);
            }
        }
        panelComentarios.add(Box.createVerticalGlue());
        panelComentarios.revalidate();
        panelComentarios.repaint();
    }

    public static JPanel crearPanelComentario(Comentario comentario) {
        JPanel recuadroComentario = new JPanel();
        recuadroComentario.setLayout(new BoxLayout(recuadroComentario, BoxLayout.Y_AXIS));
        recuadroComentario.setBackground(new Color(190, 235, 255));
        recuadroComentario.setOpaque(true);
        recuadroComentario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(18, 18, 18, 18),
                BorderFactory.createLineBorder(new Color(190, 235, 255), 30, true)
        ));
        recuadroComentario.setMaximumSize(new Dimension(1000, 2500));
        recuadroComentario.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Fila superior: icono, nombre, fecha
        JPanel fila1 = new JPanel(new BorderLayout());
        fila1.setOpaque(false);
        ImageIcon iconoUser = new ImageIcon(ComentariosClienteUtils.class.getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoCliente = new JLabel(new ImageIcon(iconoUser.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH)));
        JLabel lblClienteHeader = new JLabel("  " + (comentario.getCliente() != null ? comentario.getCliente().getNombre() : "Cliente"));
        lblClienteHeader.setFont(new Font(FUENTE_BONITA, Font.BOLD, 21));
        fila1.add(lblIconoCliente, BorderLayout.WEST);
        fila1.add(lblClienteHeader, BorderLayout.CENTER);

        JLabel lblFecha = new JLabel("Fecha: " + (comentario.getFechaComentario() != null ? comentario.getFechaComentario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : ""));
        lblFecha.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));
        fila1.add(lblFecha, BorderLayout.EAST);

        // Línea
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

        // Panel de datos (Categoría, Calificación, Asunto)
        JPanel panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setOpaque(false);

        String categoriaText = comentario.getCategoria() != null ? comentario.getCategoria().name() : "Sin categoría";
        JLabel lblCategoria = new JLabel("Categoría: " + categoriaText);
        lblCategoria.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 19));
        JLabel lblCalificacion = new JLabel("Calificación: " + comentario.getCalificacion());
        lblCalificacion.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 19));
        JLabel lblAsunto = new JLabel("Asunto: " + comentario.getAsunto());
        lblAsunto.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 19));
        panelDatos.add(lblCategoria);
        panelDatos.add(lblCalificacion);
        panelDatos.add(lblAsunto);

        JPanel fila2 = new JPanel(new BorderLayout());
        fila2.setOpaque(false);
        fila2.add(Box.createHorizontalStrut(80), BorderLayout.WEST); // espacio para el icono
        fila2.add(panelDatos, BorderLayout.CENTER);

        // Area de contenido debajo alineada a la izquierda
        JTextArea taContenido = new JTextArea(comentario.getContenido());
        taContenido.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 22));
        taContenido.setWrapStyleWord(true);
        taContenido.setLineWrap(true);
        taContenido.setEditable(false);
        taContenido.setOpaque(false);
        taContenido.setBorder(null);
        taContenido.setFocusable(false);
        taContenido.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollContenido = new JScrollPane(taContenido);
        scrollContenido.setBorder(null);
        scrollContenido.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollContenido.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollContenido.setPreferredSize(new Dimension(900, Math.min(taContenido.getPreferredSize().height + 32, 210)));
        scrollContenido.setOpaque(false);
        scrollContenido.getViewport().setOpaque(false);

        recuadroComentario.add(fila1);
        recuadroComentario.add(separator);
        recuadroComentario.add(fila2);
        recuadroComentario.add(Box.createVerticalStrut(24));
        recuadroComentario.add(scrollContenido);

        if (comentario.getRespuestas() != null && !comentario.getRespuestas().isEmpty()) {
            for (RespuestaComentario respuesta : comentario.getRespuestas()) {
                recuadroComentario.add(Box.createVerticalStrut(55));
                JPanel centrarRespuesta = new JPanel();
                centrarRespuesta.setOpaque(false);
                centrarRespuesta.setLayout(new BoxLayout(centrarRespuesta, BoxLayout.X_AXIS));
                centrarRespuesta.add(Box.createHorizontalGlue());
                centrarRespuesta.add(crearPanelRespuesta(respuesta));
                centrarRespuesta.add(Box.createHorizontalGlue());
                recuadroComentario.add(centrarRespuesta);
            }
        }

        return recuadroComentario;
    }

    public static JPanel crearPanelRespuesta(RespuestaComentario respuesta) {
        JPanel recuadroRespuesta = new JPanel();
        recuadroRespuesta.setLayout(new BoxLayout(recuadroRespuesta, BoxLayout.Y_AXIS));
        recuadroRespuesta.setBackground(new Color(255, 236, 136));
        recuadroRespuesta.setOpaque(true);
        recuadroRespuesta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 28, 15, 28),
                BorderFactory.createLineBorder(new Color(240, 200, 50), 9, true)
        ));
        recuadroRespuesta.setMaximumSize(new Dimension(900, 2200));
        recuadroRespuesta.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Fila superior: icono, Admin, fecha
        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izq.setOpaque(false);
        ImageIcon iconoAdmin = new ImageIcon(ComentariosClienteUtils.class.getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoAdmin = new JLabel(new ImageIcon(iconoAdmin.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH)));

        // Administrador : Nombre
        String nombreAdmin = "";
        if (respuesta.getAdministrador() != null && respuesta.getAdministrador().getNombre() != null) {
            nombreAdmin = respuesta.getAdministrador().getNombre();
        } else {
            nombreAdmin = "Administrador";
        }
        JLabel lblAdminHeader = new JLabel("  Administrador: " + nombreAdmin);
        lblAdminHeader.setFont(new Font(FUENTE_BONITA, Font.BOLD, 22));
        izq.add(lblIconoAdmin);
        izq.add(lblAdminHeader);

        JLabel lblFechaAdmin = new JLabel("Fecha: " +
                (respuesta.getFechaRespuesta() != null
                        ? respuesta.getFechaRespuesta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                        : ""));
        lblFechaAdmin.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 18));

        fila.add(izq, BorderLayout.WEST);
        fila.add(lblFechaAdmin, BorderLayout.EAST);

        JTextArea taRespuesta = new JTextArea(respuesta.getContenido());
        taRespuesta.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 20));
        taRespuesta.setWrapStyleWord(true);
        taRespuesta.setLineWrap(true);
        taRespuesta.setEditable(false);
        taRespuesta.setOpaque(false);
        taRespuesta.setBorder(null);
        taRespuesta.setFocusable(false);
        taRespuesta.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane scrollRespuesta = new JScrollPane(taRespuesta);
        scrollRespuesta.setBorder(null);
        scrollRespuesta.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollRespuesta.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollRespuesta.setPreferredSize(new Dimension(800, Math.min(taRespuesta.getPreferredSize().height + 30, 130)));
        scrollRespuesta.setOpaque(false);
        scrollRespuesta.getViewport().setOpaque(false);

        recuadroRespuesta.add(fila);
        recuadroRespuesta.add(scrollRespuesta);

        return recuadroRespuesta;
    }

    public static void agregarComentarioBonito(JFrame parent, ComentarioServicio comentarioServicio, Usuario cliente, JPanel panelComentarios, MenuComentario menuCliente) {
        JTextField tfAsunto = new JTextField();
        JTextArea taContenido = new JTextArea(5, 25);
        taContenido.setLineWrap(true);
        taContenido.setWrapStyleWord(true);
        JScrollPane scrollContenido = new JScrollPane(taContenido);
        scrollContenido.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollContenido.setPreferredSize(new Dimension(250, 80));

        Integer[] calificaciones = {1, 2, 3, 4, 5};
        JComboBox<Integer> cbCalificacion = new JComboBox<>(calificaciones);

        CategoriaComentarioEnum[] categorias = CategoriaComentarioEnum.values();
        JComboBox<CategoriaComentarioEnum> cbCategoria = new JComboBox<>(categorias);

        JDialog dialog = new JDialog((JFrame) parent, "Agregar Comentario", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(500, 430);
        dialog.setLocationRelativeTo(parent);

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(252, 236, 142));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelFondo.setLayout(new GridBagLayout());
        panelFondo.setBorder(BorderFactory.createEmptyBorder(24, 24, 12, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitulo = new JLabel("Agregar Comentario");
        lblTitulo.setFont(new Font(FUENTE_BONITA, Font.BOLD, 28));
        lblTitulo.setForeground(new Color(130, 100, 50));
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFondo.add(lblTitulo, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        gbc.gridy++;
        panelFondo.add(new JLabel("Asunto:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFondo.add(tfAsunto, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE;
        panelFondo.add(new JLabel("Contenido:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panelFondo.add(scrollContenido, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.fill = GridBagConstraints.NONE;
        panelFondo.add(new JLabel("Calificación:"), gbc);
        gbc.gridx = 1;
        panelFondo.add(cbCalificacion, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelFondo.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        panelFondo.add(cbCategoria, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 6));
        panelBotones.setOpaque(false);
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        btnOK.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 17));
        btnCancel.setFont(new Font(FUENTE_BONITA, Font.PLAIN, 17));
        panelBotones.add(btnOK);
        panelBotones.add(btnCancel);
        panelFondo.add(panelBotones, gbc);

        dialog.setContentPane(panelFondo);

        btnOK.addActionListener(e -> {
            Comentario nuevo = new Comentario();
            nuevo.setAsunto(tfAsunto.getText().trim());
            nuevo.setContenido(taContenido.getText().trim());
            nuevo.setCalificacion((Integer) cbCalificacion.getSelectedItem());
            nuevo.setCategoria((CategoriaComentarioEnum) cbCategoria.getSelectedItem());
            nuevo.setCliente(cliente);
            nuevo.setFechaComentario(java.time.LocalDateTime.now());
            String error = comentarioServicio.validarYGuardar(nuevo);
            if (error != null) {
                JOptionPane.showMessageDialog(dialog, error, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Comentario agregado correctamente.");
                dialog.dispose();
                recargarComentarios(panelComentarios, comentarioServicio, cliente, menuCliente);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}