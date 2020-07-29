package kits.personal.repository;

import java.util.List;

import kits.personal.entity.Author;

public interface AuthorRepository {
	int add(String name);
	List<Author> findAll();
	int update(Author author);
	int delete(int id);
}
