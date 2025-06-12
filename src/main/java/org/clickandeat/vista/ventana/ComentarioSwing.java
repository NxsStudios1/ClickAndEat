package org.clickandeat.vista.ventana;

import org.clickandeat.funciones.realizacionComentario.ComentarioServicio;
import org.clickandeat.modelo.entidades.comentario.CategoriaComentarioEnum;
import org.clickandeat.modelo.entidades.comentario.Comentario;
import org.clickandeat.modelo.entidades.sesion.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class ComentarioSwing extends JFrame {

    public ComentarioSwing(Usuario cliente, ComentarioServicio comentarioServicio) {
        setTitle("Nuevo Comentario");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField tfAsunto = new JTextField();
        JTextArea taContenido = new JTextArea(4, 20);
        JScrollPane scrollContenido = new JScrollPane(taContenido);
        JSpinner spCalificacion = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        JComboBox<CategoriaComentarioEnum> cbCategoria = new JComboBox<>(CategoriaComentarioEnum.values());

        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("Asunto:"));
        add(tfAsunto);
        add(new JLabel("Contenido:"));
        add(scrollContenido);
        add(new JLabel("Calificación (1-5):"));
        add(spCalificacion);
        add(new JLabel("Categoría:"));
        add(cbCategoria);

        add(new JLabel());
        add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String asunto = tfAsunto.getText().trim();
            String contenido = taContenido.getText().trim();
            int calificacion = (int) spCalificacion.getValue();
            CategoriaComentarioEnum categoria = (CategoriaComentarioEnum) cbCategoria.getSelectedItem();

            Comentario comentario = new Comentario();
            comentario.setAsunto(asunto);
            comentario.setContenido(contenido);
            comentario.setCalificacion(calificacion);
            comentario.setCategoria(categoria);
            comentario.setFechaComentario(LocalDateTime.now());
            comentario.setCliente(cliente);

            String error = comentarioServicio.validarYGuardar(comentario);

            if (error == null) {
                JOptionPane.showMessageDialog(this, "¡Comentario guardado con éxito!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}