package application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBObj {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/fileapp?useSSL=false";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "";
	private static final String CSTMT_LoginUser = "{call LoginUser (?, ?)}";	
		
	// Login validate user
	public boolean validateUser(String username, String password) throws SQLException {
		// CallableStatement cstmt = null;
		try {
			//Establish connection
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			//Call Stored procedure
			CallableStatement cstmt = conn.prepareCall(CSTMT_LoginUser);
			cstmt.setString(1, username);
			cstmt.setString(2, password);
			cstmt.execute();
			ResultSet result = cstmt.getResultSet();
			if(result.next()) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//Establish Cconnection
	public Connection getConnection() throws SQLException {
		try {
			//Establish connection
			Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			return conn;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
