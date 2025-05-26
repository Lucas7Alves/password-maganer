package service;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;

import model.dao.impl.TokenDaoImpl;

public class AuthenticatorService {

	private final SecureRandom random = new SecureRandom();
	private final TokenDaoImpl tokenDao = new TokenDaoImpl();

	public String generateToken(String email) {
		int token = 100000 + random.nextInt(900000);
		String strToken = null;
		int userId;
		try {
			userId = tokenDao.getUserIdByEmail(email);

			strToken = String.valueOf(token);
			tokenDao.saveToken(userId, strToken);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return strToken;
	}

	public boolean validateToken(String email, String token) {
		try {
			int userId = tokenDao.getUserIdByEmail(email);
			return tokenDao.validateToken(userId, token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
