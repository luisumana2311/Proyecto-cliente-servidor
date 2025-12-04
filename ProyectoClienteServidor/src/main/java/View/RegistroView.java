package View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegistroView extends JFrame {

    public JTextField campoNombre;
    public JTextField campoCorreo;
    public JPasswordField campoPassword;
    public JPasswordField campoPassword2;
    public JComboBox<String> comboRol;
    public JButton btnRegistrar;
    public JButton btnVolver;

    public RegistroView() {

        setTitle("Crear Cuenta");
        setSize(420, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(0xF5F7FA));
        main.setBorder(new EmptyBorder(30, 30, 30, 30));
        add(main);

        JLabel titulo = new JLabel("Crear Cuenta", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(0x2D2D2D));
        main.add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(12, 1, 0, 10));
        form.setBackground(new Color(0xF5F7FA));

        JLabel lblNombre = new JLabel("Nombre completo:");
        campoNombre = new JTextField();

        JLabel lblCorreo = new JLabel("Correo electrónico:");
        campoCorreo = new JTextField();

        JLabel lblRol = new JLabel("Rol en el proyecto:");
        lblRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboRol = new JComboBox<>(new String[]{
            "Developer",
            "Scrum Master",
            "Product Owner"
        });
        comboRol.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboRol.setBackground(Color.WHITE);

        JLabel lblPass = new JLabel("Contraseña:");
        campoPassword = new JPasswordField();

        JLabel lblPass2 = new JLabel("Confirmar contraseña:");
        campoPassword2 = new JPasswordField();

        btnRegistrar = new JButton("Registrarse");
        btnRegistrar.setBackground(new Color(0x4A90E2));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(0xE94E77));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));

        form.add(lblNombre);
        form.add(campoNombre);
        form.add(lblCorreo);
        form.add(campoCorreo);
        form.add(lblRol);
        form.add(comboRol);
        form.add(lblPass);
        form.add(campoPassword);
        form.add(lblPass2);
        form.add(campoPassword2);
        form.add(btnRegistrar);
        form.add(btnVolver);

        main.add(form, BorderLayout.CENTER);
    }
}
