package Controller;

import View.HistorialView;
import Model.Usuario;
import Model.HistorialActividad;
import Model.Proyecto;
import DAO.HistorialDAO;
import DAO.ProyectoDAO;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author landr
 */
public class HistorialController {

    private HistorialView vista;
    private Usuario usuario;
    private HistorialDAO historialDAO;
    private ProyectoDAO proyectoDAO;
    private Proyecto proyectoActual;
    private Timer actualizarHistorial;
    private int ultimaCantidad = 0;

    public HistorialController(HistorialView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.historialDAO = new HistorialDAO();
        this.proyectoDAO = new ProyectoDAO();
        cargarProyecto();
        cargarHistorial();
        initActulizarAuto();
    }

    private void cargarProyecto() {
        try {
            List<Proyecto> proyectos = proyectoDAO.findByUsuario(usuario.getIdUsuario());
            if (!proyectos.isEmpty()) {
                proyectoActual = proyectos.get(0);
            } else {
                vista.areaHistorial.setText("No hay proyectos disponibles");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar el proyecto: " + ex.getMessage());
        }
    }

    private void cargarHistorial() {
        if (proyectoActual == null) {
            vista.areaHistorial.setText("No hay proyecto activo para mostrar el historial");
            return;
        }
        try {
            List<HistorialActividad> actividades = historialDAO.findByProyecto(proyectoActual.getIdProyecto());
            if (actividades.isEmpty()) {
                vista.areaHistorial.setText("No hay actividades registradas aún.\n\n"
                        + "Las actividades se registrarán automáticamente cuando:\n"
                        + "• Crees una tarea\n"
                        + "• Cambies el estado de una tarea\n"
                        + "• Elimines una tarea");
                return;
            }

            StringBuilder sb = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sb.append("═══════════════════════════════════════════════════════\n");
            sb.append("  HISTORIAL DE ACTIVIDADES - ").append(proyectoActual.getNombre()).append("\n");
            sb.append("═══════════════════════════════════════════════════════\n\n");
            for (HistorialActividad h : actividades) {
                String fecha = sdf.format(h.getFechaHora());
                sb.append(fecha).append("\n");
                sb.append(h.getNombreUsuario()).append("\n");
                sb.append(h.getNombreTarea()).append("\n");
                sb.append(h.getAccion()).append("\n");
                sb.append("───────────────────────────────────────────────────────\n\n");
            }
            vista.areaHistorial.setText(sb.toString());
            vista.areaHistorial.setCaretPosition(0);
            ultimaCantidad = actividades.size();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar historial: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void initActulizarAuto() {
        actualizarHistorial = new Timer(5000, e -> nuevasActividades());
        actualizarHistorial.start();
    }

    private void nuevasActividades() {
        if (proyectoActual == null) {
            return;
        }
        try {
            List<HistorialActividad> actividades = historialDAO.findByProyecto(proyectoActual.getIdProyecto());
            if (actividades.size()> ultimaCantidad) {
                cargarHistorial();
            }
        } catch (Exception ex) {
            System.err.println("Error verificando nuevas actividades: " + ex.getMessage());
        }
    }
    
    public void detener() {
        if (actualizarHistorial != null) {
            actualizarHistorial.stop();
        }
    }
}
