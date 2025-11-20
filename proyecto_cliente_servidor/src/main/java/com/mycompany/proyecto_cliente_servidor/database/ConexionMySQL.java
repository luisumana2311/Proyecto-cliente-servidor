package com.mycompany.proyecto_cliente_servidor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {

    private static final String URL = "jdbc:mysql://localhost:3306/kanban?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Mariano23*";

    public static Connection getConnection() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✔ Conexión a MySQL exitosa.");
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a MySQL: " + e.getMessage());
        }

        return conexion;
    }

    public static void main(String[] args) {
        // Test de conexión
        getConnection();
    }
}
