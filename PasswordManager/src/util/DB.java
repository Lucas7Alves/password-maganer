package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            String url = System.getenv("H2_DB_URL");
            String user = System.getenv("H2_DB_USER");
            String pass = System.getenv("H2_DB_PASS");
            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }
}