package Controller;

import View.ChatView;
import Model.Usuario;
import Model.Mensaje;
import Model.Proyecto;
import DAO.MensajeDAO;
import DAO.ProyectoDAO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author landr
 */
public class ChatController {

    private ChatView vista;
    private Usuario usuario;
    private MensajeDAO mensajeDAO;
    private ProyectoDAO proyectoDAO;
    private Proyecto proyectoActual;
    private Timer actualizarMensajes;
    private java.sql.Timestamp ultimoMensaje = null;

    public ChatController(ChatView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.mensajeDAO = new MensajeDAO();
        this.proyectoDAO = new ProyectoDAO();
        initActions();
        cargarProyecto();
        cargarMensajes();
        initActualizarAuto();
    }

    private void initActions() {
        vista.btnEnviar.addActionListener(e -> enviarMensaje());
        vista.campoMensaje.addActionListener(e -> enviarMensaje());
    }

    private void cargarProyecto() {
        try {
            List<Proyecto> proyectos = proyectoDAO.findByUsuario(usuario.getIdUsuario());
            if (!proyectos.isEmpty()) {
                proyectoActual = proyectos.get(0);
            } else {
                JOptionPane.showMessageDialog(vista, "No hay proyectos disponibles para chatear");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar el proyecto: " + ex.getMessage());
        }
    }

    private void cargarMensajes() {
        if (proyectoActual == null) {
            vista.areaMensajes.setText("No hay proyecto activo para mostrar mensajes");
            return;
        }
        try {
            List<Mensaje> mensajes = mensajeDAO.findByProyecto(proyectoActual.getIdProyecto());
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            for (Mensaje m : mensajes) {
                String fecha = sdf.format(m.getFechaEnvio());
                String usuarioM = m.getNombreUsuario();
                if (m.getIdUsuario() == usuario.getIdUsuario()) {
                    sb.append("[").append(fecha).append("] Tú:\n");
                } else {
                    sb.append("[").append(fecha).append("] ").append(usuarioM).append(":\n");
                }
                sb.append("  ").append(m.getMensaje()).append("\n\n");
            }
            vista.areaMensajes.setText(sb.toString());
            vista.areaMensajes.setCaretPosition(vista.areaMensajes.getDocument().getLength());
            if (!mensajes.isEmpty()) {
                ultimoMensaje = mensajes.get(mensajes.size() - 1).getFechaEnvio();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar mensajes: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void enviarMensaje() {
        if (proyectoActual == null) {
            JOptionPane.showMessageDialog(vista, "No hay un proyecto activo en este momento");
            return;
        }
        String txtMensaje = vista.campoMensaje.getText().trim();
        if (txtMensaje.isEmpty()) {
            return;
        }
        try {
            Mensaje m = new Mensaje(proyectoActual.getIdProyecto(), usuario.getIdUsuario(), txtMensaje);
            if (mensajeDAO.create(m)) {
                vista.campoMensaje.setText("");
                cargarMensajes();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al enviar mensaje");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void initActualizarAuto() {
        actualizarMensajes = new Timer(2000, e -> mensajesNuevos());
        actualizarMensajes.start();
    }

    private void mensajesNuevos() {
        if (proyectoActual == null) {
            return;
        }
        try {
            Timestamp ultimoMensajeS = mensajeDAO.ultimoMensaje(proyectoActual.getIdProyecto());
            if (ultimoMensajeS != null && (ultimoMensaje == null || ultimoMensajeS.after(ultimoMensaje))) {
                cargarMensajes();
            }
        } catch (Exception ex) {
            System.err.println("Error verificando mensaje nuevos: " + ex.getMessage());
        }
    }

    public void detener() {
        if (actualizarMensajes != null) {
            actualizarMensajes.stop();
        }
    }
}
