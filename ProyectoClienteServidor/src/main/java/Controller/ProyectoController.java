package Controller;

import DAO.ProyectoDAO;
import Model.Proyecto;
import Model.Usuario;
import View.CrearProyectoView;
import View.ProyectosView;

import javax.swing.*;
import java.util.List;

public class ProyectoController {

    private ProyectosView vista;
    private ProyectoDAO dao;
    private Usuario usuario;

    public ProyectoController(ProyectosView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.dao = new ProyectoDAO();
        cargarTabla();
        vista.btnNuevo.addActionListener(e -> nuevoProyecto());
        vista.btnEliminar.addActionListener(e -> eliminarProyecto());
    }

    // ============================
    // CARGAR TABLA DE PROYECTOS
    // ============================
    private void cargarTabla() {
        List<Proyecto> lista = dao.listar(usuario.getIdUsuario());

        vista.modeloTabla.setRowCount(0);

        for (Proyecto p : lista) {
            vista.modeloTabla.addRow(new Object[]{
                p.getIdProyecto(),
                p.getNombre(),
                p.getDescripcion(),
                p.getEstado()
            });
        }
    }

    // ============================
    // NUEVO PROYECTO
    // ============================
    private void nuevoProyecto() {
        CrearProyectoView crearVista = new CrearProyectoView();

        new CrearProyectoController(
                crearVista,
                usuario,
                this::cargarTabla
        );
        crearVista.setVisible(true);
    }

    // ============================
    // ELIMINAR PROYECTO
    // ============================
    private void eliminarProyecto() {
        int fila = vista.tablaProyectos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un proyecto");
            return;
        }

        int idProyecto = (int) vista.modeloTabla.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(
                vista, "¿Eliminar este proyecto?", "Confirmar", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.eliminar(idProyecto)) {
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar proyecto");
            }
        }
    }
}
