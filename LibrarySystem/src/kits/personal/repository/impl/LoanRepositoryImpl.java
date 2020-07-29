package kits.personal.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kits.personal.connection.JDBCConnection;
import kits.personal.dto.LoanDto;
import kits.personal.entity.Loan;
import kits.personal.repository.LoanRepository;

public class LoanRepositoryImpl implements LoanRepository {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private Connection getConnection() throws SQLException {
		return conn = JDBCConnection.getInstance().getConnection();
	}

	@Override
	public List<Loan> findAll() {
		List<Loan> list = new ArrayList<Loan>();
		String query = "SELECT * FROM loans";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Loan loan = new Loan();
				loan.setId(rs.getInt("id"));
				loan.setLoanDate(rs.getDate("loan_date"));
				loan.setIsActive(rs.getInt("isactive"));
				loan.setBookId(rs.getInt("book_id"));
				loan.setUserId(rs.getInt("user_id"));
				list.add(loan);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.toString());
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
	public List<LoanDto> findByUser(int id) {
		List<LoanDto> list = new ArrayList<LoanDto>();
		String query = "SELECT l.id,l.loan_date, b.name as bookname, u.name as username, l.isactive FROM loans l join books b on l.book_id = b.id join users u on l.user_id = u.id Where l.user_id = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				LoanDto loanDto = new LoanDto();
				loanDto.setId(rs.getInt("id"));
				loanDto.setLoanDate(rs.getDate("loan_date"));
				loanDto.setIsActive(rs.getInt("isactive"));
				loanDto.setBookName(rs.getString("bookname"));
				loanDto.setUserName(rs.getString("username"));
				list.add(loanDto);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.toString());
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
	public int add(Loan loan) {
		String query = "INSERT INTO loans( user_id, book_id) values(?,?)";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, loan.getUserId());
			ps.setInt(2, loan.getBookId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
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
	public int update(Loan loan) {
		String query = "UPDATE loans SET loan_date =?, isactive=?, user_id=?, book_id=? where id = ?";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setDate(1, loan.getLoanDate());
			ps.setInt(2, loan.getIsActive());
			ps.setInt(3, loan.getUserId());
			ps.setInt(4, loan.getBookId());
			ps.setInt(5, loan.getId());
			result =ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
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
	public int deActivate(int bookId,Date returnDate) {
		String query = "UPDATE loans SET isactive = 0, return_date = ? where book_id = ?";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(2, bookId);
			ps.setDate(1, returnDate);
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

}
