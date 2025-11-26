
package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HistorialView extends JPanel {

    public JTextArea areaHistorial;

    public HistorialView() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F7FA));

        // ======= TÍTULO SUPERIOR =======
        JLabel titulo = new JLabel("Historial de Actividades", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(0x2D2D2D));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));

        add(titulo, BorderLayout.NORTH);

        // ======= PANEL CENTRAL =======
        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setFont(new Font("SansSerif", Font.PLAIN, 15));
        areaHistorial.setBackground(Color.WHITE);
        areaHistorial.setLineWrap(true);
        areaHistorial.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaHistorial);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        add(scroll, BorderLayout.CENTER);
    }
}