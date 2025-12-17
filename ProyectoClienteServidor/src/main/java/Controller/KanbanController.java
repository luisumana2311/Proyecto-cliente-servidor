package Controller;

import View.KanbanBoardView;
import View.CrearTareaView;
import Model.Usuario;
import Model.Tarea;
import DAO.UsuarioDAO;
import DAO.TareaDAO;
import DAO.SprintDAO;
import DAO.ProyectoDAO;
import DAO.HistorialDAO;
import Model.HistorialActividad;
import Model.Sprint;
import Model.Proyecto;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author landr
 */
public class KanbanController {

    private KanbanBoardView vista;
    private Usuario usuario;
    private TareaDAO tareaDAO;
    private SprintDAO sprintDAO;
    private ProyectoDAO proyectoDAO;
    private UsuarioDAO usuarioDAO;
    private HistorialDAO historialDAO;
    private Sprint sprintActual;
    private Proyecto proyectoActual;
    private Timer actualizarAuto;
    private Timestamp ultimaActualizacion = null;

    public KanbanController(KanbanBoardView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.tareaDAO = new TareaDAO();
        this.sprintDAO = new SprintDAO();
        this.proyectoDAO = new ProyectoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.historialDAO = new HistorialDAO();
        iniciarEventos();
        cargarDatos();
        configurarDrops();
        initActulizacionAuto();
    }

    private void iniciarEventos() {
        vista.btnNuevaTarea.addActionListener(e -> crearNuevaTarea());
    }

    private void cargarDatos() {
        try {
            List<Proyecto> proyectos = proyectoDAO.findByUsuario(usuario.getIdUsuario());
            vista.comboProyecto.removeAllItems();
            if (proyectos.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No hay proyectos creados. Crea un proyecto primero.");
                return;
            }
            for (Proyecto p : proyectos) {
                vista.comboProyecto.addItem(p.getIdProyecto() + " - " + p.getNombre());
            }
            vista.comboProyecto.addActionListener(e -> {
                if (vista.comboProyecto.getSelectedItem() != null) {
                    cargarSprintsProyecto();
                }
            });
            vista.comboSprint.addActionListener(e -> {
                if (vista.comboSprint.getSelectedItem() != null) {
                    cargarTareasSprint();
                }
            });
            if (vista.comboProyecto.getItemCount() > 0) {
                vista.comboProyecto.setSelectedIndex(0);
                cargarSprintsProyecto();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void cargarTareas() {
        if (sprintActual == null) {
            return;
        }
        try {
            List<Tarea> tareas = tareaDAO.findBySprint(sprintActual.getIdSprint());
            limpiarColumnas();
            for (Tarea t : tareas) {
                JPanel tarjeta = crearTarjeta(t);
                switch (t.getIdEstadoTareas()) {
                    case 1:
                        agregarTC(vista.columnaPendiente, tarjeta);
                        break;
                    case 2:
                        agregarTC(vista.columnaProgreso, tarjeta);
                        break;
                    case 3:
                        agregarTC(vista.columnaCompletado, tarjeta);
                        break;
                }
            }
            ultimaActualizacion = tareaDAO.ultimaModi(sprintActual.getIdSprint());
            vista.revalidate();
            vista.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tareas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private JPanel crearTarjeta(Tarea t) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BorderLayout(5, 5));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(getColorPrioridad(t.getPrioridad()), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel lblNombre = new JLabel(t.getNombre());
        lblNombre.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));

        JLabel lblPrioridad = new JLabel(t.getPrioridad());
        lblPrioridad.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblPrioridad.setForeground(getColorPrioridad(t.getPrioridad()));

        topPanel.add(lblNombre, BorderLayout.WEST);
        topPanel.add(lblPrioridad, BorderLayout.EAST);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setBackground(Color.WHITE);
        try {
            Usuario responsable = usuarioDAO.findAll().stream().filter(u -> u.getIdUsuario() == t.getIdUsuario()).findFirst().orElse(null);
            if (responsable != null) {
                JLabel lblResponsable = new JLabel(responsable.getNombre());
                lblResponsable.setFont(new Font("SansSerif", Font.ITALIC, 10));
                lblResponsable.setForeground(new Color(0x666666));
                bottomPanel.add(lblResponsable);
            }
        } catch (Exception ex) {
            System.err.println("Error cargando responsable: " + ex.getMessage());
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        if (t.getDescripcion() != null && !t.getDescripcion().isEmpty()) {
            JLabel lblDesc = new JLabel("<html>"
                    + (t.getDescripcion().length() > 50
                    ? t.getDescripcion().substring(0, 50) + "..."
                    : t.getDescripcion()) + "</html>");
            lblDesc.setFont(new Font("SansSerif", Font.PLAIN, 11));
            lblDesc.setForeground(Color.GRAY);
            centerPanel.add(lblDesc);
            centerPanel.add(bottomPanel);
        }
        tarjeta.add(topPanel, BorderLayout.NORTH);
        tarjeta.add(centerPanel, BorderLayout.CENTER);
        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    mostrarMenu(e, t);
                } else if (e.getClickCount() == 2) {
                    mostrarDetalles(t);
                }
            }
        });

