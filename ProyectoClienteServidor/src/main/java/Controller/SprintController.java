package Controller;

import View.SprintView;
import View.CrearSprintView;
import DAO.SprintDAO;
import DAO.ProyectoDAO;
import Model.Sprint;
import Model.Proyecto;
import Model.Usuario;

import javax.swing.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SprintController {

    private SprintView vista;
    private SprintDAO dao;
    private Usuario usuario;

    public SprintController(SprintView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.dao = new SprintDAO();
        cargarProyectos();
        cargarSprints();
        vista.btnNuevo.addActionListener(e -> abrirCrearSprint());
        vista.btnEliminar.addActionListener(e -> {
            try {
                eliminarSprint();
            } catch (Exception ex) {
                Logger.getLogger(SprintController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        vista.comboProyecto.addActionListener(e -> cargarSprints());
    }

    private void cargarProyectos() {
        try {
            ProyectoDAO pdao = new ProyectoDAO();
            List<Proyecto> lista = pdao.listar(usuario.getIdUsuario());
            vista.comboProyecto.removeAllItems();
            if (lista.isEmpty()) {
                vista.comboProyecto.addItem("No hay proyectos disponibles");
                return;
            }
            for (Proyecto p : lista) {
                vista.comboProyecto.addItem(p.getIdProyecto() + " - " + p.getNombre());
            }
            if (vista.comboProyecto.getItemCount() > 0) {
                vista.comboProyecto.setSelectedIndex(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar proyectos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarSprints() {
        vista.modeloTabla.setRowCount(0);
        if (vista.comboProyecto.getItemCount() == 0 || vista.comboProyecto.getSelectedItem() == null || vista.comboProyecto.getSelectedItem().toString().contains("No hay proyectos")) {
            return;
        }
        try {
            int idProyectoSelect = obtenerIdProyect();
            if (idProyectoSelect == -1) {
                return;
            }
            List<Sprint> sprints = dao.findByProyecto(idProyectoSelect);
            for (Sprint s : sprints) {
                vista.modeloTabla.addRow(new Object[]{
                    s.getIdSprint(),
                    s.getNombre(),
                    s.getFechaInicio(),
                    s.getFechaFin(),
                    "Activo"
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar sprints: " + ex.getMessage());
        }
    }

    private void abrirCrearSprint() {
        if (vista.comboProyecto.getItemCount() == 0 || vista.comboProyecto.getSelectedItem().toString().equals("Sin proyectos disponibles")) {
            JOptionPane.showMessageDialog(vista, "Primero debe crear un proyecto");
            return;
        }
        CrearSprintView form = new CrearSprintView();
        new CrearSprintController(
                form,
                obtenerIdProyect(),
                usuario.getIdUsuario(),
                this::cargarSprints
        );
        form.setVisible(true);
    }

    private void eliminarSprint() throws Exception {
        int fila = vista.tablaSprints.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un sprint para eliminar");
            return;
        }
        int id = (int) vista.modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(
                vista,
                "¿Eliminar este sprint?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = dao.delete(id);
            if (ok) {
                JOptionPane.showMessageDialog(vista, "Sprint eliminado");
                cargarSprints();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar");
            }
        }
    }

    private int obtenerIdProyect() {
        try {
            String texto = vista.comboProyecto.getSelectedItem().toString();
            return Integer.parseInt(texto.split(" - ")[0]);
        } catch (Exception e) {
            return -1;
        }
    }
}
