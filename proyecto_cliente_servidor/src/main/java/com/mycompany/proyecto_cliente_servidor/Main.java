package com.mycompany.proyecto_cliente_servidor;

import javax.swing.SwingUtilities;
import com.mycompany.proyecto_cliente_servidor.views.DashboardView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DashboardView().setVisible(true);
        });
    }
}
