package model.dao;

import java.util.List;

import model.entity.PasswordEntry;

public interface PasswordDao {
	
	void save(PasswordEntry entry, String userId);
	List<PasswordEntry> findAllByUserId(int userId);
	boolean deleteByServiceName(int userId, String serviceName);
}
