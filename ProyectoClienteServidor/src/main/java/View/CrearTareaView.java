package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CrearTareaView extends JFrame {

    public JTextField campoTitulo;
    public JComboBox<String> comboPrioridad;
    public JTextArea campoDescripcion;
    public JButton btnGuardar;
    public JButton btnCancelar;
    public JComboBox<String> comboResponsable;
    public JComboBox<String> comboProyecto;
    public JComboBox<String> comboSprint;

    public CrearTareaView() {

        setTitle("Crear Tarea");
        setSize(480, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xF5F7FA));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Título
        JLabel titulo = new JLabel("Nueva Tarea", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(0x2D2D2D));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0xF5F7FA));
        formPanel.setLayout(new BorderLayout(0, 10));

        JPanel camposPanel = new JPanel();
        camposPanel.setBackground(new Color(0xF5F7FA));
        camposPanel.setLayout(new GridLayout(10, 1, 0, 12));
        camposPanel.setPreferredSize(new Dimension(0, 420));

        // Campo título
        JLabel lblTitulo = new JLabel("Título de la tarea:");
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTitulo.setForeground(new Color(0x555555));

        campoTitulo = new JTextField();
        campoTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Proyecto
        JLabel lblProyecto = new JLabel("Proyecto:");
        lblProyecto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblProyecto.setForeground(new Color(0x555555));

        comboProyecto = new JComboBox<>();
        comboProyecto.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboProyecto.setBackground(Color.WHITE);

        // Sprint
        JLabel lblSprint = new JLabel("Sprint:");
        lblSprint.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSprint.setForeground(new Color(0x555555));

        comboSprint = new JComboBox<>();
        comboSprint.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboSprint.setBackground(Color.WHITE);

        // Prioridad
        JLabel lblPrioridad = new JLabel("Prioridad:");
        lblPrioridad.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPrioridad.setForeground(new Color(0x555555));

        comboPrioridad = new JComboBox<>(new String[]{
            "Baja", "Media", "Alta"
        });
        comboPrioridad.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboPrioridad.setBackground(Color.WHITE);

        JLabel lblResponsable = new JLabel("Responsable:");
        lblResponsable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblResponsable.setForeground(new Color(0x555555));
        comboResponsable = new JComboBox<>();
        comboResponsable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboResponsable.setBackground(Color.WHITE);

        camposPanel.add(lblTitulo);
        camposPanel.add(campoTitulo);
        camposPanel.add(lblPrioridad);
        camposPanel.add(comboPrioridad);
        camposPanel.add(lblProyecto);
        camposPanel.add(comboProyecto);
        camposPanel.add(lblSprint);
        camposPanel.add(comboSprint);
        camposPanel.add(lblResponsable);
        camposPanel.add(comboResponsable);

        JPanel descripcionPanel = new JPanel();
        descripcionPanel.setBackground(new Color(0xF5F7FA));
        descripcionPanel.setLayout(new BorderLayout(0, 5));

        // Descripción
        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblDescripcion.setForeground(new Color(0x555555));

        campoDescripcion = new JTextArea(5, 20);
        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);
        campoDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JScrollPane scrollDescripcion = new JScrollPane(campoDescripcion);
        scrollDescripcion.setBorder(BorderFactory.createEmptyBorder());
        scrollDescripcion.setPreferredSize(new Dimension(0, 120));

        descripcionPanel.add(lblDescripcion, BorderLayout.NORTH);
        descripcionPanel.add(scrollDescripcion, BorderLayout.CENTER);

        formPanel.add(camposPanel, BorderLayout.NORTH);
        formPanel.add(descripcionPanel, BorderLayout.CENTER);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0xF5F7FA));

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0x4A90E2));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(0xE94E77));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
