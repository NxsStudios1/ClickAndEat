package org.clickandeat.vista.ventana.adminSwing;

import org.clickandeat.funciones.administracion.RespuestaComentarioServicio;
import org.clickandeat.funciones.inicioSesion.UsuarioServicio;
import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.RespuestaComentarioDao;
import org.clickandeat.modelo.entidades.comentario.Comentario;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;
import org.clickandeat.modelo.entidades.sesion.Usuario;
import org.clickandeat.vista.ventana.adminSwing.menu.MenuAdministracionSwing;
import org.clickandeat.vista.ventana.inicioSwing.InicioSesionSwing;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuComentariosAdministradorSwing extends JFrame {
    private static final String FUENTE_BONITA = "Comic Sans MS";
    private final JPanel panelComentarios;
    private final RespuestaComentarioServicio respuestaComentarioServicio;
    private final RespuestaComentarioDao respuestaComentarioDao;
    private final Usuario usuario;
    private final UsuarioServicio usuarioServicio;
    private final JFrame menuAdministradorSwing;

    private JPanel panelComentarioSeleccionado = null;
    private Comentario comentarioSeleccionado = null;
    private final Color COLOR_SELECCIONADO = new Color(253, 159, 119);
    private final Color COLOR_DESELECCIONADO = new Color(190, 235, 255);

    public MenuComentariosAdministradorSwing(Usuario usuario, UsuarioServicio usuarioServicio, JFrame menuAdministradorSwing) {
        this.usuario = usuario;
        this.usuarioServicio = usuarioServicio;
        this.menuAdministradorSwing = menuAdministradorSwing;
        this.respuestaComentarioDao = new RespuestaComentarioDao();
        this.respuestaComentarioServicio = new RespuestaComentarioServicio(respuestaComentarioDao);

        setTitle("Administracion Comentarios - AndyBurger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // === PANEL IZQUIERDO (SIDEBAR) ===
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(255, 162, 130));
        panelIzquierdo.setPreferredSize(new Dimension(270, 830));
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

        // Panel de botones menú
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] opciones = {"Inventario", "Pedidos", "Comentarios", "Administración Menu"};
        JButton btnInventario = null;
        JButton btnComentarios = null;
        JButton btnAdministracionMenu = null;
        for (int i=0; i<opciones.length; i++) {
            JButton btn = new JButton(opciones[i]);
            btn.setFont(new Font(FUENTE_BONITA, Font.BOLD, 24));
            btn.setBackground(new Color(255, 162, 130));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLACK));
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            btn.setPreferredSize(new Dimension(240, 48));
            gbc.gridy = i;
            panelBotones.add(btn, gbc);

            if (opciones[i].equals("Inventario")) {
                btnInventario = btn;
            }
            if (opciones[i].equals("Comentarios")) {
                btnComentarios = btn;
                btn.setEnabled(false);
            }
            if (opciones[i].equals("Administración Menu")) {
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
        panelUsuario.setPreferredSize(new Dimension(270, 120));

        ImageIcon iconoUser = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoUser = new JLabel(new ImageIcon(iconoUser.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
        lblIconoUser.setBounds(28, 34, 64, 64);
        panelUsuario.add(lblIconoUser);

        JLabel lblAdmin = new JLabel(usuario != null ? usuario.getNombre() : "Administrador");
        lblAdmin.setFont(new Font(FUENTE_BONITA, Font.BOLD, 13));
        lblAdmin.setBounds(110, 60, 150, 30);
        panelUsuario.add(lblAdmin);

        ImageIcon iconoSalir = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/salir.png"));
        JButton btnSalir = new JButton(new ImageIcon(iconoSalir.getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH)));
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
                new InicioSesionSwing(usuarioServicio, null).setVisible(true);
            }
        });

        // Acción Inventario
        if (btnInventario != null) {
            btnInventario.addActionListener(e -> {
                this.setVisible(false);
                new MenuInventarioSwing(usuario, usuarioServicio, menuAdministradorSwing).setVisible(true);
            });
        }

        // Acción Administración Menu
        if (btnAdministracionMenu != null) {
            btnAdministracionMenu.addActionListener(e -> {
                this.setVisible(false);
                new MenuAdministracionSwing(usuario, usuarioServicio, this).setVisible(true);
            });
        }

        JPanel panelDerecho = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(246, 227, 203));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JPanel panelSuperior = new JPanel(null) {
            @Override
            public boolean isOpaque() { return true; }
        };
        panelSuperior.setBackground(new Color(181, 224, 202));
        panelSuperior.setPreferredSize(new Dimension(0, 48));

        JLabel lblPanel = new JLabel("Administración Comentarios Clientes");
        lblPanel.setFont(new Font(FUENTE_BONITA, Font.BOLD, 28));
        lblPanel.setForeground(Color.BLACK);
        lblPanel.setBounds(panelSuperior.getPreferredSize().width - 520, 8, 500, 32);
        panelSuperior.add(lblPanel);

        panelSuperior.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                lblPanel.setBounds(panelSuperior.getWidth() - 520, 8, 500, 32);
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

        JLabel lblLogoCentral = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        lblLogoCentral.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogoCentral.setBorder(BorderFactory.createEmptyBorder(12,0,8,0));
        panelCentral.add(lblLogoCentral, BorderLayout.NORTH);

        panelCentral.add(scrollComentarios, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel(new GridBagLayout());
        panelBoton.setBackground(new Color(255, 245, 180));
        panelBoton.setPreferredSize(new Dimension(0, 70));
        JButton btnResponder = new JButton("Responder Comentario");
        btnResponder.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnResponder.setBackground(new Color(130, 190, 255));
        btnResponder.setForeground(Color.BLACK);
        btnResponder.setFocusPainted(false);
        btnResponder.setPreferredSize(new Dimension(260, 40));

        JButton btnEliminar = new JButton("Eliminar Comentario");
        btnEliminar.setFont(new Font(FUENTE_BONITA, Font.BOLD, 20));
        btnEliminar.setBackground(new Color(255, 148, 148));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(260, 40));

        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0; gbcBtn.gridy = 0; gbcBtn.insets = new Insets(0, 8, 0, 8);
        panelBoton.add(btnResponder, gbcBtn);
        gbcBtn.gridx = 1;
        panelBoton.add(btnEliminar, gbcBtn);

        panelDerecho.add(panelCentral, BorderLayout.CENTER);
        panelDerecho.add(panelBoton, BorderLayout.SOUTH);

        panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        panelPrincipal.add(panelDerecho, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        recargarComentarios();

        // Acción responder comentario usando el diseño personalizado
        btnResponder.addActionListener(e -> responderComentario());

        // Acción eliminar comentario
        btnEliminar.addActionListener(e -> eliminarComentario());
    }

    private void recargarComentarios() {
        panelComentarios.removeAll();
        List<Comentario> lista = respuestaComentarioDao.obtenerComentariosSinFiltrar();
        comentarioSeleccionado = null;
        panelComentarioSeleccionado = null;

        if (lista.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay comentarios de clientes.");
            lblVacio.setFont(new Font(FUENTE_BONITA, Font.ITALIC, 22));
            lblVacio.setHorizontalAlignment(SwingConstants.CENTER);
            panelComentarios.add(Box.createVerticalStrut(40));

            JPanel centrarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            centrarPanel.setOpaque(false);
            centrarPanel.add(lblVacio);
            panelComentarios.add(centrarPanel);
        } else {
            for (Comentario c : lista) {
                panelComentarios.add(Box.createVerticalStrut(40));
                JPanel centrarPanel = new JPanel();
                centrarPanel.setOpaque(false);
                centrarPanel.setLayout(new BoxLayout(centrarPanel, BoxLayout.X_AXIS));
                centrarPanel.add(Box.createHorizontalGlue());
                JPanel panel = crearPanelComentario(c);
                centrarPanel.add(panel);
                centrarPanel.add(Box.createHorizontalGlue());
                panelComentarios.add(centrarPanel);

                panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                panel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        comentarioSeleccionado = c;

                        if (panelComentarioSeleccionado != null) {
                            panelComentarioSeleccionado.setBackground(COLOR_DESELECCIONADO);
                            panelComentarioSeleccionado.repaint();
                        }
                        panel.setBackground(COLOR_SELECCIONADO);
                        panel.repaint();

                        panelComentarioSeleccionado = panel;
                    }
                });
            }
        }
        panelComentarios.add(Box.createVerticalGlue());
        panelComentarios.revalidate();
        panelComentarios.repaint();
    }

    private JPanel crearPanelComentario(Comentario comentario) {
        JPanel recuadroComentario = new JPanel();
        recuadroComentario.setLayout(new BoxLayout(recuadroComentario, BoxLayout.Y_AXIS));
        recuadroComentario.setBackground(COLOR_DESELECCIONADO);
        recuadroComentario.setOpaque(true);
        recuadroComentario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(18, 18, 18, 18),
                BorderFactory.createLineBorder(new Color(190, 235, 255), 30, true)
        ));
        recuadroComentario.setMaximumSize(new Dimension(1000, 2500));
        recuadroComentario.setAlignmentX(Component.LEFT_ALIGNMENT);

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

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);

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
        fila2.add(Box.createHorizontalStrut(80), BorderLayout.WEST);
        fila2.add(panelDatos, BorderLayout.CENTER);

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

        JPanel fila = new JPanel(new BorderLayout());
        fila.setOpaque(false);

        JPanel izq = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        izq.setOpaque(false);
        ImageIcon iconoAdmin = new ImageIcon(getClass().getResource("/recursosGraficos/iconos/user.png"));
        JLabel lblIconoAdmin = new JLabel(new ImageIcon(iconoAdmin.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH)));

        String nombreAdmin = respuesta.getAdministrador() != null && respuesta.getAdministrador().getNombre() != null ?
                respuesta.getAdministrador().getNombre() : "Administrador";
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

    // RESPONDER COMENTARIO: diálogo personalizado con fondo amarillo, título naranja y botones adentro
    private void responderComentario() {
        if (comentarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un comentario para responder.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Crear diálogo modal
        JDialog dialog = new JDialog(this, "Responder Comentario - AndyBurger", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(600, 450);
        dialog.setLocationRelativeTo(this);

        // Panel fondo amarillo claro
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(252, 236, 142));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelFondo.setLayout(new BoxLayout(panelFondo, BoxLayout.Y_AXIS));
        panelFondo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel título naranja
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(255, 186, 79));
        panelTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        panelTitulo.setPreferredSize(new Dimension(0, 55));
        panelTitulo.setLayout(new GridBagLayout());
        JLabel lblTitulo = new JLabel("Respuesta a Comentario");
        lblTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
        panelTitulo.add(lblTitulo);

        // Panel blanco para JTextArea
        JPanel panelBlanco = new JPanel(new BorderLayout());
        panelBlanco.setBackground(Color.WHITE);
        panelBlanco.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBlanco.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        JTextArea areaRespuesta = new JTextArea(7, 34);
        areaRespuesta.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
        areaRespuesta.setLineWrap(true);
        areaRespuesta.setWrapStyleWord(true);
        JScrollPane scrollArea = new JScrollPane(areaRespuesta);
        panelBlanco.add(scrollArea, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 8));
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");

        btnOK.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        btnCancel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));

        panelBotones.add(btnOK);
        panelBotones.add(btnCancel);

        // Añadir todo al fondo
        panelFondo.add(panelTitulo);
        panelFondo.add(Box.createVerticalStrut(24));
        panelFondo.add(panelBlanco);
        panelFondo.add(Box.createVerticalStrut(30));
        panelFondo.add(panelBotones);

        dialog.setContentPane(panelFondo);

        // Acción OK
        btnOK.addActionListener(e -> {
            String contenidoRespuesta = areaRespuesta.getText().trim();
            if (contenidoRespuesta.isBlank()) {
                JOptionPane.showMessageDialog(dialog, "El contenido no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            RespuestaComentario respuesta = new RespuestaComentario();
            respuesta.setComentario(comentarioSeleccionado);
            respuesta.setAdministrador(usuario);
            respuesta.setFechaRespuesta(java.time.LocalDateTime.now());
            respuesta.setContenido(contenidoRespuesta);

            String mensajeError = respuestaComentarioServicio.validarYGuardar(respuesta);
            if (mensajeError == null) {
                JOptionPane.showMessageDialog(dialog, "Respuesta guardada correctamente.");
                dialog.dispose();
                recargarComentarios();
            } else {
                JOptionPane.showMessageDialog(dialog, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción Cancel
        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void eliminarComentario() {
        if (comentarioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un comentario para eliminar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int resultado = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar este comentario?", "Eliminar Comentario",
                JOptionPane.YES_NO_OPTION);
        if (resultado == JOptionPane.YES_OPTION) {
            respuestaComentarioDao.eliminarPorComentarioId(comentarioSeleccionado.getId());
            respuestaComentarioDao.eliminarComentarioPorId(comentarioSeleccionado.getId());
            JOptionPane.showMessageDialog(this, "Comentario eliminado correctamente.");
            recargarComentarios();
        }
    }
}