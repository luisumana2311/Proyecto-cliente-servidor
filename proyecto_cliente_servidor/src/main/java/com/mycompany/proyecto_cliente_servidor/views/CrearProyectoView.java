package com.mycompany.proyecto_cliente_servidor.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CrearProyectoView extends JFrame {

    public JTextField campoNombre;
    public JTextArea campoDescripcion;
    public JButton btnGuardar;
    public JButton btnCancelar;

    public CrearProyectoView() {
        setTitle("Crear Proyecto");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xF5F7FA));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        // Título
        JLabel titulo = new JLabel("Nuevo Proyecto", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(0x2D2D2D));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0xF5F7FA));
        formPanel.setLayout(new GridLayout(4, 1, 0, 10));

        JLabel lblNombre = new JLabel("Nombre del proyecto:");
        lblNombre.setFont(new Font("Segoe UI", 14, Font.PLAIN));
        lblNombre.setForeground(new Color(0x555555));

        campoNombre = new JTextField();
        campoNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setFont(new Font("Segoe UI", 14, Font.PLAIN));
        lblDescripcion.setForeground(new Color(0x555555));

        campoDescripcion = new JTextArea(5, 20);
        campoDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        JScrollPane scrollDescripcion = new JScrollPane(campoDescripcion);
        scrollDescripcion.setBorder(BorderFactory.createEmptyBorder());

        formPanel.add(lblNombre);
        formPanel.add(campoNombre);
        formPanel.add(lblDescripcion);
        formPanel.add(scrollDescripcion);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0xF5F7FA));

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(0x4A90E2));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorderPainted(false);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(0xE94E77));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorderPainted(false);

        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
