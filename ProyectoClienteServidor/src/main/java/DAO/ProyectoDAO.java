package DAO;

import Model.Proyecto;
import Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {
    // ==================================
    // CREAR PROYECTO
    // ==================================

    public boolean create(Proyecto p) throws Exception {
        String sql = "INSERT INTO Proyectos (Id_Usuario, Nombre, Descripcion, Fecha_Inicio, Estado) VALUES (?, ?, ?, CURDATE(), 'Activo')";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getIdUsuario());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setIdProyecto(rs.getInt(1));
                }
            }
        }
        return true;
    }

    public List<Proyecto> findByUsuario(int idUsuario) throws Exception {
        return findAll();
    }

    public List<Proyecto> findAll() throws Exception {
        List<Proyecto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Proyectos ORDER BY Fecha_Inicio DESC";
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Proyecto p = new Proyecto();
                p.setIdProyecto(rs.getInt("Id_Proyecto"));
                p.setIdUsuario(rs.getInt("Id_Usuario"));
                p.setNombre(rs.getString("Nombre"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setFechaInicio(rs.getDate("Fecha_Inicio"));
                p.setFechaFin(rs.getDate("Fecha_Fin"));
                p.setEstado(rs.getString("Estado"));
                lista.add(p);
            }
        }
        return lista;
    }

    public Proyecto findById(int idProyecto) throws Exception {
        String sql = "SELECT * FROM Proyectos WHERE Id_Proyecto = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Proyecto p = new Proyecto();
                    p.setIdProyecto(rs.getInt("Id_Proyecto"));
                    p.setIdUsuario(rs.getInt("Id_Usuario"));
                    p.setNombre(rs.getString("Nombre"));
                    p.setDescripcion(rs.getString("Descripcion"));
                    p.setFechaInicio(rs.getDate("Fecha_Inicio"));
                    p.setFechaFin(rs.getDate("Fecha_Fin"));
                    p.setEstado(rs.getString("Estado"));
                    return p;
                }
                return null;
            }
        }
    }

    public boolean delete(int idProyecto) throws Exception {
        String sql = "DELETE FROM Proyectos WHERE Id_Proyecto = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Proyecto> listar(int idUsuario) {
        try {
            return findAll();
        } catch (Exception e) {
            System.out.println("Error al listar proyectos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean crear(Proyecto p) {
        try {
            return create(p);
        } catch (Exception e) {
            System.out.println("Error al crear proyecto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            return delete(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar proyecto: " + e.getMessage());
            return false;
        }
    }
}
