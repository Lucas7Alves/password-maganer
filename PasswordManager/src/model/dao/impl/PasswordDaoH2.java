package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.dao.PasswordDao;
import model.entity.PasswordEntry;
import service.PasswordEncrypter;
import util.DB;

public class PasswordDaoH2 implements PasswordDao {
	
	
	@Override
	public void save(PasswordEntry entry, String userId) {
		String sql = "INSERT INTO password_entry (service_name, username, password_encrypted, user_id) VALUES (?, ?, ?, ?)";

		try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, entry.getServiceName());
			stmt.setString(2, entry.getUsername());
			stmt.setString(3, PasswordEncrypter.encrypt(entry.getPasswordEncrypted())); 
			stmt.setInt(4, Integer.parseInt(userId));
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PasswordEntry> findAllByUserId(String userId) {
		List<PasswordEntry> list = new ArrayList<>();
		String sql = "SELECT * FROM password_entry WHERE user_id = ?";

		try (Connection conn = DB.getConnection();
			 PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String decryptedPassword = PasswordEncrypter.decrypt(rs.getString("password_encrypted")); // descriptografar

				PasswordEntry pe = new PasswordEntry(
					rs.getString("service_name"),
					rs.getString("username"),
					decryptedPassword
				);
				list.add(pe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

    @Override
    public boolean deleteByServiceName(String userId, String serviceName) {
    	String sql = "DELETE FROM password_entry WHERE user_id = ? AND service_name = ?";
    	try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
    		stmt.setString(1, userId);
    		stmt.setString(2, serviceName);
    		return stmt.executeUpdate() > 0;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
		return false;
	}
}
