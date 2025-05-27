package model.dao;

import java.sql.SQLException;

public interface TokenDao {
    void saveToken(int userId, String token) throws SQLException;
    boolean validateToken(int userId, String token) throws SQLException;
    void cleanupExpiredTokens() throws SQLException;
}