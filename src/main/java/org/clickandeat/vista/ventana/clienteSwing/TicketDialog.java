package org.clickandeat.vista.ventana.clienteSwing;

import javax.swing.*;
import java.awt.*;

public class TicketDialog extends JDialog {
    public TicketDialog(Frame parent, String ticketText) {
        super(parent, "Ticket", true);

        JTextArea ta = new JTextArea(ticketText);
        ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        ta.setEditable(false);
        ta.setBackground(new Color(250, 250, 240));
        ta.setBorder(null);

        JScrollPane scroll = new JScrollPane(ta);
        scroll.setPreferredSize(new Dimension(420, 350));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        JPanel panelBtn = new JPanel();
        panelBtn.add(btnCerrar);

        panel.add(panelBtn, BorderLayout.SOUTH);
        add(panel);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
}