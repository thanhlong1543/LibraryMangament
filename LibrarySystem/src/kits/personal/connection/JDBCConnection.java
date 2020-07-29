package kits.personal.connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class JDBCConnection {
	private String driver = "org.mariadb.jdbc.Driver";
	private String username = "root";
	private String password = "823082";
	private String url = "jdbc:mariadb://localhost:3306/personal";

	private static JDBCConnection instance = null;
	
	private JDBCConnection() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JDBCConnection getInstance() {
		if(instance==null) {
			instance = new JDBCConnection();
		}
		return instance;	
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(url, username, password);
		return conn; 
	}
}
