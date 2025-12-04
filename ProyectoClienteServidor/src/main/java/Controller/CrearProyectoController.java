package Controller;

import DAO.ProyectoDAO;
import Model.Proyecto;
import Model.Usuario;
import View.CrearProyectoView;

import javax.swing.*;

public class CrearProyectoController {

    private CrearProyectoView vista;
    private ProyectoDAO dao;
    private Usuario usuario;
    private Runnable actualizarTablaCB;

    public CrearProyectoController(CrearProyectoView vista, Usuario usuario, Runnable actualizarTablaCB) {
        this.vista = vista;
        this.usuario = usuario;
        this.actualizarTablaCB = actualizarTablaCB;
        this.dao = new ProyectoDAO();

        vista.btnGuardar.addActionListener(e -> guardar());
        vista.btnCancelar.addActionListener(e -> vista.dispose());
    }

    private void guardar() {
        String nombre = vista.campoNombre.getText().trim();
        String descripcion = vista.campoDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre es obligatorio");
            return;
        }
        try {
            Proyecto p = new Proyecto(usuario.getIdUsuario(), nombre, descripcion);
            if (dao.create(p)) {
                JOptionPane.showMessageDialog(vista, "Proyecto creado con éxito");
                if (actualizarTablaCB != null) {
                    actualizarTablaCB.run();
                }
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al crear el proyecto");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
