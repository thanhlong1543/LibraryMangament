package kits.personal.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import kits.personal.dto.BookDto;
import kits.personal.dto.LoanDto;
import kits.personal.entity.Author;
import kits.personal.entity.Book;
import kits.personal.entity.Publisher;
import kits.personal.entity.Role;
import kits.personal.entity.User;
import kits.personal.repository.AuthorRepository;
import kits.personal.repository.BookRepository;
import kits.personal.repository.LoanRepository;
import kits.personal.repository.PublisherRepository;
import kits.personal.repository.RoleRepository;
import kits.personal.repository.UserRepository;
import kits.personal.repository.impl.AuthorRepositoryImpl;
import kits.personal.repository.impl.BookRepositoryImpl;
import kits.personal.repository.impl.LoanRepositoryImpl;
import kits.personal.repository.impl.PublisherRepositoryImpl;
import kits.personal.repository.impl.RoleRepositoryImpl;
import kits.personal.repository.impl.UserRepositoryImpl;
import kits.personal.service.LibrarianService;

public class LibrarianServiceImpl extends UserServiceImpl implements LibrarianService {
	Scanner scanner = new Scanner(System.in);
 	private AuthorRepository authorRepository = new AuthorRepositoryImpl();
 	private PublisherRepository publisherRepository = new PublisherRepositoryImpl();
 	private RoleRepository roleRepository = new RoleRepositoryImpl();
 	private BookRepository bookRepository = new BookRepositoryImpl();
 	private UserRepository userRepository = new UserRepositoryImpl();
 	private LoanRepository loanRepository = new LoanRepositoryImpl();
	@Override
	public void showAllAvailable() {
		super.showAllAvailable();
	}

	@Override
	public boolean borrow(int userId) {
		// TODO Auto-generated method stub
		return super.borrow(userId);
	}

	@Override
	public Date getCurrentDate() {
		// TODO Auto-generated method stub
		return super.getCurrentDate();
	}

	@Override
	public boolean returnBook() {
		// TODO Auto-generated method stub
		return super.returnBook();
	}

	@Override
	public List<BookDto> showBorrowBook(int userId) {
		// TODO Auto-generated method stub
		return super.showBorrowBook(userId);
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return super.updateUser(user);
	}

	@Override
	public void showBookList() {

	}

	@Override
	public boolean createUser() {
		return false;
	}

	@Override
	public boolean createBook() {
		return false;
	}

	@Override
	public boolean updateBook() {
		return false;
	}

	@Override
	public String isContinue(String string) {
		// TODO Auto-generated method stub
		return super.isContinue(string);
	}
	@Override
	public boolean deleteUser() {
		return false;
	}

	@Override
	public boolean deleteBook() {
		return false;
	}

	@Override
	public List<BookDto> showAllBorrowBook() {

		return null;
	}

	public void showMenu() {
		System.out.println("What service do you want to use? Choose a number");
		System.out.println("1. Show available books");
		System.out.println("2. Find book by name, author or publisher");
		System.out.println("3. Show borrowed book");
		System.out.println("4. Update information");
		System.out.println("5. Show all book");
		System.out.println("6. Show all user");
		System.out.println("7. Show all borrowed history");
		System.out.println("8. Show borrowed book by user");
		System.out.println("9. Cancel");
	}

	@Override
	public void showBorrowHistory() {
		System.out.printf("%-10s%-30s%-30s%-30s%-20s%-20s%s\n", "ID", "Username", "Book Name", "Author", "Publisher",
				"Loan Date", "Return");

		for (BookDto item : bookRepository.findBorrowHistory()) {
			System.out.printf("%-10s%-30s%-30s%-30s%-20s%-20s%s\n", item.getId(), item.getUsername(), item.getName(),
					item.getAuthor(), item.getPublisher(), item.getLoanDate(), item.getReturnDate());
		}
	}

