package kits.personal.repository;

import java.util.List;

import kits.personal.dto.BookDto;
import kits.personal.entity.Book;

public interface BookRepository {
	List<Book> findAll();
	Book findById(int id);
	void update(Book book);
	void delete(int id);
	List<BookDto> findAllAvailable();
	void add(Book book);
	int setAvailability(int available,int id);
	List<BookDto> findByBookInfo(String keyword);
	List<BookDto> findBorrowed(int userId );
	List<BookDto> findAllBorrowed();
	List<BookDto> findBorrowHistory();
	List<BookDto> findAllBook();
}
