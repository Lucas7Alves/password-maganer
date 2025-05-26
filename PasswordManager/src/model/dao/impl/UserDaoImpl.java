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
    public boolean validateUser(String email, String password) throws SQLException {
        String sql = "SELECT password_hash FROM app_user WHERE email = ?";
        
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            var rs = stmt.executeQuery();
            
            if (rs.next()) {
                String hashedPassword = rs.getString("password_hash");
                return BCrypt.checkpw(password, hashedPassword); 
            }
            return false; 
        }
    }
	
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


}
