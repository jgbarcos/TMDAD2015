package es.unizar.tmdad.analyzer.services.db.connecton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import es.unizar.tmdad.analyzer.services.db.model.Book;

public class BookDAO {
	
	public void createBook(Book book) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			
			String insertBookSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".BOOK (id, title) VALUES (?,?)";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(insertBookSQL);
			pstmt.setLong(1, book.getId());
			pstmt.setString(2, book.getTitle());
			pstmt.executeUpdate();
			pstmt.close();
			
			String insertChapterSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".CHAPTER (book, num, chapterTitle) VALUES (?,?,?)";
			PreparedStatement pstmt2;
			pstmt2 = conn.prepareStatement(insertChapterSQL);
			
			for (int i = 0; i < book.getChapters().size(); i++) {
				pstmt2.setLong(1, book.getId());
				pstmt2.setInt(2, i);
				pstmt2.setString(3, book.getChapters().get(i));
				pstmt2.executeUpdate();
			}	
			pstmt2.close();
			
			conn.close();		
		} catch (Exception e) {
			//e.printStackTrace();			
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					//e1.printStackTrace();
				}
			}
		}
	}

	public Book findBookById(long bookId) {
		Connection conn = null;
		Book book = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String selectBookSQL = "SELECT * "
					+ "FROM " + DatabaseConstants.dbSchema + ".BOOK "
					+ "INNER JOIN " + DatabaseConstants.dbSchema + ".CHAPTER "
					+ "ON " + DatabaseConstants.dbSchema + ".BOOK.id=" + DatabaseConstants.dbSchema + ".CHAPTER.book "
					+ "WHERE " + DatabaseConstants.dbSchema + ".BOOK.id=? "
					+ "ORDER BY " + DatabaseConstants.dbSchema + ".CHAPTER.num ASC";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(selectBookSQL);
			pstmt.setLong(1, bookId);
			
			ResultSet rs = pstmt.executeQuery();

			String chapterTitle;
			while (rs.next()) {
				if (book==null) {
					String title = rs.getString("title");
					book = new Book(bookId, title, new ArrayList<String>());
				}
				chapterTitle = rs.getString("chapterTitle");
				book.getChapters().add(chapterTitle);
			}				
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
		return book;
	}

}
