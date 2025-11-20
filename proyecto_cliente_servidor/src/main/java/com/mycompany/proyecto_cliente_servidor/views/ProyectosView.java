package com.mycompany.proyecto_cliente_servidor.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ProyectosView extends JPanel {

    public JTable tablaProyectos;
    public JButton btnCrearProyecto;
    public JButton btnEliminarProyecto;
    public JComboBox<String> comboFiltro;

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

        comboFiltro.setFont(new Font("Segoe UI", 14, Font.PLAIN));
        comboFiltro.setBackground(Color.WHITE);
        comboFiltro.setBorder(BorderFactory.createLineBorder(new Color(0xD0D7E1)));

        btnCrearProyecto = new JButton("Crear Proyecto");
        btnCrearProyecto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCrearProyecto.setBackground(new Color(0x4A90E2));
        btnCrearProyecto.setForeground(Color.WHITE);
        btnCrearProyecto.setFocusPainted(false);
        btnCrearProyecto.setBorderPainted(false);
        btnCrearProyecto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEliminarProyecto = new JButton("Eliminar");
        btnEliminarProyecto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEliminarProyecto.setBackground(new Color(0xE94E77));
        btnEliminarProyecto.setForeground(Color.WHITE);
        btnEliminarProyecto.setFocusPainted(false);
        btnEliminarProyecto.setBorderPainted(false);
        btnEliminarProyecto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelSuperior.add(lblTitulo);
        panelSuperior.add(comboFiltro);
        panelSuperior.add(btnCrearProyecto);
        panelSuperior.add(btnEliminarProyecto);

        add(panelSuperior, BorderLayout.NORTH);

        // ======= TABLA =======
        String[] columnas = {"ID", "Nombre", "Descripción", "Estado"};

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaProyectos = new JTable(modelo);

        tablaProyectos.setRowHeight(28);
        tablaProyectos.setShowHorizontalLines(false);
        tablaProyectos.setSelectionBackground(new Color(0x4A90E2));
        tablaProyectos.setGridColor(new Color(0xD0D7E1));

        JScrollPane scrollTabla = new JScrollPane(tablaProyectos);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        add(scrollTabla, BorderLayout.CENTER);
    }
}