        dragAndDrop(tarjeta, t);
        return tarjeta;
    }

    private void dragAndDrop(JPanel tarjeta, Tarea tarea) {
        DragSource ds = new DragSource();
        ds.createDefaultDragGestureRecognizer(tarjeta, DnDConstants.ACTION_MOVE, new DragGestureListener() {
            @Override
            public void dragGestureRecognized(DragGestureEvent dge) {
                Transferable transferable = new StringSelection(String.valueOf(tarea.getIdTarea()));
                dge.startDrag(DragSource.DefaultMoveDrop, transferable);
            }
        });
    }

    private Color getColorPrioridad(String prioridad) {
        if (prioridad == null) {
            return Color.GRAY;
        }
        switch (prioridad.toLowerCase()) {
            case "alta":
                return new Color(0xE57373);
            case "media":
                return new Color(0xFFB74D);
            case "baja":
                return new Color(0x81C784);
            default:
                return Color.GRAY;
        }
    }

    private void mostrarMenu(MouseEvent e, Tarea t) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem itmDetalles = new JMenuItem("Ver detalles");
        itmDetalles.addActionListener(ev -> mostrarDetalles(t));
        JMenuItem itmMoverPendiente = new JMenuItem(" Pendiente");
        itmMoverPendiente.addActionListener(ev -> cambiarEstado(t, 1));
        JMenuItem itmMoverProgreso = new JMenuItem(" En Progreso");
        itmMoverProgreso.addActionListener(ev -> cambiarEstado(t, 2));
        JMenuItem itmMoverCompletado = new JMenuItem(" Completado");
        itmMoverCompletado.addActionListener(ev -> cambiarEstado(t, 3));
        JMenuItem itmEliminar = new JMenuItem("Eliminar");
        itmEliminar.setForeground(Color.RED);
        itmEliminar.addActionListener(ev -> eliminarTarea(t));
        menu.add(itmDetalles);
        menu.addSeparator();
        menu.add(itmMoverPendiente);
        menu.add(itmMoverProgreso);
        menu.add(itmMoverCompletado);
        menu.addSeparator();
        menu.add(itmEliminar);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private void mostrarDetalles(Tarea t) {
        StringBuilder detalles = new StringBuilder();
        detalles.append("TAREA: ").append(t.getNombre()).append("\n\n");
        detalles.append("Proyecto: ").append(proyectoActual.getNombre()).append("\n");
        detalles.append("Sprint: ").append(sprintActual.getNombre()).append("\n\n");
        detalles.append("Descripción:\n").append(t.getDescripcion() != null ? t.getDescripcion() : "Sin descripción").append("\n\n");
        detalles.append("Prioridad: ").append(t.getPrioridad()).append("\n");
        detalles.append("Estado: ");
        switch (t.getIdEstadoTareas()) {
            case 1:
                detalles.append("Pendiente");
                break;
            case 2:
                detalles.append("En Progreso");
                break;
            case 3:
                detalles.append("Completado");
                break;
        }

        JTextArea textArea = new JTextArea(detalles.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(vista, scrollPane, "Detalles de la Tarea", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarEstado(Tarea t, int nuevoEstado) {
        try {
            if (tareaDAO.updateEstado(t.getIdTarea(), nuevoEstado)) {
                String estadoN = "";
                switch (nuevoEstado) {
                    case 1:
                        estadoN = "Pendiente";
                        break;
                    case 2:
                        estadoN = "En Progreso";
                        break;
                    case 3:
                        estadoN = "Completado";
                        break;
                }
                registarHistorial(t.getIdTarea(), "Cambio estado a " + estadoN);
                cargarTareas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar estado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
        }
    }

    private void eliminarTarea(Tarea t) {
        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar la tarea '" + t.getNombre() + "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (tareaDAO.delete(t.getIdTarea())) {
                    registarHistorial(t.getIdTarea(), "Elimino la tarea");
                    JOptionPane.showMessageDialog(vista, "Tarea eliminada");
                    cargarTareas();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "Error: " + e.getMessage());
            }
        }
    }

    private void agregarTC(JPanel columna, JPanel tarjeta) {
        Component comp = columna.getComponent(1);
        if (comp instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) comp;
            JPanel contenido = (JPanel) scroll.getViewport().getView();
            contenido.add(tarjeta);
            contenido.add(Box.createVerticalStrut(10));
        }
    }

    private void limpiarColumnas() {
        limpiarColumna(vista.columnaPendiente);
        limpiarColumna(vista.columnaProgreso);
        limpiarColumna(vista.columnaCompletado);
    }

    private void limpiarColumna(JPanel columna) {
        Component comp = columna.getComponent(1);
        if (comp instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) comp;
            JPanel contenido = (JPanel) scroll.getViewport().getView();
            contenido.removeAll();
        }
    }

    private void crearNuevaTarea() {
        CrearTareaView crearVista = new CrearTareaView();
        try {
            List<Proyecto> proyectos = proyectoDAO.findByUsuario(usuario.getIdUsuario());
            crearVista.comboProyecto.removeAllItems();
            for (Proyecto p : proyectos) {
                crearVista.comboProyecto.addItem(p.getIdProyecto() + " - " + p.getNombre());
            }
            crearVista.comboProyecto.addActionListener(e -> {
                cargarSprintsForm(crearVista);
            });
            if (crearVista.comboProyecto.getItemCount() > 0) {
                cargarSprintsForm(crearVista);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(crearVista, "Error al cargar proyectos: " + e.getMessage());
        }
        try {
            List<Model.Usuario> usuarios = usuarioDAO.findAll();
            crearVista.comboResponsable.removeAllItems();
            for (Model.Usuario u : usuarios) {
                crearVista.comboResponsable.addItem(u.getIdUsuario() + " - " + u.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(crearVista, "Error al cargar usuarios: " + e.getMessage());
        }
        crearVista.btnGuardar.addActionListener(e -> {
            String nombre = crearVista.campoTitulo.getText().trim();
            String descripcion = crearVista.campoDescripcion.getText().trim();
            String prioridad = (String) crearVista.comboPrioridad.getSelectedItem();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(crearVista, "El nombre es obligatorio");
                return;
            }
            int idProyectoSeleccionado = -1;
            int idSprintSeleccionado = -1;
            try {
                String proyectoStr = (String) crearVista.comboProyecto.getSelectedItem();
                if (proyectoStr != null) {
                    idProyectoSeleccionado = Integer.parseInt(proyectoStr.split(" - ")[0]);
                }
                String sprintStr = (String) crearVista.comboSprint.getSelectedItem();
                if (sprintStr != null) {
                    idSprintSeleccionado = Integer.parseInt(sprintStr.split(" - ")[0]);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(crearVista, "Error: Selecciona un proyecto y sprint válidos");
                return;
            }
            if (idProyectoSeleccionado == -1 || idSprintSeleccionado == -1) {
                JOptionPane.showMessageDialog(crearVista, "Debes seleccionar un proyecto y un sprint");
                return;
            }
            int idResponsable = usuario.getIdUsuario();
            try {
                String seleccion = (String) crearVista.comboResponsable.getSelectedItem();
                if (seleccion != null) {
                    idResponsable = Integer.parseInt(seleccion.split(" - ")[0]);
                }
            } catch (Exception ex) {
                System.err.println("Error parseando responsable: " + ex.getMessage());
            }
            try {
                Tarea t = new Tarea();
                t.setIdProyecto(idProyectoSeleccionado);
                t.setIdSprint(idSprintSeleccionado);
                t.setIdUsuario(idResponsable);
                t.setNombre(nombre);
                t.setDescripcion(descripcion);
                t.setPrioridad(prioridad);
                t.setIdEstadoTareas(1);
                if (tareaDAO.create(t)) {
                    registarHistorial(t.getIdTarea(), "Creó la tarea");
                    JOptionPane.showMessageDialog(crearVista, "Tarea creada exitosamente");
                    crearVista.dispose();
                    cargarTareas();
                } else {
                    JOptionPane.showMessageDialog(crearVista, "Error al crear la tarea");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(crearVista, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        crearVista.btnCancelar.addActionListener(e -> crearVista.dispose());
        crearVista.setVisible(true);
    }

    private void configurarDrops() {
        configDropColum(vista.columnaPendiente, 1);
        configDropColum(vista.columnaProgreso, 2);
        configDropColum(vista.columnaCompletado, 3);
    }

    private void configDropColum(JPanel columna, int nuevoEstado) {
        Component scroll = columna.getComponent(1);
        if (scroll instanceof JScrollPane) {
            JPanel contenido = (JPanel) ((JScrollPane) scroll).getViewport().getView();
            new DropTarget(contenido, new DropTargetAdapter() {
                @Override
                public void drop(DropTargetDropEvent dtde) {
                    try {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                        Transferable trans = dtde.getTransferable();
                        String idTareaS = (String) trans.getTransferData(DataFlavor.stringFlavor);
                        int idTarea = Integer.parseInt(idTareaS);
                        if (tareaDAO.updateEstado(idTarea, nuevoEstado)) {
                            cargarTareas();
                            dtde.dropComplete(true);
                        } else {
                            dtde.dropComplete(true);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        dtde.dropComplete(false);
                    }
                }
            });
        }
    }

    private void initActulizacionAuto() {
        actualizarAuto = new Timer(3000, e -> actualizaciones());
        actualizarAuto.start();
    }

    private void actualizaciones() {
        if (sprintActual == null) {
            return;
        }
        try {
            Timestamp ultimaModiS = tareaDAO.ultimaModi(sprintActual.getIdSprint());
            if (ultimaModiS != null && (ultimaActualizacion == null || ultimaModiS.after(ultimaActualizacion))) {
                System.out.println("Cambios detectados, las tareas se recargaran");
                try {
                    if (sprintActual != null) {
                        cargarTareas();
                    }
                } catch (Exception ex) {
                    System.err.println("Error actualizando tareas: " + ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error verificando actualizaciones: " + ex.getMessage());
        }
    }

    public void detener() {
        if (actualizarAuto != null) {
            actualizarAuto.stop();
        }
    }

    private void registarHistorial(int idTarea, String accion) {
        try {
            HistorialActividad h = new HistorialActividad(usuario.getIdUsuario(), idTarea, accion);
            historialDAO.create(h);
        } catch (Exception ex) {
            System.err.println("Error al registrar en historial: " + ex.getMessage());
        }
    }

    private void cargarSprintsForm(CrearTareaView form) {
        try {
            String proyectoStr = (String) form.comboProyecto.getSelectedItem();
            if (proyectoStr == null) {
                return;
            }
            int idProyecto = Integer.parseInt(proyectoStr.split(" - ")[0]);
            List<Sprint> sprints = sprintDAO.findByProyecto(idProyecto);
            form.comboSprint.removeAllItems();
            if (sprints.isEmpty()) {
                form.comboSprint.addItem("No hay sprints en este proyecto");
            } else {
                for (Sprint s : sprints) {
                    form.comboSprint.addItem(s.getIdSprint() + " - " + s.getNombre());
                }
            }
        } catch (Exception e) {
            System.err.println("Error cargando sprints: " + e.getMessage());
        }
    }

    private void cargarSprintsProyecto() {
        try {
            String proyectoS = (String) vista.comboProyecto.getSelectedItem();
            if (proyectoS == null) {
                return;
            }
            int idProyecto = Integer.parseInt(proyectoS.split(" - ")[0]);
            proyectoActual = proyectoDAO.findByUsuario(usuario.getIdUsuario()).stream().filter(p -> p.getIdProyecto() == idProyecto).findFirst().orElse(null);
            if (proyectoActual == null) {
                return;
            }
            List<Sprint> sprints = sprintDAO.findByProyecto(idProyecto);
            vista.comboSprint.removeAllItems();
            if (sprints.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No hay sprints en el proyecto '" + proyectoActual.getNombre());
                limpiarColumnas();
                vista.actualizarTitulo(proyectoActual.getNombre(), "Sin Sprints");
                return;
            }
            for (Sprint s : sprints) {
                vista.comboSprint.addItem(s.getIdSprint() + " - " + s.getNombre());
            }
            if (vista.comboSprint.getItemCount() > 0) {
                vista.comboSprint.setSelectedIndex(0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar sprints:" + ex.getMessage());
            ex.printStackTrace();

        }
    }

    private void cargarTareasSprint() {
        try {
            String sprintS = (String) vista.comboSprint.getSelectedItem();
            if (sprintS == null || proyectoActual == null) {
                return;
            }
            int idSprint = Integer.parseInt(sprintS.split(" - ")[0]);
            sprintActual = sprintDAO.findByProyecto(proyectoActual.getIdProyecto()).stream().filter(s -> s.getIdSprint() == idSprint).findFirst().orElse(null);
            if (sprintActual != null) {
                vista.actualizarTitulo(proyectoActual.getNombre(), sprintActual.getNombre());
                cargarTareas();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tareas: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
