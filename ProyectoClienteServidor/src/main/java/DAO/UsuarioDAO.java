package DAO;

import Model.Usuario;
import Util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean create(Usuario u) throws Exception {
        String sql = "INSERT INTO Usuarios (Id_Rol, Nombre, Correo, Contraseña) VALUES (?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, u.getIdRol());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getCorreo());
            ps.setString(4, u.getContrasena());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                return false;
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    u.setIdUsuario(rs.getInt(1));
                }
            }
            return true;
        }
    }

    public Usuario findByCorreo(String correo) throws Exception {
        String sql = "SELECT * FROM Usuarios WHERE Correo = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("Id_Usuario"));
                    u.setIdRol(rs.getInt("Id_Rol"));
                    u.setNombre(rs.getString("Nombre"));
                    u.setCorreo(rs.getString("Correo"));
                    u.setContrasena(rs.getString("Contraseña"));
                    return u;
                }
                return null;
            }
        }
    }

    public Usuario findByNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM Usuarios WHERE Nombre = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("Id_Usuario"));
                    u.setIdRol(rs.getInt("Id_Rol"));
                    u.setNombre(rs.getString("Nombre"));
                    u.setCorreo(rs.getString("Correo"));
                    u.setContrasena(rs.getString("Contraseña"));
                    return u;
                }
                return null;
            }
        }
    }

    public List<Usuario> findAll() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";
        try (Connection c = DBUtil.getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("Id_Usuario"));
                u.setIdRol(rs.getInt("Id_Rol"));
                u.setNombre(rs.getString("Nombre"));
                u.setCorreo(rs.getString("Correo"));
                u.setContrasena(rs.getString("Contraseña"));
                lista.add(u);
            }
        }
        return lista;
    }
}
