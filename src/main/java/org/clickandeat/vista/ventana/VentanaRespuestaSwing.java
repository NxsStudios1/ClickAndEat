package org.clickandeat.vista.ventana;

import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.RespuestaComentarioDao;
import org.clickandeat.modelo.baseDatos.dao.implementacion.comentarioDao.ComentarioDao;
import org.clickandeat.modelo.entidades.comentario.Comentario;
import org.clickandeat.modelo.entidades.comentario.RespuestaComentario;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class VentanaRespuestaSwing extends JFrame {
    private JList<Comentario> listaComentarios;
    private JTextArea areaComentario;
    private JTextArea areaRespuesta;
    private JButton btnResponder;
    private Usuario administrador;
    private RespuestaComentarioDao respuestaDao = new RespuestaComentarioDao();
    private ComentarioDao comentarioDao = new ComentarioDao();

    public VentanaRespuestaSwing(Usuario administrador) {
        this.administrador = administrador;

        setTitle("Responder Comentarios");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel izquierdo: lista de comentarios
        List<Comentario> comentarios = comentarioDao.findAll();
        listaComentarios = new JList<>(comentarios.toArray(new Comentario[0]));
        add(new JScrollPane(listaComentarios), BorderLayout.WEST);

        // Panel derecho: detalle y respuesta
        JPanel panelDerecho = new JPanel(new BorderLayout());

        areaComentario = new JTextArea();
        areaComentario.setEditable(false);
        areaComentario.setLineWrap(true);
        areaComentario.setWrapStyleWord(true);
        panelDerecho.add(new JLabel("Comentario del cliente:"), BorderLayout.NORTH);
        panelDerecho.add(new JScrollPane(areaComentario), BorderLayout.CENTER);

        areaRespuesta = new JTextArea(5, 30);
        areaRespuesta.setLineWrap(true);
        areaRespuesta.setWrapStyleWord(true);

        btnResponder = new JButton("Responder");
        btnResponder.setEnabled(false);

        JPanel panelRespuesta = new JPanel(new BorderLayout());
        panelRespuesta.add(new JLabel("Respuesta del administrador:"), BorderLayout.NORTH);
        panelRespuesta.add(new JScrollPane(areaRespuesta), BorderLayout.CENTER);
        panelRespuesta.add(btnResponder, BorderLayout.SOUTH);

        panelDerecho.add(panelRespuesta, BorderLayout.SOUTH);

        add(panelDerecho, BorderLayout.CENTER);

        // Listener para seleccionar comentario
        listaComentarios.addListSelectionListener(e -> {
            Comentario comentario = listaComentarios.getSelectedValue();
            if (comentario != null) {
                mostrarDetalleComentario(comentario);
            }
        });

        btnResponder.addActionListener(e -> {
            Comentario comentario = listaComentarios.getSelectedValue();
            if (comentario != null && !areaRespuesta.getText().trim().isEmpty()) {
                RespuestaComentario respuesta = new RespuestaComentario();
                respuesta.setComentario(comentario);
                respuesta.setAdministrador(administrador);
                respuesta.setContenido(areaRespuesta.getText().trim());
                respuesta.setFechaRespuesta(LocalDateTime.now());
                respuestaDao.guardar(respuesta);
                JOptionPane.showMessageDialog(this, "Respuesta guardada.");
                mostrarDetalleComentario(comentario); // refresca la respuesta
            }
        });
    }

    private void mostrarDetalleComentario(Comentario comentario) {
        // Muestra el detalle completo del comentario del cliente
        String info = "Cliente: " + comentario.getCliente().getNombre() +
                "\nFecha: " + comentario.getFechaComentario() +
                "\n\n" + comentario.getContenido();
        areaComentario.setText(info);

        // Busca si ya hay respuesta
        RespuestaComentario respuesta = buscarRespuestaPorComentario(comentario);
        if (respuesta != null) {
            areaRespuesta.setText(respuesta.getContenido());
            areaRespuesta.setEditable(false);
            btnResponder.setEnabled(false);
        } else {
            areaRespuesta.setText("");
            areaRespuesta.setEditable(true);
            btnResponder.setEnabled(true);
        }
    }

    // Puedes optimizar esto en el DAO, aquí va ejemplo simple
    private RespuestaComentario buscarRespuestaPorComentario(Comentario comentario) {
        List<RespuestaComentario> todas = respuestaDao.findAll();
        for (RespuestaComentario r : todas) {
            if (r.getComentario().getId().equals(comentario.getId())) {
                return r;
            }
        }
        return null;
    }
}