package kits.personal.repository;

import java.util.List;

import kits.personal.entity.Publisher;

public interface PublisherRepository {
	int add(String name);
	List<Publisher> findAll();
	int update(Publisher publisher);
	int delete(int id);
}
