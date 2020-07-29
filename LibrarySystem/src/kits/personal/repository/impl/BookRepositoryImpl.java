package kits.personal.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kits.personal.connection.JDBCConnection;
import kits.personal.dto.BookDto;
import kits.personal.entity.Book;
import kits.personal.repository.BookRepository;

public class BookRepositoryImpl implements BookRepository {

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private Connection getConnection() throws SQLException {
		return conn = JDBCConnection.getInstance().getConnection();
	}

	public List<Book> findAll() {
		List<Book> list = new ArrayList<Book>();
		String query = "SELECT * FROM users";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setAuthorId(rs.getInt("author_id"));
				book.setPublisherId(rs.getInt("publisherId"));
				book.setAvailable(rs.getInt("available"));
				list.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	public List<BookDto> findAllAvailable() {
		List<BookDto> list = new ArrayList<BookDto>();
		String query = "SELECT b.id, b.name bookname, a.name author, p.name publisher FROM books b  JOIN authors a ON b.author_id = a.id JOIN publishers p ON p.id =b.publisher_id WHERE b.available = 1";
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookDto book = new BookDto();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("bookname"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				list.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}
	
	public List<BookDto> findBorrowHistory() {
		List<BookDto> list = new ArrayList<BookDto>();
		String query = "SELECT b.id, b.name bookname, u.name username, a.name author, p.name publisher, l.loan_date, l.return_date "
				+ "FROM books b "
				+ "JOIN authors a ON b.author_id = a.id "
				+ "JOIN publishers p ON p.id =b.publisher_id "
				+ "JOIN loans l ON l.book_id = b.id "
				+ "JOIN users u on l.user_id = u.id";
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookDto book = new BookDto();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("bookname"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				book.setReturnDate(rs.getDate("return_date"));
				book.setLoanDate(rs.getDate("loan_date"));
				book.setUsername(rs.getString("username"));
				list.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}
	
	@Override
	public List<BookDto> findAllBook() {
		List<BookDto> list = new ArrayList<BookDto>();
		String query = "SELECT b.id, b.name bookname, a.name author, p.name publisher FROM books b  JOIN authors a ON b.author_id = a.id JOIN publishers p ON p.id =b.publisher_id";
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookDto book = new BookDto();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("bookname"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				list.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	public Book findById(int id) {
		String query = "SELECT * FROM books where id = ?";
		Book book = new Book();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("name"));
				book.setAuthorId(rs.getInt("author_id"));
				book.setPublisherId(rs.getInt("publisher_id"));
				book.setAvailable(rs.getInt("available"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return book;

	}

	public void add(Book book) {
		String query = "INSERT INTO books(name,author_id,publisher_id) values(?,?,?)";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, book.getName());
			ps.setInt(2, book.getAuthorId());
			ps.setInt(3, book.getPublisherId());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	public void update(Book book) {
		String query = "UPDATE books SET name = ?, author_id =?, publisher_id =? where id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, book.getName());
			ps.setInt(2, book.getAuthorId());
			ps.setInt(3, book.getPublisherId());
			ps.setInt(4, book.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void delete(int id) {
		String query = "DELETE FROM books where id =? ";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public int setAvailability(int available, int id) {
		String query = "UPDATE books SET available=? where id = ?";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, available);
			ps.setInt(2, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	@Override
	public List<BookDto> findByBookInfo(String keyword) {
		List<BookDto> list = new ArrayList<BookDto>();
		String query = "SELECT b.id, b.name bookname, a.name author, p.name publisher FROM books b  JOIN authors a ON b.author_id = a.id JOIN publishers p ON p.id =b.publisher_id WHERE b.available = 1 and (a.name like ? or b.name like ? or p.name like ?)";
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "%" + keyword + "%");
			ps.setString(2, "%" + keyword + "%");
			ps.setString(3, "%" + keyword + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookDto book = new BookDto();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("bookname"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				list.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {

			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return list;
	}
	
	@Override
	public List<BookDto> findBorrowed(int userId) {
		List<BookDto> list = new ArrayList<BookDto>();
		String query = "SELECT b.id, b.name bookname, a.name author, p.name publisher, l.loan_date FROM books b "
			 +	"JOIN authors a ON b.author_id = a.id "
			 +	"JOIN publishers p ON p.id = b.publisher_id "
			 +	"JOIN loans l ON b.id = l.book_id "
			 +	"JOIN users u ON l.user_id = u.id " 
			 +	"WHERE u.id = ? and l.isactive = 1" ;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookDto book = new BookDto();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("bookname"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				book.setLoanDate(rs.getDate("loan_date"));
				list.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public List<BookDto> findAllBorrowed() {
		List<BookDto> list = new ArrayList<BookDto>();
		String query = "SELECT b.id, b.name bookname, a.name author, p.name publisher, l.loan_date FROM books b "
			 +	"JOIN authors a ON b.author_id = a.id "
			 +	"JOIN publishers p ON p.id = b.publisher_id "
			 +	"JOIN loans l ON b.id = l.book_id "
			 +	"JOIN users u ON l.user_id = u.id ";
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				BookDto book = new BookDto();
				book.setId(rs.getInt("id"));
				book.setName(rs.getString("bookname"));
				book.setAuthor(rs.getString("author"));
				book.setPublisher(rs.getString("publisher"));
				book.setLoanDate(rs.getDate("loan_date"));
				list.add(book);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}


	
}