	@Override
	public void crudBook() {
		int choose = 0;
		while (choose != 4) {
			showAllBook();
			
			
			System.out.println("Choose a number");

			System.out.println("1. Create new book");
			System.out.println("2. Update book");
			System.out.println("3. Delete book from system");
			System.out.println("4. Cancel");

			choose = Integer.parseInt(scanner.nextLine());
			
			
			
			System.out.println("List author");
			System.out.printf("%-10s%s\n", "ID", "Name");
			for (Author item : authorRepository.findAll()) {
				System.out.printf("%-10s%s\n", item.getId(), item.getName());
			}

			System.out.println("Do you want to add new author?");
			if (scanner.nextLine().equals("YES")) {
				System.out.print("Input author's name: ");
				authorRepository.add(scanner.nextLine());
			}

			System.out.println("List publisher");
			System.out.printf("%-10s%s\n", "ID", "Name");
			for (Publisher item : publisherRepository.findAll()) {
				System.out.printf("%-10s%s\n", item.getId(), item.getName());
			}

			System.out.println("Do you want to add new publisher?");
			if (scanner.nextLine().equals("YES")) {
				System.out.print("Input publisher's name: ");
				publisherRepository.add(scanner.nextLine());
			}

			
			if (choose == 4)
				break;
			Book book = new Book();
			switch (choose) {
			case 1:

				System.out.print("Book name: ");
				book.setName(scanner.nextLine());
				System.out.print("Author id: ");

				book.setAuthorId(Integer.parseInt(scanner.nextLine()));
				System.out.print("Publisher id: ");
				book.setPublisherId(Integer.parseInt(scanner.nextLine()));
				bookRepository.add(book);
				break;
			case 2:

				System.out.print("Book id: ");
				book = bookRepository.findById(Integer.parseInt(scanner.nextLine()));

				System.out.print("Book name: ");
				book.setName(scanner.nextLine());
				System.out.print("Author id: ");
				book.setAuthorId(Integer.parseInt(scanner.nextLine()));
				System.out.print("Publisher id: ");
				book.setPublisherId(Integer.parseInt(scanner.nextLine()));
				bookRepository.update(book);
				break;
			case 3:
				System.out.println("Do you want to delete book ?");
				if (scanner.nextLine().equals("YES")) {
					System.out.print("Input book id to delete: ");
					bookRepository.delete(Integer.parseInt(scanner.nextLine()));
				}
			default:
				break;
			}
		}
	}

	@Override
	public void showAllBook() {
		System.out.printf("%-10s%-30s%-30s%s\n", "ID", "Name", "Author", "Publisher");
		for (BookDto item : bookRepository.findAllAvailable()) {
			System.out.printf("%-10s%-30s%-30s%s\n", item.getId(), item.getName(), item.getAuthor(),
					item.getPublisher());
		}
	}

	@Override
	public int signUp() {
		return super.signUp();
	}

	@Override
	public void crudUser() {

		int choose = 0;
		while (choose != 4) {
			showAllUser();
			System.out.println("List role");
			System.out.printf("%-10s%s\n", "ID", "Name");
			for (Role item : roleRepository.findAll()) {
				System.out.printf("%-10s%s\n", item.getId(), item.getName());
			}

			System.out.println("Choose a number");
			System.out.println("1. Create new user");
			System.out.println("2. Update user");
			System.out.println("3. Delete user from system");
			System.out.println("4. Cancel");

			choose = Integer.parseInt(scanner.nextLine());

			switch (choose) {
			case 1:
				signUp();
				break;
			case 2:

				System.out.print("Username: ");
				String username = scanner.nextLine();
				System.out.print("Password: ");
				String password = scanner.nextLine();
				System.out.print("Identification number: ");
				String idnum = scanner.nextLine();
				System.out.print("Phone: ");
				String phone = scanner.nextLine();
				try {
					userRepository.update(new User(username, password, phone, idnum));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("Do you want to delete user ?");
				if (scanner.nextLine().equals("YES")) {
					System.out.print("Input user id to delete: ");
					userRepository.delete(Integer.parseInt(scanner.nextLine()));
				}
				break;
			default:
				break;
			}
		}
	}

	@Override
	public List<BookDto> search() {
		// TODO Auto-generated method stub
		return super.search();
	}

	@Override
	public void showAllUser() {
		System.out.printf("%-10s%-30s%-20s%-20s%s\n", "ID", "Name", "Phone", "ID Num", "Role");

		for (User item : userRepository.findAll()) {
			System.out.printf("%-10s%-30s%-20s%-20s%s\n", item.getId(), item.getName(), item.getPhone(),
					item.getIdNum(), item.getRoleId());
		}
	}

	@Override
	public void showBorrowByUser() {

		System.out.println("Input user id:");
		int id = Integer.parseInt(scanner.nextLine());
		String isReturn= "No";
		System.out.printf("%-10s%-30s%-20s%-20s%s\n", "ID", "User name", "Book name", "Loan Date", "Return");
		
		for (LoanDto item : loanRepository.findByUser(id)) {
			if(item.getIsActive()==0) {
				isReturn = "Yes";
			}
			System.out.printf("%-10s%-30s%-20s%-20s%s\n", item.getId(), item.getUserName(), item.getBookName(),
					item.getLoanDate(), isReturn);
		}

	}
}
