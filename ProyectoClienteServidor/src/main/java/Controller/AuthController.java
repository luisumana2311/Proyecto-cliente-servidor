package Controller;

import Client.*;
import View.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author landr
 */
public class AuthController {

    private LoginView loginView;
    private RegistroView registroView;
    private String host = "localhost";
    private int port = 5555;

    public AuthController(LoginView loginView, RegistroView registroView) {
        this.loginView = loginView;
        this.registroView = registroView;
        initActions();
    }

    private void initActions() {
        loginView.btnIniciar.addActionListener(e -> doLogin());
        loginView.btnRegistrarse.addActionListener(e -> registroView.setVisible(true));
        registroView.btnRegistrar.addActionListener(e -> doRegister());
        registroView.btnVolver.addActionListener(e -> registroView.setVisible(false));
    }

    private void doLogin() {
        String correo = loginView.campoCorreo.getText().trim();
        String password = new String(loginView.campoPassword.getPassword());

        if (correo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginView, "Complete los campos");
            return;
        }
        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("login");
            Map<String, Object> p = new HashMap<>();
            p.put("correo", correo);
            p.put("password", password);
            r.setPayload(p);
            Response resp = conn.sendRequest(r);
            JOptionPane.showMessageDialog(loginView, resp.getMessage());
            if (resp.isSuccess()) {
                loginView.dispose();
                DashboardView dashboard = new DashboardView();
                dashboard.setVisible(true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(loginView, "Error de conexion: " + ex.getMessage());
        }
    }

    private void doRegister() {
        String nombre = registroView.campoNombre.getText().trim();
        String correo = registroView.campoCorreo.getText().trim();
        String password1 = new String(registroView.campoPassword.getPassword());
        String password2 = new String(registroView.campoPassword2.getPassword());

        if (nombre.isEmpty() || correo.isEmpty() || password1.isEmpty()) {
            JOptionPane.showMessageDialog(registroView, "Complete los campos");
            return;
        }
        if (!password1.equals(password2)) {
            JOptionPane.showMessageDialog(registroView, "Contraseñas no coinciden");
            return;
        }
        try (ClientConnector conn = new ClientConnector(host, port)) {
            Request r = new Request();
            r.setAction("register");
            Map<String, Object> p = new HashMap<>();
            p.put("nombre", nombre);
            p.put("correo", correo);
            p.put("password", password1);
            p.put("idRol", 3);
            r.setPayload(p);
            Response resp = conn.sendRequest(r);
            JOptionPane.showMessageDialog(registroView, resp.getMessage());
            if (resp.isSuccess()) {
                registroView.setVisible(false);
                registroView.campoNombre.setText("");
                registroView.campoCorreo.setText("");
                registroView.campoPassword.setText("");
                registroView.campoPassword2.setText("");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(registroView, "Error de conexion: " + ex.getMessage());
        }
    }
}
