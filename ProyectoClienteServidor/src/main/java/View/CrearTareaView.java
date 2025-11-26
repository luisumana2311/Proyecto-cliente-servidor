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

    public CrearTareaView() {

        setTitle("Crear Tarea");
        setSize(430, 380);
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
        formPanel.setLayout(new GridLayout(6, 1, 0, 10));

        // Campo título
        JLabel lblTitulo = new JLabel("Título de la tarea:");
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTitulo.setForeground(new Color(0x555555));

        campoTitulo = new JTextField();
        campoTitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Prioridad
        JLabel lblPrioridad = new JLabel("Prioridad:");
        lblPrioridad.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPrioridad.setForeground(new Color(0x555555));

        comboPrioridad = new JComboBox<>(new String[]{
            "Baja", "Media", "Alta"
        });
        comboPrioridad.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboPrioridad.setBackground(Color.WHITE);

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

        // Agregar al formulario
        formPanel.add(lblTitulo);
        formPanel.add(campoTitulo);
        formPanel.add(lblPrioridad);
        formPanel.add(comboPrioridad);
        formPanel.add(lblDescripcion);
        formPanel.add(scrollDescripcion);

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
