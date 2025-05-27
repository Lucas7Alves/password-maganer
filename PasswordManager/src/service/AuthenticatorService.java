package service;

import java.security.SecureRandom;
import java.sql.SQLException;

import model.dao.impl.TokenDaoImpl;
import model.dao.impl.UserDaoImpl;

public class AuthenticatorService {

	private final SecureRandom random = new SecureRandom();
	private final TokenDaoImpl tokenDao = new TokenDaoImpl();
	private final UserDaoImpl userDao = new UserDaoImpl();

	public String generateToken(String email) {
		int token = 100000 + random.nextInt(900000);
		String strToken = null;
		int userId;
		try {
			userId = userDao.getUserIdByEmail(email);

			strToken = String.valueOf(token);
			tokenDao.saveToken(userId, strToken);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return strToken;
	}

	public boolean validateToken(String email, String token) {
		try {
			int userId = userDao.getUserIdByEmail(email);
			return tokenDao.validateToken(userId, token);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
