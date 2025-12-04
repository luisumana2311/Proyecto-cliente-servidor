package Util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author landr
 */
public class DBUtil {

    private static String url = "jdbc:mysql://localhost:3306/pizarra_kanban?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static String user = "root";
    private static String password = "luisandres";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar el driver de MySQL", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(url, user, password);
    }
}
