package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ChatView extends JPanel {

    public JTextArea areaMensajes;
    public JTextField campoMensaje;
    public JButton btnEnviar;

    public ChatView() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F7FA));

        // ======= TÍTULO SUPERIOR =======
        JLabel titulo = new JLabel("Chat del Proyecto", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(0x2D2D2D));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));

        add(titulo, BorderLayout.NORTH);

        // ======= PANEL CENTRAL (MENSAJES) =======
        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);
        areaMensajes.setFont(new Font("SansSerif", Font.PLAIN, 15));
        areaMensajes.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(areaMensajes);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(scroll, BorderLayout.CENTER);

        // ======= PANEL INFERIOR (ESCRITURA) =======
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setBackground(new Color(0xF5F7FA));
        panelInferior.setBorder(new EmptyBorder(10, 20, 20, 20));

        campoMensaje = new JTextField();
        campoMensaje.setFont(new Font("SansSerif", Font.PLAIN, 15));
        campoMensaje.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        btnEnviar = new JButton("Enviar");
        btnEnviar.setBackground(new Color(0x4A90E2));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnEnviar.setPreferredSize(new Dimension(100, 40));
        btnEnviar.setBorderPainted(false);
        btnEnviar.setFocusPainted(false);

        panelInferior.add(campoMensaje, BorderLayout.CENTER);
        panelInferior.add(btnEnviar, BorderLayout.EAST);

        add(panelInferior, BorderLayout.SOUTH);
    }
}
