package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import model.dao.UserDao;
import model.entity.User;
import util.DB;

public class UserDaoImpl implements UserDao {
	
	@Override
    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO app_user (username, password_hash, email) VALUES (?, ?, ?)";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
           
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            
            stmt.setString(1, user.getUsername());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getEmail());
            
            stmt.executeUpdate();
        }
    }
    
	@Override
    public boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT password_hash FROM app_user WHERE username = ?";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            var rs = stmt.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("password_hash");
                return BCrypt.checkpw(password, hashedPassword); 
            }
            return false; 
        }
    }

}
