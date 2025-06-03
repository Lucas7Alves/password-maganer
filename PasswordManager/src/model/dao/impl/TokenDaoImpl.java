package model.dao.impl;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import model.dao.TokenDao;
import util.DB;

/**
 * Implementação do DAO de tokens de autenticação de dois fatores.
 */
public class TokenDaoImpl implements TokenDao {

    private static final int TOKEN_EXPIRATION_MINUTES = 5;

    /**
     * Salva um token de autenticação com tempo de expiração.
     * @param userId ID do usuário
     * @param token Token gerado
     * @throws SQLException caso ocorra erro ao salvar
     */
    @Override
    public void saveToken(String userId, String token) throws SQLException {
        String sql = "INSERT INTO two_factor_token (user_id, token, expires_at) VALUES (?, ?, ?)";
        LocalDateTime expiresAt = LocalDateTime.now().plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES);

        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, token);
            stmt.setTimestamp(3, Timestamp.valueOf(expiresAt));
            stmt.executeUpdate();
        }
    }

    /**
     * Valida se um token ainda é válido para o usuário.
     * @param userId ID do usuário
     * @param token Token informado
     * @return true se válido, false caso contrário
     * @throws SQLException caso ocorra erro na validação
     */
    @Override
    public boolean validateToken(String userId, String token) throws SQLException {
        cleanupExpiredTokens();

        String sql = "SELECT 1 FROM two_factor_token WHERE user_id = ? AND token = ? AND expires_at > CURRENT_TIMESTAMP";

        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, token);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Remove todos os tokens expirados do banco de dados.
     * @throws SQLException caso ocorra erro na remoção
     */
    @Override
    public void cleanupExpiredTokens() throws SQLException {
        String sql = "DELETE FROM two_factor_token WHERE expires_at <= CURRENT_TIMESTAMP";

        try (Connection conn = DB.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }
}
