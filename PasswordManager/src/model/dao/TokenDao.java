package model.dao;

import java.sql.SQLException;

public interface TokenDao {
    void cleanupExpiredTokens() throws SQLException;
	boolean validateToken(String userId, String token) throws SQLException;
	void saveToken(String userId, String token) throws SQLException;
}