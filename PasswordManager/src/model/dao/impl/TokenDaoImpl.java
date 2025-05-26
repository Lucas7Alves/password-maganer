package model.dao.impl;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import model.dao.TokenDao;
import util.DB;

public class TokenDaoImpl implements TokenDao {

	private static final int TOKEN_EXPIRATION_MINUTES = 5;

	@Override
	public void saveToken(int userId, String token) throws SQLException {
		String sql = "INSERT INTO two_factor_token (user_id, token, expires_at) VALUES (?, ?, ?)";

		LocalDateTime expiresAt = LocalDateTime.now().plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES);

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId);
			stmt.setString(2, token);
			stmt.setTimestamp(3, Timestamp.valueOf(expiresAt));

			stmt.executeUpdate();
		}
	}

	@Override
	public boolean validateToken(int userId, String token) throws SQLException {
		cleanupExpiredTokens();

		String sql = "SELECT 1 FROM two_factor_token WHERE user_id = ? AND token = ? AND expires_at > CURRENT_TIMESTAMP";

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId);
			stmt.setString(2, token);

			try (ResultSet rs = stmt.executeQuery()) {
				return rs.next();
			}
		}
	}

	@Override
	public void cleanupExpiredTokens() throws SQLException {
		String sql = "DELETE FROM two_factor_token WHERE expires_at <= CURRENT_TIMESTAMP";

		try (Connection conn = DB.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.executeUpdate(sql);
		}
	}

	@Override
	public int getUserIdByEmail(String email) throws SQLException {
		String sql = "SELECT id FROM app_user WHERE email = ?";

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
				throw new SQLException("Usuário não encontrado com o email: " + email);
			}
		}
	}
}