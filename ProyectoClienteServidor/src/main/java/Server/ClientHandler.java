package Server;

import Client.Request;
import Client.Response;
import DAO.*;
import Model.*;
import Util.BCryptUtil;
import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author landr
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private ProyectoDAO proyectoDAO = new ProyectoDAO();
    private SprintDAO sprintDAO = new SprintDAO();
    private TareaDAO tareaDAO = new TareaDAO();

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream()); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            System.out.println("Cliente conectado: " + socket.getRemoteSocketAddress());
            Object obj;
            while ((obj = in.readObject()) != null) {
                if (!(obj instanceof Request)) {
                    continue;
                }
                Request req = (Request) obj;
                System.out.println("Acción recibida: " + req.getAction());
                Response resp = handleRequest(req);
                out.writeObject(resp);
                out.flush();
            }
        } catch (EOFException eof) {
            System.out.println("Cliente desconectado");
        } catch (Exception e) {
            System.err.println("Error en ClientHandler: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception ex) {
            }
        }
    }

    private Response handleRequest(Request req) {
        try {
            String action = req.getAction();
            Map<String, Object> p = req.getPayload();

            switch (action.toLowerCase()) {
                case "register":
                    return handleRegister(p);
                case "login":
                    return handleLogin(p);
                case "crear_proyecto":
                    return handleCrearProyecto(p);
                case "listar_proyectos":
                    return handleListarProyectos(p);
                case "eliminar_proyecto":
                    return handleEliminarProyecto(p);
                case "crear_sprint":
                    return handleCrearSprint(p);
                case "listar_sprints":
                    return handleListarSprints(p);
                case "eliminar_sprint":
                    return handleEliminarSprint(p);
                case "crear_tarea":
                    return handleCrearTarea(p);
                case "listar_tareas":
                    return handleListarTareas(p);
                case "actualizar_estado_tarea":
                    return handleActualizarEstadoTarea(p);
                default:
                    return new Response(false, "Acción no reconocida: " + action);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error interno: " + ex.getMessage());
        }
    }

    private Response handleRegister(Map<String, Object> p) {
        try {
            String nombre = (String) p.get("nombre");
            String correo = (String) p.get("correo");
            String contrasena = (String) p.get("password");
            Integer idRol = (Integer) p.getOrDefault("idRol", 3);
            if (nombre == null || correo == null || contrasena == null) {
                return new Response(false, "Datos incompletos");
            }
            Usuario existente = usuarioDAO.findByCorreo(correo);
            if (existente != null) {
                return new Response(false, "El correo ya está registrado");
            }
            String contraHash = BCryptUtil.hashPassword(contrasena);
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setCorreo(correo);
            u.setContrasena(contraHash);
            u.setIdRol(idRol);
            if (usuarioDAO.create(u)) {
                return new Response(true, "Usuario registrado exitosamente");
            } else {
                return new Response(false, "Error al registrar usuario");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error al registrar: " + ex.getMessage());
        }
    }

    private Response handleLogin(Map<String, Object> p) {
        try {
            String correo = (String) p.get("correo");
            String contrasena = (String) p.get("password");
            if (correo == null || contrasena == null) {
                return new Response(false, "Datos incompletos");
            }
            Usuario u = usuarioDAO.findByCorreo(correo);
            if (u == null) {
                return new Response(false, "Usuario no encontrado");
            }
            if (BCryptUtil.checkPassword(contrasena, u.getContrasena())) {
                Response r = new Response(true, "Bienvenido " + u.getNombre());
                r.setData(u);
                return r;
            } else {
                return new Response(false, "Contraseña incorrecta");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error al iniciar sesión: " + ex.getMessage());
        }
    }

    private Response handleCrearProyecto(Map<String, Object> p) {
        try {
            Integer idUsuario = (Integer) p.get("idUsuario");
            String nombre = (String) p.get("nombre");
            String descripcion = (String) p.get("descripcion");
            if (idUsuario == null || nombre == null) {
                return new Response(false, "Datos incompletos");
            }
            Proyecto proyecto = new Proyecto(idUsuario, nombre, descripcion);
            if (proyectoDAO.create(proyecto)) {
                Response r = new Response(true, "Proyecto creado exitosamente");
                r.setData(proyecto);
                return r;
            } else {
                return new Response(false, "Error al crear proyecto");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleListarProyectos(Map<String, Object> p) {
        try {
            Integer idUsuario = (Integer) p.get("idUsuario");
            if (idUsuario == null) {
                return new Response(false, "ID de usuario requerido");
            }
            List<Proyecto> proyectos = proyectoDAO.findByUsuario(idUsuario);
            Response r = new Response(true, "Proyectos obtenidos");
            r.setData(proyectos);
            return r;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleEliminarProyecto(Map<String, Object> p) {
        try {
            Integer idProyecto = (Integer) p.get("idProyecto");
            if (idProyecto == null) {
                return new Response(false, "ID de proyecto requerido");
            }
            if (proyectoDAO.delete(idProyecto)) {
                return new Response(true, "Proyecto eliminado");
            } else {
                return new Response(false, "Error al eliminar proyecto");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleCrearSprint(Map<String, Object> p) {
        try {
            Integer idProyecto = (Integer) p.get("idProyecto");
            String nombre = (String) p.get("nombre");
            Integer numeroSprint = (Integer) p.getOrDefault("numeroSprint", 1);
            if (idProyecto == null || nombre == null) {
                return new Response(false, "Datos incompletos");
            }
            Sprint sprint = new Sprint();
            sprint.setIdProyecto(idProyecto);
            sprint.setNombre(nombre);
            sprint.setNumeroSprint(numeroSprint);
            if (sprintDAO.create(sprint)) {
                Response r = new Response(true, "Sprint creado exitosamente");
                r.setData(sprint);
                return r;
            } else {
                return new Response(false, "Error al crear sprint");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleListarSprints(Map<String, Object> p) {
        try {
            Integer idProyecto = (Integer) p.get("idProyecto");
            if (idProyecto == null) {
                return new Response(false, "ID de proyecto requerido");
            }
            List<Sprint> sprints = sprintDAO.findByProyecto(idProyecto);
            Response r = new Response(true, "Sprints obtenidos");
            r.setData(sprints);
            return r;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleEliminarSprint(Map<String, Object> p) {
        try {
            Integer idSprint = (Integer) p.get("idSprint");
            if (idSprint == null) {
                return new Response(false, "ID de sprint requerido");
            }
            if (sprintDAO.delete(idSprint)) {
                return new Response(true, "Sprint eliminado");
            } else {
                return new Response(false, "Error al eliminar sprint");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleCrearTarea(Map<String, Object> p) {
        try {
            Integer idProyecto = (Integer) p.get("idProyecto");
            Integer idSprint = (Integer) p.get("idSprint");
            Integer idUsuario = (Integer) p.get("idUsuario");
            String nombre = (String) p.get("nombre");
            String descripcion = (String) p.get("descripcion");
            String prioridad = (String) p.getOrDefault("prioridad", "Media");
            Integer idEstado = (Integer) p.getOrDefault("idEstado", 1); // 1 = Pendiente
            if (idProyecto == null || idSprint == null || idUsuario == null || nombre == null) {
                return new Response(false, "Datos incompletos");
            }
            Tarea tarea = new Tarea();
            tarea.setIdProyecto(idProyecto);
            tarea.setIdSprint(idSprint);
            tarea.setIdUsuario(idUsuario);
            tarea.setNombre(nombre);
            tarea.setDescripcion(descripcion);
            tarea.setPrioridad(prioridad);
            tarea.setIdEstadoTareas(idEstado);
            if (tareaDAO.create(tarea)) {
                Response r = new Response(true, "Tarea creada exitosamente");
                r.setData(tarea);
                return r;
            } else {
                return new Response(false, "Error al crear tarea");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleListarTareas(Map<String, Object> p) {
        try {
            Integer idSprint = (Integer) p.get("idSprint");

            if (idSprint == null) {
                return new Response(false, "ID de sprint requerido");
            }

            List<Tarea> tareas = tareaDAO.findBySprint(idSprint);
            Response r = new Response(true, "Tareas obtenidas");
            r.setData(tareas);
            return r;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }

    private Response handleActualizarEstadoTarea(Map<String, Object> p) {
        try {
            Integer idTarea = (Integer) p.get("idTarea");
            Integer nuevoEstado = (Integer) p.get("nuevoEstado");

            if (idTarea == null || nuevoEstado == null) {
                return new Response(false, "Datos incompletos");
            }

            if (tareaDAO.updateEstado(idTarea, nuevoEstado)) {
                return new Response(true, "Estado actualizado");
            } else {
                return new Response(false, "Error al actualizar estado");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response(false, "Error: " + ex.getMessage());
        }
    }
}
