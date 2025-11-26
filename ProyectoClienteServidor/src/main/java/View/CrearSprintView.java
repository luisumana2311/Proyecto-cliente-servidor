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
        setSize(430, 330);
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
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(new Color(0x2D2D2D));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Formulario
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0xF5F7FA));
        formPanel.setLayout(new GridLayout(6, 1, 0, 10));

        JLabel lblNombre = new JLabel("Nombre del sprint:");
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNombre.setForeground(new Color(0x555555));

        campoNombreSprint = new JTextField();
        campoNombreSprint.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lblInicio = new JLabel("Fecha de inicio (YYYY-MM-DD):");
        lblInicio.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblInicio.setForeground(new Color(0x555555));

        campoFechaInicio = new JTextField();
        campoFechaInicio.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lblFin = new JLabel("Fecha de fin (YYYY-MM-DD):");
        lblFin.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblFin.setForeground(new Color(0x555555));

        campoFechaFin = new JTextField();
        campoFechaFin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

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
