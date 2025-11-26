package Main;

import javax.swing.SwingUtilities;
import View.*;
import Controller.AuthController;

/**
 *
 * @author landr
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginView lv = new LoginView();
            RegistroView rv = new RegistroView();
            new AuthController(lv, rv);
            lv.setVisible(true);
        });
    }
}
