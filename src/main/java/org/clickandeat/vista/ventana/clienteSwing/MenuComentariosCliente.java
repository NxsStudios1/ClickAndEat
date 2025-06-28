package org.clickandeat.vista.ventana.clienteSwing;

import org.clickandeat.funciones.administracion.ProductoServicio;
import org.clickandeat.funciones.administracion.PromocionServicio;
import org.clickandeat.funciones.cliente.ComentarioServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.funciones.restaurante.PedidoServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.ComentarioDao;
import org.clickandeat.modelo.entidades.comentario.CategoriaComentarioEnum;
import org.clickandeat.modelo.entidades.comentario.Comentario;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuComentariosCliente extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final JPanel panelComentarios;
    private final ComentarioServicio comentarioServicio;
    private final Usuario cliente;
    private final UsuarioServicio usuarioServicio;
    private final JFrame ventanaBienvenida;

    public MenuComentariosCliente(Usuario cliente, UsuarioServicio usuarioServicio, JFrame ventanaBienvenida) {
        this.cliente = cliente;
        this.usuarioServicio = usuarioServicio;
        this.ventanaBienvenida = ventanaBienvenida;
        this.comentarioServicio = new ComentarioServicio(new ComentarioDao());

        setTitle("Comentarios AndyBurger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // PANEL IZQUIERDO (sidebar)
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(186, 240, 215));
        panelIzquierdo.setPreferredSize(new Dimension(250, 700));
        panelIzquierdo.setLayout(new BorderLayout());

        // Panel de arriba (logo + linea)
        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setOpaque(false);

        ImageIcon logo = new ImageIcon(getClass().getResource("/recursosGraficos/logos/logo_andyBurger.png"));
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogo.setIcon(new ImageIcon(logo.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH)));
        lblLogo.setBorder(BorderFactory.createEmptyBorder(8,0,0,0));
        panelArriba.add(lblLogo, BorderLayout.CENTER);

        // Línea separadora bajo logo
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.BLACK);
        separator.setPreferredSize(new Dimension(250, 3));
        panelArriba.add(separator, BorderLayout.SOUTH);

        panelIzquierdo.add(panelArriba, BorderLayout.NORTH);

        // Botones menú
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 0));
        panelBotones.setOpaque(false);

        JButton btnMenu = new JButton("Menu");
        btnMenu.setFont(new Font(FUENTE_BONITA, Font.BOLD, 28));
        btnMenu.setBackground(new Color(186, 240, 215));
        btnMenu.setFocusPainted(false);
        btnMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        btnMenu.setHorizontalAlignment(SwingConstants.CENTER);

