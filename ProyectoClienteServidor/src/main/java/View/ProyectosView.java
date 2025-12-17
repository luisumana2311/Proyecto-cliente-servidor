package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ProyectosView extends JPanel {

    public JTable tablaProyectos;
    public JButton btnNuevo;          // <-- nombre compatible
    public JButton btnEliminar;       // <-- nombre compatible
    public JComboBox<String> comboFiltro;
    public DefaultTableModel modeloTabla;   // <-- modelo público

    public ProyectosView() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F7FA));

        // ======= PANEL SUPERIOR =======
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(0xFFFFFF));
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Proyectos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0x2D2D2D));

        comboFiltro = new JComboBox<>(new String[]{
            "Todos",
            "Activos",
            "Completados",
            "Archivados"
        });

        comboFiltro.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboFiltro.setBackground(Color.WHITE);
        comboFiltro.setBorder(BorderFactory.createLineBorder(new Color(0xD0D7E1)));

        // ---- BOTÓN CREAR PROYECTO ----
        btnNuevo = new JButton("Crear Proyecto");
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNuevo.setBackground(new Color(0x4A90E2));
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ---- BOTÓN ELIMINAR ----
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEliminar.setBackground(new Color(0xE94E77));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelSuperior.add(lblTitulo);
        panelSuperior.add(comboFiltro);
        panelSuperior.add(btnNuevo);
        panelSuperior.add(btnEliminar);

        add(panelSuperior, BorderLayout.NORTH);

        // ======= TABLA =======
        String[] columnas = {"ID", "Nombre", "Descripción", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0);  // <-- modelo público
        tablaProyectos = new JTable(modeloTabla);

        tablaProyectos.setRowHeight(28);
        tablaProyectos.setShowHorizontalLines(false);
        tablaProyectos.setSelectionBackground(new Color(0x4A90E2));
        tablaProyectos.setGridColor(new Color(0xD0D7E1));

        JScrollPane scrollTabla = new JScrollPane(tablaProyectos);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        add(scrollTabla, BorderLayout.CENTER);
    }
}
