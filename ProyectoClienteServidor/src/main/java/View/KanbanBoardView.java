package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class KanbanBoardView extends JPanel {

    public JPanel columnaPendiente;
    public JPanel columnaProgreso;
    public JPanel columnaCompletado;
    public JButton btnNuevaTarea;
    public JComboBox<String> comboProyecto;
    public JComboBox<String> comboSprint;
    private JLabel labelTitulo;
    private JLabel labelInfo;

    public KanbanBoardView() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F7FA));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(0xF5F7FA));
        panelSuperior.setBorder(new EmptyBorder(20, 20, 10, 20));

        // ======= TÍTULO SUPERIOR =======
        labelTitulo = new JLabel("Tablero Kanban", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelTitulo.setForeground(new Color(0x2D2D2D));

        // ======= PANEL FILTROS =======
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelFiltros.setBackground(new Color(0xF5F7FA));

        JLabel lblProyecto = new JLabel("Proyecto:");
        lblProyecto.setFont(new Font("SansSerif", Font.PLAIN, 14));

        comboProyecto = new JComboBox<>();
        comboProyecto.setPreferredSize(new Dimension(200, 30));
        comboProyecto.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblSprint = new JLabel("Sprint:");
        lblSprint.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        comboSprint = new JComboBox<>();
        comboSprint.setPreferredSize(new Dimension(200, 30));
        comboSprint.setFont(new Font("SansSerif", Font.PLAIN, 14));

        panelFiltros.add(lblProyecto);
        panelFiltros.add(comboProyecto);
        panelFiltros.add(lblSprint);
        panelFiltros.add(comboSprint);

        labelInfo = new JLabel("Selecciona un proyecto y sprint", SwingConstants.CENTER);
        labelInfo.setFont(new Font("SansSerif", Font.ITALIC, 12));
        labelInfo.setForeground(new Color(0x666666));
        
        JPanel componentes = new JPanel(new BorderLayout(0, 5));
        componentes.setBackground(new Color(0x666666));
        componentes.add(labelTitulo, BorderLayout.NORTH);
        componentes.add(panelFiltros, BorderLayout.CENTER);
        componentes.add(labelInfo, BorderLayout.SOUTH);
        
        panelSuperior.add(componentes, BorderLayout.CENTER);
        add(panelSuperior, BorderLayout.NORTH);

        // ======= PANEL PRINCIPAL (3 COLUMNAS) =======
        JPanel panelColumnas = new JPanel(new GridLayout(1, 3, 15, 0));
        panelColumnas.setBorder(new EmptyBorder(10, 20, 20, 20));
        panelColumnas.setBackground(new Color(0xF5F7FA));

        // ======= COLUMNA: PENDIENTE =======
        columnaPendiente = crearColumna("Pendiente", new Color(0xE57373));
        panelColumnas.add(columnaPendiente);

        // ======= COLUMNA: EN PROGRESO =======
        columnaProgreso = crearColumna("En progreso", new Color(0x64B5F6));
        panelColumnas.add(columnaProgreso);

        // ======= COLUMNA: COMPLETADO =======
        columnaCompletado = crearColumna("Completado", new Color(0x81C784));
        panelColumnas.add(columnaCompletado);

        add(panelColumnas, BorderLayout.CENTER);

        // ======= BOTÓN PARA NUEVA TAREA =======
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(new Color(0xF5F7FA));

        btnNuevaTarea = new JButton("Nueva Tarea");
        btnNuevaTarea.setBackground(new Color(0x4A90E2));
        btnNuevaTarea.setForeground(Color.WHITE);
        btnNuevaTarea.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnNuevaTarea.setBorderPainted(false);
        btnNuevaTarea.setFocusPainted(false);
        btnNuevaTarea.setPreferredSize(new Dimension(200, 40));
        btnNuevaTarea.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelInferior.add(btnNuevaTarea);

        add(panelInferior, BorderLayout.SOUTH);
    }

    public void actualizarTitulo(String nombreProyecto, String nombreSprint) {
        labelInfo.setText("Proyecto: " + nombreProyecto + " | Sprint: " + nombreSprint);
    }

    // ======= MÉTODO PARA CREAR UNA COLUMNA =======
    private JPanel crearColumna(String titulo, Color colorHeader) {

        JPanel columna = new JPanel(new BorderLayout());
        columna.setBackground(Color.WHITE);
        columna.setBorder(BorderFactory.createLineBorder(new Color(0xD0D0D0)));

        // Header
        JLabel header = new JLabel(titulo, SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(colorHeader);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.setPreferredSize(new Dimension(0, 50));

        columna.add(header, BorderLayout.NORTH);

        // Contenedor donde irán las tarjetas
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(Color.WHITE);
        contenido.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Agregamos dentro de la columna
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(null);

        columna.add(scroll, BorderLayout.CENTER);

        return columna;
    }
}
