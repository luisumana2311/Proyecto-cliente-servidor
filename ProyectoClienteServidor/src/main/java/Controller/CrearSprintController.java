package Controller;

import DAO.SprintDAO;
import Model.Sprint;
import View.CrearSprintView;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class CrearSprintController {

    private CrearSprintView vista;
    private SprintDAO dao;
    private int idProyecto;
    private int idUsuario;
    private Runnable actualizarTablaCB;

    public CrearSprintController(CrearSprintView vista, int idProyecto, int idUsuario, Runnable actualizarTablaCB) {
        this.vista = vista;
        this.idProyecto = idProyecto;
        this.idUsuario = idUsuario;
        this.actualizarTablaCB = actualizarTablaCB;
        this.dao = new SprintDAO();
        vista.btnGuardar.addActionListener(e -> {
            try {
                guardar();
            } catch (Exception ex) {
                Logger.getLogger(CrearSprintController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        vista.btnCancelar.addActionListener(e -> vista.dispose());
    }

    private void guardar() throws Exception {
        String nombre = vista.campoNombreSprint.getText().trim();
        String inicio = vista.campoFechaInicio.getText().trim();
        String fin = vista.campoFechaFin.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre es obligatorio");
            return;
        }
        try {
            Sprint s = new Sprint(nombre, inicio, fin, idUsuario, idProyecto);
            s.setNumeroSprint(1);
            boolean ok = dao.create(s);
            if (ok) {
                JOptionPane.showMessageDialog(vista, "Sprint creado con éxito");
                if (actualizarTablaCB != null) {
                    actualizarTablaCB.run();
                }
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al guardar sprint");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
