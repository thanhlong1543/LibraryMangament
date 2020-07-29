package kits.personal.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import kits.personal.dto.BookDto;
import kits.personal.dto.FeeDto;
import kits.personal.entity.Book;
import kits.personal.entity.Fee;
import kits.personal.entity.Loan;
import kits.personal.entity.User;
import kits.personal.repository.BookRepository;
import kits.personal.repository.FeeRepository;
import kits.personal.repository.LoanRepository;
import kits.personal.repository.UserRepository;
import kits.personal.repository.impl.BookRepositoryImpl;
import kits.personal.repository.impl.FeeRepositoryImpl;
import kits.personal.repository.impl.LoanRepositoryImpl;
import kits.personal.repository.impl.UserRepositoryImpl;
import kits.personal.service.UserService;
import kits.personal.thread.ThreadCountDown;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository = new UserRepositoryImpl();
	private BookRepository bookRepository = new BookRepositoryImpl();
	private LoanRepository loanRepository = new LoanRepositoryImpl();
	private FeeRepository feeRepository = new FeeRepositoryImpl();
	Scanner scanner = new Scanner(System.in);

	@Override
	public User checkLogin(String username, String password) {

		User user = userRepository.checkUsername(username);
		if (user == null) {
			System.out.println("Invalid username or password");
			return null;
		}

		if (user.getPassword().equals(password)) {
			System.out.println("Login success");
			System.out.println("Welcome: " + user.getName());
			return user;
		}

		System.out.println("Invalid username or password");
		return null;
	}

	@Override
	public User executeLogin() {

		System.out.print("Username: ");

		String username = scanner.nextLine();

		System.out.print("Password: ");

		String password = scanner.nextLine();

		return checkLogin(username, password);
	}

	@Override
	public int signUp() {
		System.out.println("Sign up to continue");
		System.out.print("Username: ");
		String username = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();
		System.out.print("Identification number: ");
		String idnum = scanner.nextLine();
		System.out.print("Phone: ");
		String phone = scanner.nextLine();
		int result = 0;
		try {
			result = userRepository.add(new User(username, password, phone, idnum));
		} catch (SQLIntegrityConstraintViolationException e) {

			System.out.println("Sign up fail! Username is already taken");
		} catch (SQLSyntaxErrorException e) {
			System.out.println("Invalid Identification number or phone number");
		} catch (SQLException e) {
			System.out.println("Invalid information. Please sign up again");
		}
		return result;
	}

	@Override
	public void showUserMenu() {
		System.out.println("What service do you want to use? Choose a number");
		System.out.println("1. Show available books");
		System.out.println("2. Find book by name, author or publisher");
		System.out.println("3. Show borrowed book");
		System.out.println("4. Update information");
		System.out.println("5. Leave");
	}

	@Override
	public void showAllAvailable() {
		System.out.printf("%-10s%-30s%-30s%s\n", "ID", "Name", "Author", "Publisher");
		for (BookDto item : bookRepository.findAllAvailable()) {
			System.out.printf("%-10s%-30s%-30s%s\n", item.getId(), item.getName(), item.getAuthor(),
					item.getPublisher());
		}
	}

	@Override
	public boolean borrow(int userId) {
		Loan loan = new Loan();
		int bookId = 0;
		while (bookId == 0) {
			try {
				System.out.println("Choose book id to borrow");
				bookId = Integer.parseInt(scanner.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("Choose correct book id");
			} catch (Exception e) {
				System.out.println("Some thing was wrong choose again");
			}
		}

		loan.setBookId(bookId);
		loan.setUserId(userId);
		Book book = bookRepository.findById(bookId);
		if (book.getAvailable() == 0) {
			System.out.println("This book is not available choose another one");
			return false;
		}
		int result = loanRepository.add(loan);
		if (result != 0) {
			ThreadCountDown tcd = new ThreadCountDown();
			System.out.println();
			saveBorrow();
			bookRepository.setAvailability(0, bookId);
			System.out.println("Borrow success\nYou borrow: " + book.getName());
			tcd.start();
			return true;
		}
		return false;
	}

	@Override
	public List<BookDto> search() {
		System.out.print("Search: ");
		String keyword = scanner.nextLine();
		System.out.printf("%-10s%-30s%-30s%s\n", "ID", "Name", "Author", "Publisher");
		List<BookDto> list = bookRepository.findByBookInfo(keyword);
		if (list.isEmpty()) {
			System.out.println("Do not find any book!");
			return null;
		}
		for (BookDto item : list) {
			System.out.printf("%-10s%-30s%-30s%s\n", item.getId(), item.getName(), item.getAuthor(),
					item.getPublisher());
		}
		return list;
	}

	@Override
	public List<BookDto> showBorrowBook(int userId) {
		List<BookDto> list = bookRepository.findBorrowed(userId);
		float fees = 0;
		if (list.isEmpty()) {
			return list;
		}
		System.out.println("List of borrowed books");
		System.out.printf("%-10s%-30s%-20s%-20s%s\n", "ID", "Name", "Author", "Publisher", "Loan Date");
		for (BookDto item : list) {
			System.out.printf("%-10s%-30s%-20s%-20s%s\n", item.getId(), item.getName(), item.getAuthor(),
					item.getPublisher(), item.getLoanDate());
			long diffDays = diffDays(item.getLoanDate(), getCurrentDate());
			if (diffDays > 30) {
				System.out.println(item.getName() + " is " + (diffDays - 30) + " days late");
				fees = (float) (0.25 * (diffDays - 30));
				feeRepository.add(new Fee("Late Fee", userId, fees));
				while (fees > 0) {
					System.out.println("You have to pay $" + fees + " late fees");
					System.out.println("Please input money");
					fees = fees - Float.valueOf(scanner.nextLine());
				}
				if (fees < 0) {
					System.out.println("Take your change: $" + Math.abs(fees));
				}
				saveFeeHistory();
			}
		}
		return list;
	}

	@Override
	public boolean checkRegDate(User user) {
		String inputChoose;
		long diffDays = diffDays(user.getRegDate(), getCurrentDate());
		if (diffDays > 30) {
			System.out.println(
					"Your account has expried\n\nPlease extend your account to continue\n\nExtention fee is $20 for each month\n");
			inputChoose = isContinue("extend your account");
			if (inputChoose.equalsIgnoreCase("NO"))
				return false;

			int extendFee = 0;

			while (extendFee < 20) {
				System.out.println("Please input money!");
				extendFee = extendFee + Integer.parseInt(scanner.nextLine());
				System.out.println("You input: " + extendFee + "\nFee is $20");
			}

			System.out.println("Extend success!!!");

			if (extendFee > 20) {
				System.out.println("Take your change: $" + (extendFee - 20));
			}
			feeRepository.add(new Fee("Extend Fee", user.getId(), 20));
			saveFeeHistory();
			user.setRegDate(getCurrentDate());
			userRepository.extendReg(user);
		}
		return true;
	}

	@Override
	public boolean returnBook() {
		int bookId = 0;
		while (bookId == 0) {
			try {
				System.out.println("Choose book id to return");
				bookId = Integer.parseInt(scanner.nextLine());
			} catch (InputMismatchException e) {
				System.out.println("Choose correct book id");
			} catch (Exception e) {
				System.out.println("Some thing was wrong choose again");
			}
		}
		int result = loanRepository.deActivate(bookId, getCurrentDate());

		if (result != -1) {
			bookRepository.setAvailability(1, bookId);
			System.out.println("Return success\nYou return: " + bookRepository.findById(bookId).getName());
			return true;
		}
		return false;
	}

	@Override
	public Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String loanDate = year + "-" + month + "-" + day;
		return Date.valueOf(loanDate);
	}

	@Override
	public int updateUser(User user) {
		int result = 0;
		System.out.printf("%-20s%-15s%-15s%s\n", "Name", "Password", "IdNumber", "PhoneNo");
		System.out.printf("%-20s%-15s%-15s%s\n", user.getName(), user.getPassword(), user.getIdNum(),
				user.getPhone());
		System.out.println();
		System.out.print("Username: ");
		user.setName(scanner.nextLine());
		System.out.print("Password: ");
		user.setPassword(scanner.nextLine());
		System.out.print("Identification number: ");
		user.setIdNum(scanner.nextLine());
		System.out.print("Phone: ");
		user.setPhone(scanner.nextLine());
		try {
			result  = userRepository.update(user);
			while (result == 0) {
				if (scanner.nextLine().equalsIgnoreCase("NO"))
					break;
				result = updateUser(user);
			}

			if (result != 0) {
				
				System.out.println("Update success");
				System.out.printf("%-20s%-15s%-15s%s\n", "Name", "Password", "IdNumber", "PhoneNo");
				System.out.printf("%-20s%-15s%-15s%s\n", user.getName(), user.getPassword(), user.getIdNum(),
						user.getPhone());
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Update fail! Username is already taken. Please try again. YES or NO");
		} catch (SQLSyntaxErrorException e) {
			System.out.println("Invalid identification number or phone number. Please try again. YES or NO");
		} catch (SQLException e) {
			System.out.println("Invalid information. Please try again. YES or NO");
		} catch (Exception e) {
			System.out.println("Invalid information. Please try again. YES or NO");
		}
		
		return result;
	}

	@Override
	public void saveBorrow() {
		String filedir = "C:/Users/PC12/Desktop/Java/LibrarySystem/borrowhistory/history.txt";

		try {
			FileOutputStream fo = new FileOutputStream(filedir);
			String head = "`````````Borrow history````````````\nID\t\tUsername\t\t\tBook Name\t\t\t\t\tAuthor\t\t\tPublisher\t\t\tLoanDate\t\t\tReturn Date\n";
			String detail = "";
			byte[] bytes = head.getBytes();
			fo.write(bytes);
			for (BookDto item : bookRepository.findBorrowHistory()) {
				detail = detail + item.getId() + "\t\t" + item.getUsername() + "\t\t\t" + item.getName() + "\t\t\t\t"
						+ item.getAuthor() + "\t\t\t\t" + item.getPublisher() + "\t\t\t" + item.getLoanDate() + "\t\t\t"
						+ item.getReturnDate() + "\n";
			}
			bytes = detail.getBytes();
			fo.write(bytes);
			fo.close();
		} catch (FileNotFoundException e) {
			System.out.println("Receipt file not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long diffDays(Date borrowDate, Date returnDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate d1 = LocalDate.parse(df.format(borrowDate), DateTimeFormatter.ISO_LOCAL_DATE);
		LocalDate d2 = LocalDate.parse(df.format(returnDate), DateTimeFormatter.ISO_LOCAL_DATE);
		Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		long diffDays = diff.toDays();
		return diffDays;
	}

	public String isContinue(String string) {
		System.out.println("Do you want to " + string + " ? YES or NO");
		String inputChoose = scanner.nextLine();
		while ((!inputChoose.equalsIgnoreCase("NO") && !inputChoose.equalsIgnoreCase("YES"))) {
			try {
				throw new InputMismatchException("Please choose YES or NO");
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			} finally {
				System.out.println("Do you want to " + string + " ? YES or NO");
				inputChoose = scanner.nextLine();
			}
		}
		return inputChoose;
	}

	@Override
	public void saveFeeHistory() {
		String filedir = "C:/Users/PC12/Desktop/Java/LibrarySystem/borrowhistory/fee.txt";

		try {
			FileOutputStream fo = new FileOutputStream(filedir);
			String head = "`````````Borrow history````````````\nID\t\tFee name\t\tUsername\tMount\t\tDate\n";
			String detail = "";
			byte[] bytes = head.getBytes();
			fo.write(bytes);
			for (FeeDto item : feeRepository.findAll()) {
				detail = detail + item.getId() + "\t\t" + item.getName() + "\t\t" + item.getUserName() + "\t\t" + "$"
						+ item.getMount() + "\t\t" + item.getTranDate() + "\n";
			}
			bytes = detail.getBytes();
			fo.write(bytes);
			fo.close();
		} catch (FileNotFoundException e) {
			System.out.println("Receipt file not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
