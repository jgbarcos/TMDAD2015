package es.unizar.tmdad.webgui.services.themesdb;

import java.util.List;

public interface ThemesDB {
	public boolean createTheme(String themeName, String... tokenNames);
	public boolean createTheme(String themeName, List<String> tokenNames);
	public Theme getTheme(long id);
	public Theme getTheme(String title);
	public List<Theme> likeTheme(String like);
}
