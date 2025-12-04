package DAO;

import Model.Tarea;
import Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author landr
 */
public class TareaDAO {

    public boolean create(Tarea t) throws Exception {
        String sql = "INSERT INTO Tareas (Id_Proyecto, Id_Sprint, Id_Usuario, Id_Estado_Tareas, Nombre, Descripcion, Prioridad, Fecha_Creacion) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getIdProyecto());
            ps.setInt(2, t.getIdSprint());
            ps.setInt(3, t.getIdUsuario());
            ps.setInt(4, t.getIdEstadoTareas());
            ps.setString(5, t.getNombre());
            ps.setString(6, t.getDescripcion());
            ps.setString(7, t.getPrioridad());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setIdTarea(rs.getInt(1));
                }
            }
        }
        return true;
    }

    public List<Tarea> findBySprint(int idSprint) throws Exception {
        List<Tarea> lista = new ArrayList<>();
        String sql = "SELECT * FROM Tareas WHERE Id_Sprint = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idSprint);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tarea t = new Tarea();
                    t.setIdTarea(rs.getInt("Id_Tarea"));
                    t.setIdProyecto(rs.getInt("Id_Proyecto"));
                    t.setIdSprint(rs.getInt("Id_Sprint"));
                    t.setIdUsuario(rs.getInt("Id_Usuario"));
                    t.setIdEstadoTareas(rs.getInt("Id_Estado_Tareas"));
                    t.setNombre(rs.getString("Nombre"));
                    t.setDescripcion(rs.getString("Descripcion"));
                    t.setPrioridad(rs.getString("Prioridad"));
                    t.setFechaCreacion(rs.getTimestamp("Fecha_Creacion"));
                    lista.add(t);
                }
            }
        }
        return lista;
    }

    public boolean updateEstado(int idTarea, int nuevoEstado) throws Exception {
        String sql = "UPDATE Tareas SET Id_Estado_Tareas = ? WHERE Id_Tarea = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado);
            ps.setInt(2, idTarea);
            return ps.executeUpdate() > 0;
        }
    }

    public Tarea findById(int idTarea) throws Exception {
        String sql = "SELECT * FROM Tareas WHERE Id_Tarea = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idTarea);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tarea t = new Tarea();
                    t.setIdTarea(rs.getInt("Id_Tarea"));
                    t.setIdProyecto(rs.getInt("Id_Proyecto"));
                    t.setIdSprint(rs.getInt("Id_Sprint"));
                    t.setIdUsuario(rs.getInt("Id_Usuario"));
                    t.setIdEstadoTareas(rs.getInt("Id_Estado_Tareas"));
                    t.setNombre(rs.getString("Nombre"));
                    t.setDescripcion(rs.getString("Descripcion"));
                    t.setPrioridad(rs.getString("Prioridad"));
                    t.setFechaCreacion(rs.getTimestamp("Fecha_Creacion"));
                    Date fechaVenc = rs.getDate("Fecha_Vencimiento");
                    if (fechaVenc != null) {
                        t.setFechaVencimiento(fechaVenc);
                    }
                    return t;
                }
                return null;
            }
        }
    }

    public boolean update(Tarea t) throws Exception {
        String sql = "UPDATE Tareas SET Nombre = ?, Descripcion = ?, Prioridad = ?, Id_Estado_Tareas = ? WHERE Id_Tarea = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getPrioridad());
            ps.setInt(4, t.getIdEstadoTareas());
            ps.setInt(5, t.getIdTarea());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idTarea) throws Exception {
        String sql = "DELETE FROM Tareas WHERE Id_Tarea = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idTarea);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Tarea> findAll() throws Exception {
        List<Tarea> lista = new ArrayList<>();
        String sql = "SELECT * FROM Tareas";
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Tarea t = new Tarea();
                t.setIdTarea(rs.getInt("Id_Tarea"));
                t.setIdProyecto(rs.getInt("Id_Proyecto"));
                t.setIdSprint(rs.getInt("Id_Sprint"));
                t.setIdUsuario(rs.getInt("Id_Usuario"));
                t.setIdEstadoTareas(rs.getInt("Id_Estado_Tareas"));
                t.setNombre(rs.getString("Nombre"));
                t.setDescripcion(rs.getString("Descripcion"));
                t.setPrioridad(rs.getString("Prioridad"));
                t.setFechaCreacion(rs.getTimestamp("Fecha_Creacion"));
                Date fechaVenc = rs.getDate("Fecha_Vencimiento");
                if (fechaVenc != null) {
                    t.setFechaVencimiento(fechaVenc);
                }
                lista.add(t);
            }
        }
        return lista;
    }
}
