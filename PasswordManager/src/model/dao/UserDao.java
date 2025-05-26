package model.dao;

import java.sql.SQLException;

import model.entity.User;

public interface UserDao {

	void registerUser(User user) throws SQLException;
	boolean validateUser(String username, String password) throws SQLException;
}
