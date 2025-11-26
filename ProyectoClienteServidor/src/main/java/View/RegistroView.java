package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistroView extends JFrame {

    public JTextField campoNombre;
    public JTextField campoCorreo;
    public JPasswordField campoPassword;
    public JPasswordField campoPassword2;
    public JButton btnRegistrar;
    public JButton btnVolver;

    public RegistroView() {
        setTitle("Crear Cuenta");
        setSize(450, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(0xF5F7FA));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(35, 35, 35, 35));
        add(mainPanel);

        // Título
        JLabel titulo = new JLabel("Crear una Cuenta", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(0x2D2D2D));
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(0xF5F7FA));
        formPanel.setLayout(new GridLayout(10, 1, 0, 8));

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(new Color(0x555555));
        lblNombre.setFont(new Font("Segoe UI", 0, 14));

        campoNombre = new JTextField();
        campoNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Correo
        JLabel lblCorreo = new JLabel("Correo electrónico:");
        lblCorreo.setForeground(new Color(0x555555));
        lblCorreo.setFont(new Font("Segoe UI", 0, 14));

        campoCorreo = new JTextField();
        campoCorreo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Password 1
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setForeground(new Color(0x555555));
        lblPassword.setFont(new Font("Segoe UI", 0, 14));

        campoPassword = new JPasswordField();
        campoPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Password 2
        JLabel lblPassword2 = new JLabel("Confirmar contraseña:");
        lblPassword2.setForeground(new Color(0x555555));
        lblPassword2.setFont(new Font("Segoe UI", 0, 14));

        campoPassword2 = new JPasswordField();
        campoPassword2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D7E1), 2),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Botón registrar
        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBackground(new Color(0x4A90E2));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBorderPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Botón volver
        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(0xE94E77));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setBorderPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Agregar componentes al panel del formulario
        formPanel.add(lblNombre);
        formPanel.add(campoNombre);
        formPanel.add(lblCorreo);
        formPanel.add(campoCorreo);
        formPanel.add(lblPassword);
        formPanel.add(campoPassword);
        formPanel.add(lblPassword2);
        formPanel.add(campoPassword2);
        formPanel.add(btnRegistrar);
        formPanel.add(btnVolver);

        mainPanel.add(formPanel, BorderLayout.CENTER);
    }
}
