package com.mycompany.proyecto_cliente_servidor.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SprintView extends JPanel {

    public JTable tablaSprints;
    public JButton btnCrearSprint;
    public JButton btnAbrirKanban;
    public JComboBox<String> comboProyectos;

    public SprintView() {
        setLayout(new BorderLayout());
        setBackground(new Color(0xF5F7FA));

        // ======= PANEL SUPERIOR =======
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Sprints del Proyecto");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(0x2D2D2D));

        comboProyectos = new JComboBox<>(new String[]{
                "Seleccionar proyecto..."
        });
        comboProyectos.setFont(new Font("Segoe UI", 14, Font.PLAIN));
        comboProyectos.setBackground(Color.WHITE);
        comboProyectos.setBorder(BorderFactory.createLineBorder(new Color(0xD0D7E1)));

        btnCrearSprint = new JButton("Crear Sprint");
        btnCrearSprint.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCrearSprint.setBackground(new Color(0x4A90E2));
        btnCrearSprint.setForeground(Color.WHITE);
        btnCrearSprint.setBorderPainted(false);
        btnCrearSprint.setFocusPainted(false);
        btnCrearSprint.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAbrirKanban = new JButton("Abrir Kanban");
        btnAbrirKanban.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAbrirKanban.setBackground(new Color(0xF5A623));
        btnAbrirKanban.setForeground(Color.WHITE);
        btnAbrirKanban.setBorderPainted(false);
        btnAbrirKanban.setFocusPainted(false);
        btnAbrirKanban.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelSuperior.add(lblTitulo);
        panelSuperior.add(comboProyectos);
        panelSuperior.add(btnCrearSprint);
        panelSuperior.add(btnAbrirKanban);

        add(panelSuperior, BorderLayout.NORTH);

        // ======= TABLA =======
        String[] columnas = {"ID Sprint", "Nombre", "Fecha Inicio", "Fecha Fin"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaSprints = new JTable(modelo);
        tablaSprints.setRowHeight(28);
        tablaSprints.setSelectionBackground(new Color(0x4A90E2));
        tablaSprints.setGridColor(new Color(0xD0D7E1));

        JScrollPane scrollTabla = new JScrollPane(tablaSprints);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        add(scrollTabla, BorderLayout.CENTER);
    }
}
