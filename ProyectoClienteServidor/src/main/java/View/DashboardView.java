package View;

import Controller.ProyectoController;
import Controller.SprintController;
import Model.Usuario;
import Controller.KanbanController;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardView extends JFrame {

    private Usuario usuario;

    // Sidebar
    public JButton btnProyectos;
    public JButton btnActividades;
    public JButton btnKanban;
    public JButton btnChat;
    public JButton btnHistorial;
    public JButton btnCerrar;

    // Panel central
    public JPanel panelContenido;

    public DashboardView(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Dashboard");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(true);

        // ====== SIDEBAR ======
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(0x2D2D2D));
        sidebar.setLayout(new GridLayout(10, 1, 0, 10));
        sidebar.setBorder(new EmptyBorder(40, 20, 40, 20));

        JLabel titulo = new JLabel("MENU");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        // CREAR BOTONES antes de añadir listeners
        btnProyectos = crearBotonSidebar("Proyectos");
        btnActividades = crearBotonSidebar("Sprints");
        btnKanban = crearBotonSidebar("Kanban Board");
        btnChat = crearBotonSidebar("Chat");
        btnHistorial = crearBotonSidebar("Historial");
        btnCerrar = crearBotonSidebar("Cerrar Sesión");
        btnCerrar.setBackground(new Color(0xE94E77));

        // Agregar botones al sidebar
        sidebar.add(titulo);
        sidebar.add(btnProyectos);
        sidebar.add(btnActividades);
        sidebar.add(btnKanban);
        sidebar.add(btnChat);
        sidebar.add(btnHistorial);
        sidebar.add(btnCerrar);

        add(sidebar, BorderLayout.WEST);

        // ====== PANEL CENTRAL ======
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(new Color(0xF5F7FA));

        JLabel mensajeInicial = new JLabel("Bienvenido " + usuario.getNombre(),
                SwingConstants.CENTER);
        mensajeInicial.setFont(new Font("SansSerif", Font.BOLD, 30));
        mensajeInicial.setForeground(new Color(0x2D2D2D));

        panelContenido.add(mensajeInicial, BorderLayout.CENTER);
        add(panelContenido, BorderLayout.CENTER);

        // Conectar los eventos
        conectarEventos();
    }

    public void mostrarVista(JPanel vista) {
        panelContenido.removeAll();
        panelContenido.add(vista, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    // BOTONES DEL MENÚ
    private void conectarEventos() {

        // PROYECTOS
        btnProyectos.addActionListener(e -> {
            ProyectosView vista = new ProyectosView();
            new ProyectoController(vista, usuario);
            mostrarVista(vista);
        });

        // SPRINTS
        btnActividades.addActionListener(e -> {
            SprintView vista = new SprintView();
            new SprintController(vista, usuario);
            mostrarVista(vista);
        });

        // KANBAN
        btnKanban.addActionListener(e -> {
            KanbanBoardView vista = new KanbanBoardView();
            new KanbanController(vista, usuario); 
            mostrarVista(vista);
        });

        // CHAT
        btnChat.addActionListener(e -> mostrarVista(new ChatView()));

        // HISTORIAL
        btnHistorial.addActionListener(e -> mostrarVista(new HistorialView()));

        // CERRAR SESIÓN
        btnCerrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Sesión cerrada correctamente.", "Salir", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });
    }

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
