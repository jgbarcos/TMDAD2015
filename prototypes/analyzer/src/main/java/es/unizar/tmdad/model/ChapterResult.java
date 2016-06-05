package es.unizar.tmdad.model;

import java.util.ArrayList;
import java.util.List;

public class ChapterResult {
	private long id;
	private String title;
	private List<ThemeResult> themes;
	
	public ChapterResult(long id, String title){
		this.id = id;
		this.title = title;
		this.themes = new ArrayList<ThemeResult>();
	}
	
	public void addToken(String themeName, String tokenName, long count){
		ThemeResult theme = themes.stream()
								  .filter(t -> t.getName().equals(themeName))
						  		  .findFirst().orElse(null);
		if(theme == null){
			theme = new ThemeResult(themeName);
			themes.add(theme);
		}
		theme.addToken(tokenName, count);
		ThemeResult.calculatePercentage(themes);
	}
	
	public long getId(){
		return id;
	}

	public String getTitle() {
		return title;
	}

	public List<ThemeResult> getThemes() {
		return themes;
	}
	
}
