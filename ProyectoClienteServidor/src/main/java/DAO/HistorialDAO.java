package DAO;

import Model.HistorialActividad;
import Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author landr
 */
public class HistorialDAO {

    public boolean create(HistorialActividad h) throws Exception {
        String sql = "INSERT INTO Historial_Actividades (Id_Usuario, Id_Tarea, Fecha_Hora, accion) " + "VALUES (?, ?, NOW(), ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, h.getIdUsuario());
            ps.setInt(2, h.getIdTarea());
            ps.setString(3, h.getAccion());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    h.setIdHistorial(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public List<HistorialActividad> findByProyecto(int idProyecto) throws Exception {
        List<HistorialActividad> lista = new ArrayList<>();
        String sql = "SELECT h.*, u.Nombre as NombreUsuario, t.Nombre as NombreTarea "
                + "FROM Historial_Actividades h "
                + "INNER JOIN Usuarios u ON h.Id_Usuario = u.Id_Usuario "
                + "INNER JOIN Tareas t ON h.Id_Tarea = t.Id_Tarea "
                + "WHERE t.Id_Proyecto = ? "
                + "ORDER BY h.Fecha_Hora DESC "
                + "LIMIT 100";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialActividad h = new HistorialActividad();
                    h.setIdHistorial(rs.getInt("Id_Historial"));
                    h.setIdUsuario(rs.getInt("Id_Usuario"));
                    h.setIdTarea(rs.getInt("Id_Tarea"));
                    h.setFechaHora(rs.getTimestamp("Fecha_Hora"));
                    h.setAccion(rs.getString("accion"));
                    h.setNombreUsuario(rs.getString("NombreUsuario"));
                    h.setNombreTarea(rs.getString("NombreTarea"));
                    lista.add(h);
                }
            }
        }
        return lista;
    }

    public List<HistorialActividad> findByUsuario(int idUsuario) throws Exception {
        List<HistorialActividad> lista = new ArrayList<>();
        String sql = "SELECT h.*, u.Nombre as NombreUsuario, t.Nombre as NombreTarea "
                + "FROM Historial_Actividades h "
                + "INNER JOIN Usuarios u ON h.Id_Usuario = u.Id_Usuario "
                + "INNER JOIN Tareas t ON h.Id_Tarea = t.Id_Tarea "
                + "WHERE h.Id_Usuario = ? "
                + "ORDER BY h.Fecha_Hora DESC "
                + "LIMIT 50";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialActividad h = new HistorialActividad();
                    h.setIdHistorial(rs.getInt("Id_Historial"));
                    h.setIdUsuario(rs.getInt("Id_Usuario"));
                    h.setIdTarea(rs.getInt("Id_Tarea"));
                    h.setFechaHora(rs.getTimestamp("Fecha_Hora"));
                    h.setAccion(rs.getString("accion"));
                    h.setNombreUsuario(rs.getString("NombreUsuario"));
                    h.setNombreTarea(rs.getString("NombreTarea"));
                    lista.add(h);
                }
            }
        }
        return lista;
    }
}
