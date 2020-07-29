package kits.personal.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kits.personal.connection.JDBCConnection;
import kits.personal.dto.FeeDto;
import kits.personal.entity.Fee;
import kits.personal.repository.FeeRepository;

public class FeeRepositoryImpl implements FeeRepository {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	private Connection getConnection() throws SQLException {
		return conn = JDBCConnection.getInstance().getConnection();
	}

	@Override
	public int add(Fee fee) {
		String query = "INSERT INTO fees(name,user_id,mount) values(?,?,?)";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, fee.getName());
			ps.setInt(2, fee.getUserId());
			ps.setFloat(3, fee.getMount());
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
	public List<FeeDto> findAll() {
		List<FeeDto> list = new ArrayList<FeeDto>();
		String query = "SELECT f.id,f.name,u.name as username,f.mount, f.transaction_date as date FROM fees as f, users as u where f.user_id = u.id";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				FeeDto fee = new FeeDto();
				fee.setId(rs.getInt("id"));
				fee.setName(rs.getString("name"));
				fee.setUserName(rs.getString("username"));
				fee.setTranDate(rs.getString("date"));
				fee.setMount(rs.getFloat("mount"));
				list.add(fee);
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
	public int update(Fee fee) {
		String query = "UPDATE fees SET name = ?, user_id = ? where id = ?";
		int result = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, fee.getName());
			ps.setInt(2, fee.getUserId());
			ps.setInt(3, fee.getId());
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
		String query = "DELETE FROM fees where id =? ";
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
