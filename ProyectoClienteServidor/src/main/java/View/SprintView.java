package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SprintView extends JPanel {

    public JTable tablaSprints;
    public JButton btnNuevo;
    public JButton btnEliminar;
    public JComboBox<String> comboProyecto;
    public DefaultTableModel modeloTabla;

    public SprintView() {

        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F7FA));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Gestión de Sprints");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        add(titulo, BorderLayout.NORTH);

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));

        comboProyecto = new JComboBox<>();
        comboProyecto.setPreferredSize(new Dimension(250, 30));

        btnNuevo = new JButton("Nuevo Sprint");
        btnEliminar = new JButton("Eliminar");

        panelTop.add(new JLabel("Proyecto:"));
        panelTop.add(comboProyecto);
        panelTop.add(btnNuevo);
        panelTop.add(btnEliminar);

        add(panelTop, BorderLayout.SOUTH);

        String[] columnas = {"ID", "Nombre", "Inicio", "Fin", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaSprints = new JTable(modeloTabla);

        add(new JScrollPane(tablaSprints), BorderLayout.CENTER);
    }
}
