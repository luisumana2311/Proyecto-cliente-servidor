package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CrearSprintView extends JFrame {

    public JTextField campoNombreSprint;
    public JTextField campoFechaInicio;
    public JTextField campoFechaFin;
    public JButton btnGuardar;
    public JButton btnCancelar;

    public CrearSprintView() {
        setTitle("Crear Sprint");
        setSize(480, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xF5F7FA));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Título
        JLabel titulo = new JLabel("Nuevo Sprint", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(new Color(0x2D2D2D));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0xF5F7FA));
        formPanel.setLayout(new GridLayout(6, 1, 0, 8));

        // ====== CAMPOS MÁS ALTOS Y ANCHOS ======
        Dimension fieldSize = new Dimension(350, 35);

        campoNombreSprint = new JTextField();
        campoNombreSprint.setPreferredSize(fieldSize);
        campoNombreSprint.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoNombreSprint.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        campoFechaInicio = new JTextField();
        campoFechaInicio.setPreferredSize(fieldSize);
        campoFechaInicio.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoFechaInicio.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        campoFechaFin = new JTextField();
        campoFechaFin.setPreferredSize(fieldSize);
        campoFechaFin.setFont(new Font("SansSerif", Font.PLAIN, 16));
        campoFechaFin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JLabel lblNombre = new JLabel("Nombre del sprint:");
        JLabel lblInicio = new JLabel("Fecha de inicio (YYYY-MM-DD):");
        JLabel lblFin = new JLabel("Fecha de fin (YYYY-MM-DD):");

        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblInicio.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lblFin.setFont(new Font("SansSerif", Font.PLAIN, 15));

        formPanel.add(lblNombre);
        formPanel.add(campoNombreSprint);
        formPanel.add(lblInicio);
        formPanel.add(campoFechaInicio);
        formPanel.add(lblFin);
        formPanel.add(campoFechaFin);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0xF5F7FA));

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0x4A90E2));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 15));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(0xE94E77));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 15));

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
