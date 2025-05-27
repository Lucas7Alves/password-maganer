package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.getMessage();
			}
		}
	}

	public static void createSchema() {
		try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

			//table users
			stmt.execute("CREATE TABLE IF NOT EXISTS app_user (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "username VARCHAR(50) UNIQUE NOT NULL," + 
					"password_hash VARCHAR(255) NOT NULL," + 
					"email VARCHAR(100) UNIQUE NOT NULL)");

			//table token
			stmt.execute("CREATE TABLE IF NOT EXISTS two_factor_token (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "user_id INT NOT NULL," + "token VARCHAR(6) NOT NULL," + 
					"expires_at TIMESTAMP NOT NULL," + 
					"FOREIGN KEY (user_id) REFERENCES app_user(id))");
			
			//table data_user
			stmt.execute("CREATE TABLE IF NOT EXISTS password_entry (" +
				    "id INT AUTO_INCREMENT PRIMARY KEY," +
				    "service_name VARCHAR(100) NOT NULL," +
				    "username VARCHAR(100) NOT NULL," +
				    "password_encrypted VARCHAR(255) NOT NULL," +
				    "user_id INT NOT NULL," +
				    "FOREIGN KEY (user_id) REFERENCES app_user(id))");
			System.out.println("Tabela criada ou já existente.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}