package DAO;

import Model.Mensaje;
import Util.DBUtil;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author landr
 */
public class MensajeDAO {

    public boolean create(Mensaje m) throws Exception {
        String sql = "INSERT INTO Mensajes (Id_Proyecto, Id_Usuario, Mensaje, Fecha_Envio) VALUES (?, ?, ?, NOW())";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, m.getIdProyecto());
            ps.setInt(2, m.getIdUsuario());
            ps.setString(3, m.getMensaje());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    m.setIdMensaje(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public List<Mensaje> findByProyecto(int idProyecto) throws Exception {
        List<Mensaje> lista = new ArrayList<>();
        String sql = "SELECT m.*, u.Nombre as NombreUsuario " + "FROM Mensajes m " + "INNER JOIN Usuarios u ON m.Id_Usuario = u.Id_Usuario " + "WHERE m.Id_Proyecto = ? " + "ORDER BY m.Fecha_Envio ASC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mensaje m = new Mensaje();
                    m.setIdMensaje(rs.getInt("Id_Mensaje"));
                    m.setIdProyecto(rs.getInt("Id_Proyecto"));
                    m.setIdUsuario(rs.getInt("Id_Usuario"));
                    m.setMensaje(rs.getString("Mensaje"));
                    m.setFechaEnvio(rs.getTimestamp("Fecha_Envio"));
                    m.setNombreUsuario(rs.getString("NombreUsuario"));
                    lista.add(m);
                }
            }
        }
        return lista;
    }

    public Timestamp ultimoMensaje(int idProyecto) throws Exception {
        String sql = "SELECT MAX(Fecha_Envio) as ultimo FROM Mensajes WHERE Id_Proyecto = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getTimestamp("ultimo");
                }
                return null;
            }
        }
    }
}
