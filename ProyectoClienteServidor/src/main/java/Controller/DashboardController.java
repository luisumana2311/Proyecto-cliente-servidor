package Controller;

import View.*;
import Model.Usuario;

/**
 *
 * @author landr
 */
public class DashboardController {

    private DashboardView dashboardView;
    private Usuario usuario;

    public DashboardController(DashboardView dashboardView, Usuario usuario) {
        this.dashboardView = dashboardView;
        this.usuario = usuario;
    }

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
