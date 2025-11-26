package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginView extends JFrame {

    public JTextField campoCorreo;
    public JPasswordField campoPassword;
    public JButton btnIniciar;
    public JButton btnRegistrarse;

    public LoginView() {
        setTitle("Iniciar Sesión");
        setSize(420, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0xF5F7FA));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        add(mainPanel);

        // Título
        JLabel titulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(0x2D2D2D));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Panel del formulario
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0xF5F7FA));
        formPanel.setLayout(new GridLayout(6, 1, 0, 10));

        JLabel lblCorreo = new JLabel("Correo electrónico:");
        lblCorreo.setFont(new Font("Segoe UI", 0, 14));
        lblCorreo.setForeground(new Color(0x555555));

        campoCorreo = new JTextField();
        campoCorreo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", 0, 14));
        lblPassword.setForeground(new Color(0x555555));

        campoPassword = new JPasswordField();
        campoPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setBackground(new Color(0x4A90E2));
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIniciar.setBorderPainted(false);
        btnIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setBackground(new Color(0xE94E77));
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrarse.setBorderPainted(false);
        btnRegistrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));

        formPanel.add(lblCorreo);
        formPanel.add(campoCorreo);
        formPanel.add(lblPassword);
        formPanel.add(campoPassword);
        formPanel.add(btnIniciar);
        formPanel.add(btnRegistrarse);

        mainPanel.add(formPanel, BorderLayout.CENTER);
    }
}
