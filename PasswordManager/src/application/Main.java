package application;

import java.sql.SQLException;

import model.dao.impl.PasswordDaoH2;
import model.entity.PasswordEntry;
import util.DB;

public class Main {
	public static void main(String[] args) {
		try {
			DB.getConnection();
			DB.createSchema();
			
			PasswordEntry pe = new PasswordEntry("Gmail", "aloha@gmail.com", "123");
			
			PasswordDaoH2 pd2 = new PasswordDaoH2();
			
			pd2.save(pe);
			pd2.deleteById(1);
			pd2.deleteById(2);
			pd2.deleteById(3);
			System.out.println(pd2.findAll());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeConnection();
		}
	}
}
