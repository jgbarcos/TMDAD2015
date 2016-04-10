package mockupdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MockupThemesDB implements ThemesDB{
	// Database that contains Created Themes/Tokens
	private long themeId = 0;
	private List<ThemeElement> themeTable;
	private long tokenId = 0;
	private List<TokenElement> tokenTable;
	
	public MockupThemesDB(){
		themeTable = new ArrayList<ThemeElement>();
		tokenTable = new ArrayList<TokenElement>();
		
		PredefinedData.fillDataBase(this);
	}

	/*
	 * Database row operations (auto-increment and add row)
	 */
	private long assignThemeId(){
		return themeId++;
	}
	
	private long assignTokenId(){
		return tokenId++;
	}
	
	private void addTheme(ThemeElement theme){
		themeTable.add(theme);
	}
	
	private void addToken(TokenElement token){
		tokenTable.add(token);
	}
	
	/*
	 * Create a theme
	 */
	public boolean createTheme(String themeName, String... tokenNames){
		return createTheme(themeName, Arrays.asList(tokenNames));
	}
	public boolean createTheme(String themeName, List<String> tokenNames){
		// Check if exists (avoid duplicates with name)
		if(themeTable.stream()
				     .anyMatch(t -> t.getTitle().equals(themeName))){
			return false;
		}
		
		// Create theme in database
		ThemeElement theme = new ThemeElement(assignThemeId(), themeName);	
		addTheme(theme);
		
		// Remove duplicates
		tokenNames = tokenNames.stream().distinct().collect(Collectors.toList());
		
		// Create tokens into theme
		for(String name : tokenNames){
			addToken(new TokenElement(theme.getId(), assignTokenId(), name));
		}
		return true;
	}
	
	/*
	 * Database access data
	 */
	private List<TokenElement> getTokensDB(long themeId){
		return tokenTable.stream()
						 .filter(t -> t.getThemeId() == themeId)
						 .collect(Collectors.toList());
	}
	private Theme getTheme(Predicate<ThemeElement> pred){
		ThemeElement themeFromDB = themeTable.stream()
			     .filter(pred)
			     .findFirst().orElse(null);
		if(themeFromDB == null){
			return null;
		}
		return new Theme(themeFromDB, getTokensDB(themeFromDB.getId()));
	}
	
	public Theme getTheme(long id){
		return getTheme(t -> t.getId() == id);
	}	
	
	public Theme getTheme(String title){
		return getTheme(t -> t.getTitle().equals(title));
	}
	
	public List<Theme> likeTheme(String like){
		return themeTable.stream()
			.filter(t -> t.getTitle().toLowerCase().contains(like.toLowerCase()))
			.map(t -> new Theme(t, getTokensDB( t.getId() )))
			.collect(Collectors.toList());
	}
	

}
