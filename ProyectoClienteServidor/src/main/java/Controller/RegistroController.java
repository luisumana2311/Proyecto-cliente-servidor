package Controller;

import View.RegistroView;
import View.LoginView;
import DAO.UsuarioDAO;
import Model.Usuario;
import Util.BCryptUtil;
import javax.swing.JOptionPane;

public class RegistroController {

    private RegistroView vista;
    private UsuarioDAO dao;

    public RegistroController(RegistroView vista) {
        this.vista = vista;
        this.dao = new UsuarioDAO();

        vista.btnRegistrar.addActionListener(e -> registrar());
        vista.btnVolver.addActionListener(e -> volver());
    }

    private void registrar() {
        String nombre = vista.campoNombre.getText();
        String correo = vista.campoCorreo.getText();
        String contrasena = new String(vista.campoPassword.getPassword());
        String contrasena2 = new String(vista.campoPassword2.getPassword());
        String rol = (String) vista.comboRol.getSelectedItem();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios");
            return;
        }

        if (!contrasena.equals(contrasena2)) {
            JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden");
            return;
        }

        try {
            Usuario existente = dao.findByCorreo(correo);
            if (existente != null) {
                JOptionPane.showMessageDialog(vista, "El correo ya está registrado");
                return;
            }
            int idRol = 3;
            switch (rol) {
                case "Scrum Master":
                    idRol = 1;
                    break;
                case "Product Owner":
                    idRol = 2;
                    break;
                case "Developer":
                    idRol = 3;
                    break;
            }
            String contraHash = BCryptUtil.hashPassword(contrasena);
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setCorreo(correo);
            nuevo.setContrasena(contraHash);
            nuevo.setIdRol(idRol);
            if (dao.create(nuevo)) {
                JOptionPane.showMessageDialog(vista, "Cuenta creada exitosamente\nRol asignado: " + rol);
                vista.dispose();
                LoginView login = new LoginView();
                new AuthController(login);
                login.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al crear la cuenta");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void volver() {
        vista.dispose();
        LoginView login = new LoginView();
        new AuthController(login);
        login.setVisible(true);
    }
}
