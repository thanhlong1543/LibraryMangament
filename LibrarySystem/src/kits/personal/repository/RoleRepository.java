package kits.personal.repository;

import java.util.List;

import kits.personal.entity.Role;

public interface RoleRepository {
	int add(String name);
	List<Role> findAll();
	int update(Role role);
	int delete(int id);
}
