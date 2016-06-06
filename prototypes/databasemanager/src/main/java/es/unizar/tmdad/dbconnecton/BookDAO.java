package es.unizar.tmdad.dbconnecton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.unizar.tmdad.dbmodel.User;

public class BookDAO {
	
	public void insertBook(User user) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String insertUserSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".USER (username, password) VALUES (?,?)";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(insertUserSQL);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();		
		} catch (SQLException e) {
			e.printStackTrace();			
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateUser(User user) {
		Connection conn = null;
		PreparedStatement pstmt;
		boolean found = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String validateUserSQL = "SELECT * FROM " + DatabaseConstants.dbSchema + ".USER WHERE username=? AND password=?";
			
			pstmt = conn.prepareStatement(validateUserSQL);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				found = true;
			}
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return found;
	}

}
