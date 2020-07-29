package kits.personal.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kits.personal.connection.JDBCConnection;
import kits.personal.entity.Author;
import kits.personal.repository.AuthorRepository;

public class AuthorRepositoryImpl implements AuthorRepository {
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private Connection getConnection() throws SQLException {
		return conn = JDBCConnection.getInstance().getConnection();
	}

	@Override
	public int add(String name ) {
		String query = "INSERT INTO authors(name) values(?)";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, name);
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
	public List<Author> findAll() {
		List<Author> list = new ArrayList<Author>();
		String query = "SELECT * FROM authors";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Author author = new Author();
				author.setId(rs.getInt("id"));
				author.setName(rs.getString("name"));
				list.add(author);
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
	public int update(Author author) {
		String query = "UPDATE authors SET NAME = ?";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, author.getName());
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
	public int delete(int id) {
		String query = "DELETE FROM authors where id =? ";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
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
