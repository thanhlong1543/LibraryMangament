package kits.personal.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kits.personal.connection.JDBCConnection;
import kits.personal.entity.User;
import kits.personal.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private Connection getConnection() throws SQLException {
		return conn = JDBCConnection.getInstance().getConnection();
	}

	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		String query = "SELECT * FROM users";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setIdNum(rs.getString("idnum"));
				user.setRoleId(rs.getInt("role_id"));
				user.setRegDate(rs.getDate("reg_date"));
				list.add(user);
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

	public int add(User user) throws SQLException {
		String query = "INSERT INTO users(name, password, phone, idnum) values(?,?,?,?)";
		int result = 0;
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getIdNum());
			result = ps.executeUpdate();
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
		
		return result;
	}

	public int update(User user) throws SQLException {
		String query = "UPDATE users SET name = ?, password =?, phone =?, idnum=? where id = ?";
		int result = 0;
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getIdNum());
			ps.setInt(5, user.getId());
			result = ps.executeUpdate();
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
		
		return result;
	}
	
	public int extendReg(User user) {
		String query = "UPDATE users SET reg_date = ? where id = ?";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setDate(1, user.getRegDate());
			ps.setInt(2, user.getId());
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

	public void delete(int id) {
		String query = "DELETE FROM users where id = ?";
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

	public User checkUsername(String username) {
		String query = "SELECT * FROM users where name = ?";
		User user = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setIdNum(rs.getString("idnum"));
				user.setRoleId(rs.getInt("role_id"));
				user.setRegDate(rs.getDate("reg_date"));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	finally {

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
		return user;
	}

	@Override
	public User findById(int id) {
		String query = "SELECT * FROM users where id = ?";
		User user = new User();
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setPhone(rs.getString("phone"));
				user.setIdNum(rs.getString("idnum"));
				user.setRoleId(rs.getInt("role_id"));
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
		return user;

	}

	
	
	
}
