package kits.personal.service;

import java.sql.Date;
import java.util.List;

import kits.personal.dto.BookDto;
import kits.personal.entity.User;

public interface UserService {
	User checkLogin(String username, String password);
	User executeLogin();
	int signUp();
	void showAllAvailable();
	void showUserMenu();
	boolean borrow(int id);
	List<BookDto> search();
	List<BookDto> showBorrowBook(int userId);
	Date getCurrentDate();
	boolean returnBook();
	int updateUser(User user);
	void saveBorrow();
	long diffDays(Date borrowDate, Date returnDate);
	String isContinue(String string);
	boolean checkRegDate(User user) ;
	void saveFeeHistory();
}
