package Main;

import View.LoginView;
import Controller.AuthController;

public class Main {
    public static void main(String[] args) {
        LoginView vista = new LoginView();
        new AuthController(vista);
        vista.setVisible(true);
    }
}
