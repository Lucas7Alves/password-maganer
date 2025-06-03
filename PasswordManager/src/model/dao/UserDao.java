package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.entity.PasswordEntry;
import model.entity.User;

public interface UserDao {

	void registerUser(User user) throws SQLException;

	boolean validateUser(String username, String password) throws SQLException;

	List<User> findAll() throws SQLException;

	String getUserIdByEmail(String email) throws SQLException;
}
