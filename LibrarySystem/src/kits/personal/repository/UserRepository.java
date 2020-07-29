package kits.personal.repository;

import java.sql.SQLException;
import java.util.List;

import kits.personal.entity.User;

public interface UserRepository {
	List<User> findAll();
	User findById(int id);
	int update(User user) throws SQLException;
	void delete(int id);
	int add(User user) throws SQLException;
	User checkUsername(String username);
	int extendReg(User user);
}
