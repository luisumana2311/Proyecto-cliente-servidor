package com.mycompany.proyecto_cliente_servidor.views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardView extends JFrame {

    // Sidebar
    public JButton btnProyectos;
    public JButton btnActividades;
    public JButton btnKanban;
    public JButton btnChat;
    public JButton btnHistorial;
    public JButton btnCerrar;

    // Panel central donde se cargan otras vistas
    public JPanel panelContenido;

    public DashboardView() {
        setTitle("Dashboard");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        // ===== SIDEBAR LATERAL =====
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(0x2D2D2D));
        sidebar.setLayout(new GridLayout(10, 1, 0, 10));
        sidebar.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel titulo = new JLabel("MENU");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Estilo para los botones del sidebar
        btnProyectos = crearBotonSidebar("Proyectos");
        btnActividades = crearBotonSidebar("Sprints");
        btnKanban = crearBotonSidebar("Kanban Board");
        btnChat = crearBotonSidebar("Chat");
        btnHistorial = crearBotonSidebar("Historial");
        btnCerrar = crearBotonSidebar("Cerrar Sesión");
        btnCerrar.setBackground(new Color(0xE94E77));

        sidebar.add(titulo);
        sidebar.add(btnProyectos);
        sidebar.add(btnActividades);
        sidebar.add(btnKanban);
        sidebar.add(btnChat);
        sidebar.add(btnHistorial);
        sidebar.add(btnCerrar);

        add(sidebar, BorderLayout.WEST);

        // ===== PANEL DE CONTENIDO =====
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(new Color(0xF5F7FA));

        JLabel mensajeInicial = new JLabel("Bienvenido al panel principal", SwingConstants.CENTER);
        mensajeInicial.setFont(new Font("SansSerif", Font.BOLD, 30));
        mensajeInicial.setForeground(new Color(0x2D2D2D));

        panelContenido.add(mensajeInicial, BorderLayout.CENTER);

        add(panelContenido, BorderLayout.CENTER);

        // ===== CONECTAR BOTONES =====
        conectarEventos();
    }

    // ===========================
    // MÉTODO PARA CAMBIAR VISTAS
    // ===========================
    private void mostrarVista(JPanel vista) {
        panelContenido.removeAll();
        panelContenido.add(vista, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // ===========================
    // CONECTAR EVENTOS DEL MENÚ
    // ===========================
    private void conectarEventos() {

        btnProyectos.addActionListener(e -> {
            mostrarVista(new ProyectosView());
        });

        btnActividades.addActionListener(e -> {
            mostrarVista(new SprintView());
        });

        btnKanban.addActionListener(e -> {
            mostrarVista(new KanbanBoardView());
        });

        btnChat.addActionListener(e -> {
            mostrarVista(new ChatView());
        });

        btnHistorial.addActionListener(e -> {
            mostrarVista(new HistorialView());
        });

        btnCerrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Sesión cerrada correctamente.", "Salir", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
    }

    // ===========================
    // ESTILO DE BOTONES DEL MENÚ
    // ===========================
    private JButton crearBotonSidebar(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setBackground(new Color(0x4A4A4A));
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
