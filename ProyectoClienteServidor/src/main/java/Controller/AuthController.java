package Controller;

import View.LoginView;
import View.RegistroView;
import DAO.UsuarioDAO;
import Model.Usuario;
import javax.swing.JOptionPane;
import View.DashboardView;
import Util.BCryptUtil;

public class AuthController {

    private LoginView loginView;
    private UsuarioDAO dao;

    public AuthController(LoginView vista) {
        this.loginView = vista;
        this.dao = new UsuarioDAO();

        // Eventos de los botones
        vista.btnIniciar.addActionListener(e -> iniciarSesion());
        vista.btnRegistrarse.addActionListener(e -> registrar());
    }

    private void iniciarSesion() {
        String correo = loginView.campoCorreo.getText().trim();
        String contra = new String(loginView.campoPassword.getPassword());
        if (correo.isEmpty() || contra.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Complete los campos");
            return;
        }
        try {
            Usuario user = dao.findByCorreo(correo);

            if (user != null && BCryptUtil.checkPassword(contra, user.getContrasena())) {
                JOptionPane.showMessageDialog(loginView, "Bienvenido " + user.getNombre());
                // Abrir dashboard enviando el usuario
                DashboardView dashboard = new DashboardView(user);
                dashboard.setVisible(true);
                // Cerrar login
                loginView.dispose();
            } else {
                JOptionPane.showMessageDialog(loginView, "Correo o contraseña incorrectos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(loginView, "Error al iniciar sesión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void registrar() {
        RegistroView reg = new RegistroView();
        new RegistroController(reg);
        reg.setVisible(true);
        loginView.dispose();
    }
}
