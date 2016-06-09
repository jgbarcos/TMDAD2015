package es.unizar.tmdad.dbconnecton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.unizar.tmdad.dbmodel.Theme;


public class ThemeDAO {
	
	public long createThemeOfUser(String username, Theme theme) {
		Connection conn = null;
		long themeId = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String insertThemeSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".THEME (name, themeOwner) VALUES (?,?)";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(insertThemeSQL);
			pstmt.setString(1, theme.getName());
			pstmt.setString(2, username);
			pstmt.executeUpdate();
			pstmt.close();
			
			String getThemeIdSQL = "SELECT id FROM " + DatabaseConstants.dbSchema + ".THEME WHERE name=? AND themeOwner=?";
			PreparedStatement pstmt2;
			pstmt2 = conn.prepareStatement(getThemeIdSQL);
			pstmt2.setString(1, theme.getName());
			pstmt2.setString(2, username);
			
			ResultSet rs = pstmt2.executeQuery();
			if (rs.next()) {
				themeId = rs.getLong("id");
			}
			pstmt2.close();
			
			String insertTermFromThemeSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".TERM_IN_THEME (term, theme) VALUES (?,?)";
			PreparedStatement pstmt3;
			pstmt3 = conn.prepareStatement(insertTermFromThemeSQL);
			
			List<String> terms = theme.getTerms();
			String term;
			for (Iterator<String> iterator = terms.iterator(); iterator.hasNext();) {
				try {
					term = (String) iterator.next();
					pstmt3.setString(1, term);
					pstmt3.setLong(2, themeId);
					pstmt3.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			pstmt3.close();
			
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
		return themeId;
	}
	
	public void deleteTheme(Theme theme) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String deleteThemeSQL = "DELETE FROM " + DatabaseConstants.dbSchema + ".THEME WHERE id=?";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(deleteThemeSQL);
			pstmt.setLong(1, theme.getId());
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
	
	public boolean addTermToTheme(String term, Theme theme) {
		Connection conn = null;
		boolean inserted = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String insertTermToThemeSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".TERM_IN_THEME(term, theme) VALUES (?,?)";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(insertTermToThemeSQL);
			pstmt.setString(1, term);
			pstmt.setLong(2, theme.getId());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			inserted = true;
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
		return inserted;
	}

	public boolean deleteTermFromTheme(String term, Theme theme) {	
		Connection conn = null;
		boolean deleted = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String insertTermToThemeVersionSQL = "DELETE FROM " + DatabaseConstants.dbSchema + ".TERM_IN_THEME WHERE term=? AND theme=?";
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(insertTermToThemeVersionSQL);
			pstmt.setString(1, term);
			pstmt.setLong(2, theme.getId());
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			deleted = true;
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
		return deleted;
	}

	public Map<Long, Theme> findAllThemeByUsername(String username) {
		Connection conn = null;
		PreparedStatement pstmt;
		Map<Long, Theme> themes = new HashMap<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String themesFromUserSQL = "SELECT * "
					+ "FROM " + DatabaseConstants.dbSchema + ".THEME "
					+ "INNER JOIN " + DatabaseConstants.dbSchema + ".TERM_IN_THEME "
					+ "ON " + DatabaseConstants.dbSchema + ".THEME.id=" + DatabaseConstants.dbSchema + ".TERM_IN_THEME.theme "
					+ "WHERE themeOwner=?";
			
			pstmt = conn.prepareStatement(themesFromUserSQL);
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();

			long themeId;
			String themeName;
			String term;
			Theme theme;
			while (rs.next()) {
				themeId = rs.getLong("id");
				themeName = rs.getString("name");
				term = rs.getString("term");
				if (!themes.containsKey(themeId)) {
					theme = new Theme(themeId, themeName, new ArrayList<>());
					themes.put(themeId, theme);
				} else {
					theme = themes.get(themeId);
				}
				theme.getTerms().add(term);
			}
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return themes;
	}

	public Theme findThemeByUsernameAndThemeId(String username, long themeId) {
		Connection conn = null;
		PreparedStatement pstmt;
		Theme theme = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String themesFromUserSQL = "SELECT * "
					+ "FROM " + DatabaseConstants.dbSchema + ".THEME "
					+ "INNER JOIN " + DatabaseConstants.dbSchema + ".TERM_IN_THEME "
					+ "ON " + DatabaseConstants.dbSchema + ".THEME.id=" + DatabaseConstants.dbSchema + ".TERM_IN_THEME.theme "
					+ "WHERE " + DatabaseConstants.dbSchema + ".THEME.themeOwner=? AND " + DatabaseConstants.dbSchema + ".THEME.id=?";
			 
			pstmt = conn.prepareStatement(themesFromUserSQL);
			pstmt.setString(1, username);
			pstmt.setLong(2, themeId);
			
			ResultSet rs = pstmt.executeQuery();

			String themeName;
			String term;
			while (rs.next()) {
				themeName = rs.getString("name");
				term = rs.getString("term");
				if (theme==null) {
					theme = new Theme(themeId, themeName, new ArrayList<>());
				} 
				theme.getTerms().add(term);
			}
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return theme;

	}

	public List<Theme> findThemeByUsernameLikeThemeName(String username, String likeThemeName) {
		Connection conn = null;
		PreparedStatement pstmt;
		Map<Long, Theme> themes = new HashMap<>();
		List<Theme> themeList = new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String themesFromUserSQL = "SELECT * "
					+ "FROM " + DatabaseConstants.dbSchema + ".THEME "
					+ "INNER JOIN " + DatabaseConstants.dbSchema + ".TERM_IN_THEME "
					+ "ON " + DatabaseConstants.dbSchema + ".THEME.id=" + DatabaseConstants.dbSchema + ".TERM_IN_THEME.theme "
					+ "WHERE themeOwner=? AND " + DatabaseConstants.dbSchema + ".THEME.name like ?";
			
			pstmt = conn.prepareStatement(themesFromUserSQL);
			pstmt.setString(1, username);
			pstmt.setString(2, likeThemeName + "%");
			
			ResultSet rs = pstmt.executeQuery();

			long themeId;
			String themeName;
			String term;
			Theme theme;
			while (rs.next()) {
				themeId = rs.getLong("id");
				themeName = rs.getString("name");
				term = rs.getString("term");
				if (!themes.containsKey(themeId)) {
					theme = new Theme(themeId, themeName, new ArrayList<>());
					themes.put(themeId, theme);
				} else {
					theme = themes.get(themeId);
				}
				theme.getTerms().add(term);
			}
			
			for (Iterator<Long> iterator = themes.keySet().iterator(); iterator.hasNext();) {
				Long thId = (Long) iterator.next();
				themeList.add(themes.get(thId));
			}

			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return themeList;
	}

	public Theme findThemeByUsernameAndThemeName(String username, String themeName) {
		Connection conn = null;
		PreparedStatement pstmt;
		Theme theme = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String themesFromUserSQL = "SELECT * "
					+ "FROM " + DatabaseConstants.dbSchema + ".THEME "
					+ "INNER JOIN " + DatabaseConstants.dbSchema + ".TERM_IN_THEME "
					+ "ON " + DatabaseConstants.dbSchema + ".THEME.id=" + DatabaseConstants.dbSchema + ".TERM_IN_THEME.theme "
					+ "WHERE " + DatabaseConstants.dbSchema + ".THEME.themeOwner=? AND " + DatabaseConstants.dbSchema + ".THEME.name=?";
			 
			pstmt = conn.prepareStatement(themesFromUserSQL);
			pstmt.setString(1, username);
			pstmt.setString(2, themeName);
			
			ResultSet rs = pstmt.executeQuery();

			long themeId;
			String term;
			while (rs.next()) {
				themeId = rs.getLong("id");
				term = rs.getString("term");
				if (theme==null) {
					theme = new Theme(themeId, themeName, new ArrayList<>());
				} 
				theme.getTerms().add(term);
			}
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
		return theme;
	}

	public long updateThemeOfUser(String username, Theme theme) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String deleteTermsOfThemeSQL = "DELETE FROM " + DatabaseConstants.dbSchema + ".TERM_IN_THEME WHERE theme=?";

			PreparedStatement pstmt;
			pstmt = conn.prepareStatement(deleteTermsOfThemeSQL);
			pstmt.setLong(1, theme.getId());
			pstmt.executeUpdate();
			pstmt.close();
			
			String insertTermOfThemeSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".TERM_IN_THEME(term, theme) VALUES (?,?)";

			PreparedStatement pstmt2;
			pstmt2 = conn.prepareStatement(insertTermOfThemeSQL);
			
			for (Iterator<String> iterator = theme.getTerms().iterator(); iterator.hasNext();) {
				String term = (String) iterator.next();
				pstmt2.setString(1, term);
				pstmt2.setLong(2, theme.getId());
				pstmt2.executeUpdate();
			}
			pstmt2.close();
			
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
		return theme.getId();
	}
	
}
