package DAO;

import Model.Sprint;
import Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SprintDAO {

    public boolean create(Sprint s) throws Exception {
        String sql = "INSERT INTO Sprints (Id_Proyecto, Nombre, Numero_Sprint, Fecha_Inicio, Fecha_Fin) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, s.getIdProyecto());
            ps.setString(2, s.getNombre());
            ps.setInt(3, s.getNumeroSprint() != null ? s.getNumeroSprint() : 1);
            ps.setDate(4, s.getFechaInicio());
            ps.setDate(5, s.getFechaFin());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setIdSprint(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public List<Sprint> findByProyecto(int idProyecto) throws Exception {
        List<Sprint> lista = new ArrayList<>();
        String sql = "SELECT * FROM Sprints WHERE Id_Proyecto = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sprint s = new Sprint();
                    s.setIdSprint(rs.getInt("Id_Sprint"));
                    s.setIdProyecto(rs.getInt("Id_Proyecto"));
                    s.setNombre(rs.getString("Nombre"));
                    s.setNumeroSprint(rs.getInt("Numero_Sprint"));
                    s.setFechaInicio(rs.getDate("Fecha_Inicio"));
                    s.setFechaFin(rs.getDate("Fecha_Fin"));
                    lista.add(s);
                }
            }
        }
        return lista;
    }

    public List<Sprint> listar(int idUsuario) {
        List<Sprint> lista = new ArrayList<>();
        String sql = "SELECT s.* FROM Sprints s "
                + "INNER JOIN Proyectos p ON s.Id_Proyecto = p.Id_Proyecto "
                + "WHERE p.Id_Usuario = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sprint s = new Sprint();
                s.setIdSprint(rs.getInt("Id_Sprint"));
                s.setNombre(rs.getString("Nombre"));
                s.setIdProyecto(rs.getInt("Id_Proyecto"));
                s.setNumeroSprint(rs.getInt("Numero_Sprint"));
                s.setFechaInicio(rs.getDate("Fecha_Inicio"));
                s.setFechaFin(rs.getDate("Fecha_Fin"));
                lista.add(s);
            }
        } catch (Exception e) {
            System.out.println("Error al listar sprints: " + e.getMessage());
        }
        return lista;
    }

    public boolean delete(int idSprint) throws Exception {
        String sql = "DELETE FROM Sprints WHERE Id_Sprint = ?";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idSprint);
            return ps.executeUpdate() > 0;
        }
    }
}
