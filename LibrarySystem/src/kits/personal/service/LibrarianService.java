package kits.personal.service;

import java.sql.Date;
import java.util.List;

import kits.personal.dto.BookDto;
import kits.personal.entity.User;

public interface LibrarianService {
	void showAllAvailable();
	boolean borrow(int id);
	List<BookDto> showBorrowBook(int userId);
	Date getCurrentDate();
	boolean returnBook();
	int updateUser(User user);
	void showBookList();
	boolean createUser();
	boolean createBook();
	boolean updateBook();
	boolean deleteUser();
	boolean deleteBook();
	void showAllUser();
	List<BookDto> showAllBorrowBook();
	void showMenu();
	void showBorrowHistory();
	void crudBook();
	void showAllBook();
	void crudUser();
	void showBorrowByUser();
	List<BookDto> search();
	String isContinue(String string);
}
