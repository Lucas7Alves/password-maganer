package model.dao;

import java.util.List;

import model.entity.PasswordEntry;

public interface PasswordDao {
	
	void save(PasswordEntry entry, String userId);
	boolean deleteByServiceName(String userId, String serviceName);
	List<PasswordEntry> findAllByUserId(String userId);
}