// Acción para ingresar a MenuRestaurante
        btnMenu.addActionListener(e -> {
            ProductoServicio productoServicio = new ProductoServicio(new org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.ProductoDao());
            PromocionServicio promocionServicio = new PromocionServicio(new org.clickandeat.modelo.baseDatos.dao.implementacion.inventarioDao.PromocionDao());
            PedidoServicio pedidoServicio = new PedidoServicio(new org.clickandeat.modelo.baseDatos.dao.implementacion.pedidoDao.PedidoDao());

            MenuRestaurante menuRestaurante = new MenuRestaurante(
                    cliente,
                    productoServicio,
                    promocionServicio,
                    pedidoServicio,
                    ventanaBienvenida,
                    usuarioServicio
            );
            menuRestaurante.setVisible(true);
            this.dispose();
        });

        JButton btnComentarios = new JButton("Comentarios");
        btnComentarios.setFont(new Font(FUENTE_BONITA, Font.BOLD, 28));
        btnComentarios.setBackground(new Color(186, 240, 215));
        btnComentarios.setFocusPainted(false);
        btnComentarios.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK));
        btnComentarios.setHorizontalAlignment(SwingConstants.CENTER);
        btnComentarios.setEnabled(false);


        panelBotones.add(btnMenu);
        panelBotones.add(btnComentarios);

        panelIzquierdo.add(panelBotones, BorderLayout.CENTER);

        // Panel usuario abajo
        JPanel panelUsuario = new JPanel(null);
        panelUsuario.setBackground(new Color(186, 240, 215));
        panelUsuario.setPreferredSize(new Dimension(250, 80));

        ImageIcon iconoUser = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoUser = new JLabel(new ImageIcon(iconoUser.getImage().getScaledInstance(56, 56, Image.SCALE_SMOOTH)));
        lblIconoUser.setBounds(12, 10, 56, 56);
        panelUsuario.add(lblIconoUser);

        JLabel lblCliente = new JLabel(cliente != null ? cliente.getNombre() : "Cliente");
        lblCliente.setFont(new Font(FUENTE_BONITA, Font.BOLD, 17));
        lblCliente.setBounds(80, 28, 90, 30);
        panelUsuario.add(lblCliente);

        ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/salir.png"));
        JLabel lblSalir = new JLabel(new ImageIcon(iconoSalir.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        lblSalir.setBounds(195, 28, 30, 30);
        panelUsuario.add(lblSalir);

        JPanel panelUsuarioCont = new JPanel(new BorderLayout());
        panelUsuarioCont.setOpaque(false);
        panelUsuarioCont.add(panelUsuario, BorderLayout.CENTER);
        panelIzquierdo.add(panelUsuarioCont, BorderLayout.SOUTH);

        // PANEL DERECHO
        JPanel panelDerecho = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(246, 227, 203));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // BARRA SUPERIOR azul claro
        JPanel panelSuperior = new JPanel(null) {
            @Override
            public boolean isOpaque() { return true; }
        };
        panelSuperior.setBackground(new Color(190, 235, 255));
        panelSuperior.setPreferredSize(new Dimension(0, 48));

        JLabel lblPanel = new JLabel("Comentarios AndyBurger");
        lblPanel.setFont(new Font(FUENTE_BONITA, Font.BOLD, 28));
        lblPanel.setForeground(Color.BLACK);
        lblPanel.setBounds(panelSuperior.getPreferredSize().width - 360, 8, 360, 32);
        panelSuperior.add(lblPanel);

        panelSuperior.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lblPanel.setBounds(panelSuperior.getWidth() - 360, 8, 360, 32);
            }
        });

        panelDerecho.add(panelSuperior, BorderLayout.NORTH);

        // PANEL CENTRAL (comentarios)
        panelComentarios = new JPanel();
        panelComentarios.setOpaque(false);
        panelComentarios.setLayout(new BoxLayout(panelComentarios, BoxLayout.Y_AXIS));

        JScrollPane scrollComentarios = new JScrollPane(panelComentarios);
        scrollComentarios.setOpaque(false);
        scrollComentarios.getViewport().setOpaque(false);
        scrollComentarios.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scrollComentarios.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setOpaque(false);

        // Logo centrado arriba
        JLabel lblLogoCentral = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        lblLogoCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoCentral.setBorder(BorderFactory.createEmptyBorder(12,0,8,0));
        panelCentral.add(lblLogoCentral, BorderLayout.NORTH);

        panelCentral.add(scrollComentarios, BorderLayout.CENTER);

        // Panel botón inferior (centrado)
        JPanel panelBoton = new JPanel(new GridBagLayout());
        panelBoton.setBackground(new Color(247, 246, 210));
        panelBoton.setPreferredSize(new Dimension(0, 70));
        JButton btnAgregarComentario = new JButton("Agregar Comentario");
        btnAgregarComentario.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnAgregarComentario.setBackground(new Color(255, 148, 195));
        btnAgregarComentario.setForeground(Color.BLACK);
        btnAgregarComentario.setFocusPainted(false);
        btnAgregarComentario.setPreferredSize(new Dimension(300, 40));
        // Centrar el botón usando GridBagConstraints
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 0;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        panelBoton.add(btnAgregarComentario, gbcBtn);

        panelDerecho.add(panelCentral, BorderLayout.CENTER);
        panelDerecho.add(panelBoton, BorderLayout.SOUTH);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        // Pantalla grande
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        recargarComentarios();

        btnAgregarComentario.addActionListener(e -> agregarComentario());

        lblSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new MenuCliente(cliente, usuarioServicio, ventanaBienvenida).setVisible(true);
            }
        });
    }

    private void recargarComentarios() {
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

    private JPanel crearPanelComentario(Comentario comentario) {
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
        ImageIcon iconoUser = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
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

        // Panel de datos (Categoría, Calificación, Asunto) alineado a la derecha del icono y nombre
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

    private JPanel crearPanelRespuesta(RespuestaComentario respuesta) {
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
        ImageIcon iconoAdmin = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
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

    // NUEVO: El formulario permanece abierto si hay error de validación y conserva lo escrito
    private void agregarComentario() {
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

        boolean exito = false;
        while (!exito) {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            panel.add(new JLabel("Asunto:"), gbc);
            gbc.gridx++;
            panel.add(tfAsunto, gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Contenido:"), gbc);
            gbc.gridx++;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            panel.add(scrollContenido, gbc);
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Calificación:"), gbc);
            gbc.gridx++;
            panel.add(cbCalificacion, gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("Categoría:"), gbc);
            gbc.gridx++;
            panel.add(cbCategoria, gbc);

            int res = JOptionPane.showConfirmDialog(this, panel, "Agregar Comentario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res == JOptionPane.OK_OPTION) {
                Comentario nuevo = new Comentario();
                nuevo.setAsunto(tfAsunto.getText().trim());
                nuevo.setContenido(taContenido.getText().trim());
                nuevo.setCalificacion((Integer) cbCalificacion.getSelectedItem());
                nuevo.setCategoria((CategoriaComentarioEnum) cbCategoria.getSelectedItem());
                nuevo.setCliente(cliente);
                nuevo.setFechaComentario(java.time.LocalDateTime.now());
                String error = comentarioServicio.validarYGuardar(nuevo);
                if (error != null) {
                    JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
                    // El ciclo while hace que el formulario vuelva a mostrarse con los datos escritos
                } else {
                    JOptionPane.showMessageDialog(this, "Comentario agregado correctamente.");
                    recargarComentarios();
                    exito = true;
                }
            } else {
                exito = true; // Si cancela, sale del ciclo
            }
        }
    }
}