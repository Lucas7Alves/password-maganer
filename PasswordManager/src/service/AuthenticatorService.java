package service;

import java.security.SecureRandom;
import java.sql.SQLException;

import model.dao.impl.TokenDaoImpl;
import model.dao.impl.UserDaoImpl;

/**
 * Responsável pela geração e validação de tokens de autenticação 2FA.
 */
public class AuthenticatorService {

    private final SecureRandom random = new SecureRandom();
    private final TokenDaoImpl tokenDao = new TokenDaoImpl();
    private final UserDaoImpl userDao = new UserDaoImpl();

    /**
     * Gera um token aleatório de 6 dígitos e o associa a um usuário.
     * @param email E-mail do usuário
     * @return Token gerado em forma de string
     */
    public String generateToken(String email) {
        int token = 100000 + random.nextInt(900000);
        String strToken = null;
        String userId;
        try {
            userId = userDao.getUserIdByEmail(email);
            strToken = String.valueOf(token);
            tokenDao.saveToken(userId, strToken);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return strToken;
    }

    /**
     * Valida se o token fornecido é válido para o usuário.
     * @param userUid ID do usuário
     * @param token Token a ser validado
     * @return true se o token for válido, false caso contrário
     */
    public boolean validateToken(String userUid, String token) {
        try {
            return tokenDao.validateToken(userUid, token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
