package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import model.dao.UserDao;
import model.entity.User;
import util.DB;

/**
 * Implementação da interface UserDao para manipulação de usuários no banco de dados.
 */
public class UserDaoImpl implements UserDao {

    /**
     * Registra um novo usuário no banco de dados com senha criptografada.
     *
     * @param user O usuário a ser registrado.
     * @throws SQLException Se ocorrer erro durante a inserção no banco.
     */
	@Override
	public void registerUser(User user) throws SQLException {
		String sql = "INSERT INTO app_user (username, password_hash, email) VALUES (?, ?, ?)";

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

			stmt.setString(1, user.getUsername());
			stmt.setString(2, hashedPassword);
			stmt.setString(3, user.getEmail());

			stmt.executeUpdate();
		}
	}

    /**
     * Valida um usuário verificando a senha com hash no banco de dados.
     *
     * @param email Email do usuário.
     * @param password Senha em texto plano a ser verificada.
     * @return true se a senha for válida, false caso contrário.
     * @throws SQLException Se ocorrer erro na consulta ao banco.
     */
	@Override
	public boolean validateUser(String email, String password) throws SQLException {
		String sql = "SELECT password_hash FROM app_user WHERE email = ?";

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);
			var rs = stmt.executeQuery();

			if (rs.next()) {
				String hashedPassword = rs.getString("password_hash");
				return BCrypt.checkpw(password, hashedPassword);
			}
			return false;
		}
	}

    /**
     * Busca todos os usuários registrados no sistema.
     *
     * @return Lista de usuários.
     * @throws SQLException Se ocorrer erro na consulta.
     */
	@Override
	public List<User> findAll() throws SQLException {
		String sql = "SELECT username, email, password_hash FROM app_user";
		List<User> users = new ArrayList<>();

		try (Connection conn = DB.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password_hash"));
				users.add(user);
			}
		}
		return users;
	}

    /**
     * Retorna o ID de um usuário com base no email.
     *
     * @param email Email do usuário.
     * @return ID do usuário como String.
     * @throws SQLException Se o usuário não for encontrado ou ocorrer erro no banco.
     */
	@Override
	public String getUserIdByEmail(String email) throws SQLException {
		String sql = "SELECT id FROM app_user WHERE email = ?";

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("id");
				}
				throw new SQLException("Usuário não encontrado com o email: " + email);
			}
		}
	}
}
