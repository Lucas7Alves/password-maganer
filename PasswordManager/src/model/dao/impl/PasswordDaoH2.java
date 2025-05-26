package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import model.entity.PasswordEntry;
import util.DB;

public class PasswordDaoH2 {
	
	
    public void save(PasswordEntry entry) {
    	
        String sql = "INSERT INTO password_entry (service_name, username, password_encrypted) VALUES (?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entry.getServiceName());
            stmt.setString(2, entry.getUsername());
            
            String hashedPassword = BCrypt.hashpw(entry.getPasswordEncrypted(), BCrypt.gensalt());
            stmt.setString(3, hashedPassword);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public List<PasswordEntry> findAll() {
    	
        List<PasswordEntry> list = new ArrayList<>();
        try (Connection conn = DB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM password_entry")) {
            while (rs.next()) {
                PasswordEntry entry = new PasswordEntry();
                entry.setId(rs.getInt("id"));
                entry.setServiceName(rs.getString("service_name"));
                entry.setUsername(rs.getString("username"));
                entry.setPasswordEncrypted(rs.getString("password_encrypted"));
                list.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public void deleteById(int id) {
    	
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM password_entry WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
