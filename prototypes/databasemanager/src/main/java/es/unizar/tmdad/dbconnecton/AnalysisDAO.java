package es.unizar.tmdad.dbconnecton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import es.unizar.tmdad.dbmodel.BookAnalysis;
import es.unizar.tmdad.dbmodel.ChapterAnalysis;

public class AnalysisDAO {

	public void createAnalysis(long bookId, long chapterNum, String term, long count) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String insertResultSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".RESULT(book, chapter, term, num) VALUES (?,?,?,?)";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(insertResultSQL);
			pstmt.setLong(1, bookId);
			pstmt.setLong(2, chapterNum);
			pstmt.setString(2, term);
			pstmt.setLong(4, count);
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

	public long getAnalysisOfTerm(long bookId, long chapterNum, String term) {
		Connection conn = null;
		long count = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String selectBookSQL = "SELECT num "
					+ "FROM " + DatabaseConstants.dbSchema + ".RESULT "
					+ "WHERE book=? AND chapter=? AND term=?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(selectBookSQL);
			pstmt.setLong(1, bookId);
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getLong("num");
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
		return count;
	}

	public BookAnalysis findAnalysisByBookAndTerms(long bookId, Set<String> terms) {
		Connection conn = null;
		BookAnalysis bookAnalysis = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String selectAnalysisSQL = "SELECT " 
					+ DatabaseConstants.dbSchema + ".BOOK.id, " 
					+ DatabaseConstants.dbSchema + ".BOOK.title, "
					+ DatabaseConstants.dbSchema + ".CHAPTER.num, "
					+ DatabaseConstants.dbSchema + ".CHAPTER.title, "
					+ DatabaseConstants.dbSchema + ".RESULT.term, "
					+ DatabaseConstants.dbSchema + ".RESULT.num"
					+ "FROM " + DatabaseConstants.dbSchema + ".RESULT "
					+ "INNER JOIN " + DatabaseConstants.dbSchema + ".BOOK "
					+ "ON " + DatabaseConstants.dbSchema + ".RESULT.book=" + DatabaseConstants.dbSchema + ".BOOK.id "
					+ "INNER JOIN " + DatabaseConstants.dbSchema + ".CHAPTER "
					+ "ON " + DatabaseConstants.dbSchema + ".RESULT.chapter=" + DatabaseConstants.dbSchema + ".CHAPTER.num "
					+ "AND " + DatabaseConstants.dbSchema + ".RESULT.book=" + DatabaseConstants.dbSchema + ".CHAPTER.book "
					+ "WHERE " + DatabaseConstants.dbSchema + ".BOOK.id=? AND (" + DatabaseConstants.dbSchema + ".RESULT.term=? ";

			for (int i = 1; i < terms.size(); i++) {
				selectAnalysisSQL += "OR " + DatabaseConstants.dbSchema + ".RESULT.term=? "; 
			}
			selectAnalysisSQL += ") ORDER BY " + DatabaseConstants.dbSchema + ".CHAPTER.num ";
			
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(selectAnalysisSQL);	
			pstmt.setLong(1, bookId);
			
			String[] termsArray = (String[]) terms.toArray();
			for (int i = 2; i < termsArray.length; i++) {
				String term = termsArray[i];
				pstmt.setString(i, term);
			}
			
			ResultSet rs = pstmt.executeQuery();

			int chapterNum;
			String chapterTitle;
			ChapterAnalysis chapter;
			long count;
			String term;
			while (rs.next()) {
				if (bookAnalysis==null) {
					String bookTitle = rs.getString(DatabaseConstants.dbSchema + ".BOOK.title");
					bookAnalysis = new BookAnalysis(bookId, bookTitle, new ArrayList<ChapterAnalysis>());
				}
				chapterNum = rs.getInt(DatabaseConstants.dbSchema + ".CHAPTER.num");
				chapterTitle = rs.getString(DatabaseConstants.dbSchema + ".CHAPTER.title");
				term = rs.getString(DatabaseConstants.dbSchema + ".RESULT.term");
				count = rs.getLong(DatabaseConstants.dbSchema + ".RESULT.num");
				if (bookAnalysis.getChapters().size()<chapterNum) {
					chapter = new ChapterAnalysis(chapterNum, chapterTitle, new HashMap<String, Long>());
					bookAnalysis.getChapters().add(chapter);
				} else {
					chapter = bookAnalysis.getChapters().get(chapterNum-1);	
				} 
				chapter.getCounts().put(term, count);
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
		return bookAnalysis;
	}
	
}
