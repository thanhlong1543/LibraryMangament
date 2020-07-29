package kits.personal.main;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import kits.personal.dto.BookDto;
import kits.personal.entity.User;
import kits.personal.service.LibrarianService;
import kits.personal.service.UserService;
import kits.personal.service.impl.LibrarianServiceImpl;
import kits.personal.service.impl.UserServiceImpl;

public class LibraryMain {

	public static void main(String[] args) {
		UserService userService = new UserServiceImpl();
		LibrarianService librarianService = new LibrarianServiceImpl();
		User user = null;
		boolean success = false;
		String inputChoose = "";
		Scanner scanner = new Scanner(System.in);
		int countLoginFail = 0;

		// Check login and sign up
		while (user == null) {
			int loginChoose = 0;
			if (countLoginFail > 4) {
				System.out.println("Too many fail attepmts\nPlease try again later");
				break;
			}
			try {
				System.out.println(
						"Please login to continue\n1.Sign in\n2.Sign up\n3.Leave\n----------------------------------");
				loginChoose = Integer.parseInt(scanner.nextLine());
				if (loginChoose < 1 || loginChoose > 3)
					throw new InputMismatchException("Please choose 1,2 or 3");
			} catch (NumberFormatException e) {
				System.out.println("Please input a number");
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}

			if (loginChoose == 3)
				break;
			if (loginChoose == 1) {
				user = userService.executeLogin();
				if (user == null)
					countLoginFail++;
			}
			if (loginChoose == 2) {
				int isSignUp = userService.signUp();
				while (isSignUp == 0) {
					inputChoose = userService.isContinue("sign up again?");
					if (inputChoose.equalsIgnoreCase("NO"))
						break;

					isSignUp = userService.signUp();
				}
			}
		}

		// Loop If role is user
		while (user != null && user.getRoleId() == 1) {

			// Check reg_date
			if (!userService.checkRegDate(user))
				break;

			// Choose service
			userService.showUserMenu();
			int chooseService = 0;

			try {
				chooseService = Integer.parseInt(scanner.nextLine());
				if (chooseService < 1 || chooseService > 5)
					throw new InputMismatchException("Please choose correct number");
			} catch (NumberFormatException e) {
				System.out.println("Please input a number");
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}

			switch (chooseService) {
			case 1:
				// show Available book and borrow
				userService.showAllAvailable();

				inputChoose = userService.isContinue("borrow book");

				if (inputChoose.equalsIgnoreCase("YES")) {
					success = userService.borrow(user.getId());

					while (success == false) {
						System.out.println("Borrow fail! Something was wrong. Try again? YES or NO");

						if (scanner.nextLine().equalsIgnoreCase("NO"))
							break;
						success = userService.borrow(user.getId());
					}
				}

				break;
			case 2:
				// find book by book's info
				List<BookDto> list = userService.search();
				if (list == null)
					break;

				inputChoose = userService.isContinue("borrow book");
				if (inputChoose.equalsIgnoreCase("YES")) {
					success = userService.borrow(user.getId());

					while (success == false) {
						System.out.println("Borrow fail! Something was wrong. Try again? YES or NO");

						if (scanner.nextLine().equalsIgnoreCase("NO"))
							break;
						success = userService.borrow(user.getId());
					}
				}
				break;
			case 3:
				// show borrowed book and return
				List<BookDto> listBorrow = userService.showBorrowBook(user.getId());
				if (listBorrow.isEmpty()) {
					System.out.println("You don't borrow any book");
					break;
				}

				success = userService.returnBook();
				while (success == false) {
					System.out.println("Return fail! Something was wrong. Try again? YES or NO");
					if (scanner.nextLine().equalsIgnoreCase("NO"))
						break;
					success = userService.returnBook();
				}

				break;
			case 4:
				// edit user's info

				inputChoose = userService.isContinue("update information");
				if (inputChoose.equalsIgnoreCase("NO"))
					break;
				int update = userService.updateUser(user);
				
				break;
			default:

				break;
			}
			if (chooseService == 5)
				break;
		}

		// Loop If role is librarian
		while (user != null && user.getRoleId() == 2) {
			librarianService.showMenu();
			int chooseService = 0;

			try {
				chooseService = Integer.parseInt(scanner.nextLine());
				if (chooseService < 1 || chooseService > 9)
					throw new InputMismatchException("Please choose correct number");
			} catch (NumberFormatException e) {
				System.out.println("Please input a number");
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
			}

			switch (chooseService) {
			case 1:
				// show Available book and borrow
				librarianService.showAllAvailable();
				inputChoose = userService.isContinue("borrow book");

				if (inputChoose.equalsIgnoreCase("YES")) {
					success = librarianService.borrow(user.getId());

					while (success == false) {
						System.out.println("Borrow fail! Something was wrong. Try again? YES or NO");

						if (scanner.nextLine().equalsIgnoreCase("NO"))
							break;
						success = librarianService.borrow(user.getId());
					}
				}

				break;
			case 2:
				// find book by book's info
				List<BookDto> list = librarianService.search();
				if (list == null)
					break;
				inputChoose = userService.isContinue("borrow book");
				if (inputChoose.equalsIgnoreCase("YES")) {
					success = librarianService.borrow(user.getId());

					while (success == false) {
						System.out.println("Borrow fail! Something was wrong. Try again? YES or NO");

						if (scanner.nextLine().equalsIgnoreCase("NO"))
							break;
						success = librarianService.borrow(user.getId());
					}

				}

				break;
			case 3:
				// show borrowed book and return
				List<BookDto> listBorrow = librarianService.showBorrowBook(user.getId());
				if (listBorrow.isEmpty()) {
					System.out.println("You don't borrow any book");
					break;
				}

				inputChoose = userService.isContinue("return book");
				if (inputChoose.equalsIgnoreCase("YES")) {
					success = librarianService.returnBook();
					while (success == false) {

						System.out.println("Return fail! Something was wrong. Try again? YES or NO");
						if (scanner.nextLine().equalsIgnoreCase("NO"))
							break;
						success = librarianService.returnBook();
					}
				}

				break;
			case 4:

				// edit user's info
				System.out.printf("%-20s%-15s%-15s%s\n", "Name", "Password", "IdNumber", "PhoneNo");
				System.out.printf("%-20s%-15s%-15s%s\n", user.getName(), user.getPassword(), user.getIdNum(),
						user.getPhone());
				System.out.println();
				inputChoose = librarianService.isContinue("update information");
				if (inputChoose.equalsIgnoreCase("NO"))
					break;

				while (librarianService.updateUser(user) == 0) {
					System.out.println("Update fail! Something was wrong. Try again? YES or NO");
					if (scanner.nextLine().equalsIgnoreCase("NO"))
						break;
				}
				System.out.println("Update success");
				System.out.printf("%-20s%-15s%-15s%s\n", "Name", "Password", "IdNumber", "PhoneNo");
				System.out.printf("%-20s%-15s%-15s%s\n", user.getName(), user.getPassword(), user.getIdNum(),
						user.getPhone());
				break;
			case 5:
				// show list book and crud book
				librarianService.crudBook();
				break;
			case 6:
				// show list user and crud user
				librarianService.crudUser();
				break;
			case 7:

				librarianService.showBorrowHistory();
				break;
			case 8:

				librarianService.showBorrowByUser();
				break;
			default:
				break;
			}

			if (chooseService == 9)
				break;
		}

		System.out.println("SEE YOU AGAIN!!!!!!!!!!");

		scanner.close();
	}
}
