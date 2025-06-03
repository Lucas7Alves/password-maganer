package util;

import java.sql.*;
import java.util.Properties;

/**
 * Classe utilitária para gerenciamento de conexão e schema do banco de dados H2.
 */
public class DB {
    private static Connection conn;

    /**
     * Retorna uma conexão com o banco de dados.
     * @return Conexão ativa
     * @throws SQLException se houver erro na conexão
     */
    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            Properties props = ConfigLoader.getProperties();
            String url = props.getProperty("H2_DB_URL");
            String user = props.getProperty("H2_DB_USER");
            String pass = props.getProperty("H2_DB_PASS");
            conn = DriverManager.getConnection(url, user, pass);
        }
        return conn;
    }

    /**
     * Fecha a conexão com o banco de dados, se existir.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.getMessage(); // Log poderia ser melhorado
            }
        }
    }

    /**
     * Cria as tabelas do schema no banco de dados H2 se ainda não existirem.
     */
    public static void createSchema() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {

            // Tabela de usuários
            stmt.execute("CREATE TABLE IF NOT EXISTS app_user (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password_hash VARCHAR(255) NOT NULL," +
                    "email VARCHAR(100) UNIQUE NOT NULL)");

            // Tabela de tokens 2FA
            stmt.execute("CREATE TABLE IF NOT EXISTS two_factor_token (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT NOT NULL," +
                    "token VARCHAR(6) NOT NULL," +
                    "expires_at TIMESTAMP NOT NULL," +
                    "FOREIGN KEY (user_id) REFERENCES app_user(id))");

            // Tabela de senhas dos usuários
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
