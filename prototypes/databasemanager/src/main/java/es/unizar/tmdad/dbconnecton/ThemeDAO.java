package es.unizar.tmdad.dbconnecton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import es.unizar.tmdad.dbmodel.Theme;


public class ThemeDAO {
	
	public long insertTheme(Theme theme, String username) {
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
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				themeId = rs.getLong("id");
			}
			
			String insertTermFromThemeSQL = "INSERT INTO " + DatabaseConstants.dbSchema + ".TERM_IN_THEME (term, theme) VALUES (?,?)";
			PreparedStatement pstmt3;
			pstmt3 = conn.prepareStatement(insertTermFromThemeSQL);
			
			Set<String> terms = theme.getTerms();
			String term;
			for (Iterator<String> iterator = terms.iterator(); iterator.hasNext();) {
				try {
					term = (String) iterator.next();
					pstmt3.setString(1, term);
					pstmt3.setLong(2, theme.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
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
			String themesFromUserSQL = "SELECT * FROM " + DatabaseConstants.dbSchema + ".THEME INNER JOIN " + DatabaseConstants.dbSchema + ".TERM_IN_THEME ON "
					 + DatabaseConstants.dbSchema + ".THEME.id=" + DatabaseConstants.dbSchema + ".TERM_IN_THEME.theme WHERE themeOwner=?";
			
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
					theme = new Theme(themeId, themeName, new HashSet<String>());
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
			String themesFromUserSQL = "SELECT * FROM " + DatabaseConstants.dbSchema + ".THEME INNER JOIN " + DatabaseConstants.dbSchema + ".TERM_IN_THEME ON "
					 + DatabaseConstants.dbSchema + ".THEME.id=" + DatabaseConstants.dbSchema + ".TERM_IN_THEME.theme WHERE " + DatabaseConstants.dbSchema 
					 + ".THEME.themeOwner=? AND " + DatabaseConstants.dbSchema + ".TERM_IN_THEME.theme WHERE " + DatabaseConstants.dbSchema + ".THEME.id=?";
			 
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
					theme = new Theme(themeId, themeName, new HashSet<String>());
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
	
	/*private void deleteUserThemes(User user) {
		Connection conn = null;
		PreparedStatement pstmt;
		List<Theme> themes = new ArrayList<>();
		Map<String, List<String>> termsFromTheme = new HashMap<String, List<String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DatabaseConstants.dbUrl, DatabaseConstants.dbUser, DatabaseConstants.dbPass);
			String themesFromUserSQL = "DELETE FROM " + DatabaseConstants.dbSchema + ".TERM_IN_THEME WHERE themeOwner=?";
			
			pstmt = conn.prepareStatement(themesFromUserSQL);
			pstmt.setString(1, user.getUsername());
			
			pstmt.executeUpdate();

			String insertThemeSQL
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	
		Theme theme;
		for (String themeName : termsFromTheme.keySet()) {
			theme = new Theme(themeName, termsFromTheme.get(themeName));
			themes.add(theme);
		}
		
		return themes;
	}*/

	/*public void updateUserThemes(User u) {
		Connection conn = null;
		PreparedStatement pstmt;
		List<Theme> themes = new ArrayList<>();
		Map<String, List<String>> termsFromTheme = new HashMap<String, List<String>>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, "root", "root");
			String themesFromUserSQL = "SELECT * FROM " + DatabaseConstants.dbSchema + ".TERM_IN_THEME WHERE themeOwner=?";
			
			pstmt = conn.prepareStatement(themesFromUserSQL);
			pstmt.setString(1, user.getUsername());
			
			ResultSet rs = pstmt.executeQuery();

			String themeName;
			while (rs.next()) {
				themeName = rs.getString("name");
				if (!termsFromTheme.containsKey(themeName)) {
					termsFromTheme.put(themeName, new ArrayList<>());
				}
				termsFromTheme.get(themeName).add(rs.getString("term"));
			}
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	
		Theme theme;
		for (String themeName : termsFromTheme.keySet()) {
			theme = new Theme(themeName, termsFromTheme.get(themeName));
			themes.add(theme);
		}
		
		return themes;
	}*/
	
}
