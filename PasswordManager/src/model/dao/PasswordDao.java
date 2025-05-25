package model.dao;

import java.util.List;

import model.entity.PasswordEntry;

public interface PasswordDao {
	
	void save(PasswordEntry entry);
	List<PasswordEntry> findAll();
	void deleteById(int id);
}
