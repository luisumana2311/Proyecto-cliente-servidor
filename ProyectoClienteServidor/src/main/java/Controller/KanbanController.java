package Controller;

import View.KanbanBoardView;
import View.CrearTareaView;
import Model.Usuario;
import Model.Tarea;
import DAO.TareaDAO;
import DAO.SprintDAO;
import DAO.ProyectoDAO;
import Model.Sprint;
import Model.Proyecto;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.MouseEvent;
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
    private Sprint sprintActual;
    private Proyecto proyectoActual;

    public KanbanController(KanbanBoardView vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.tareaDAO = new TareaDAO();
        this.sprintDAO = new SprintDAO();
        this.proyectoDAO = new ProyectoDAO();
        iniciarEventos();
        cargarDatos();
    }

    private void iniciarEventos() {
        vista.btnNuevaTarea.addActionListener(e -> crearNuevaTarea());
    }

    private void cargarDatos() {
        try {
            List<Proyecto> proyectos = proyectoDAO.findByUsuario(usuario.getIdUsuario());
            if (proyectos.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No hay proyectos creados. Crea un proyecto primero.");
                return;
            }
            proyectoActual = proyectos.get(0);
            List<Sprint> sprints = sprintDAO.findByProyecto(proyectoActual.getIdProyecto());
            if (sprints.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No hay sprints creados en el proyecto" + proyectoActual.getNombre() + ".");
                return;
            }
            sprintActual = sprints.get(0);
            vista.actualizarTitulo(proyectoActual.getNombre(), sprintActual.getNombre());
            cargarTareas();
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
        if (sprintActual == null) {
            JOptionPane.showMessageDialog(vista, "No hay sprint disponible");
            return;
        }
        CrearTareaView crearVista = new CrearTareaView();
        crearVista.btnGuardar.addActionListener(e -> {
            String nombre = crearVista.campoTitulo.getText().trim();
            String descripcion = crearVista.campoDescripcion.getText().trim();
            String prioridad = (String) crearVista.comboPrioridad.getSelectedItem();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(crearVista, "El nombre es obligatorio");
                return;
            }
            try {
                Tarea t = new Tarea();
                t.setIdProyecto(sprintActual.getIdProyecto());
                t.setIdSprint(sprintActual.getIdSprint());
                t.setIdUsuario(usuario.getIdUsuario());
                t.setNombre(nombre);
                t.setDescripcion(descripcion);
                t.setPrioridad(prioridad);
                t.setIdEstadoTareas(1);
                if (tareaDAO.create(t)) {
                    JOptionPane.showMessageDialog(crearVista,
                            "Tarea creada en:\nProyecto: " + proyectoActual.getNombre()
                            + "\nSprint: " + sprintActual.getNombre());
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
}
