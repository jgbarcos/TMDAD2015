package es.unizar.tmdad.analyzer.services.themesdb;

import java.util.ArrayList;
import java.util.List;

public class Theme {
	private long id;
	private String title;
	private List<String> tokens;
	
	public Theme(ThemeElement themeDB, List<TokenElement> tokensDB){
		this.id = themeDB.getId();
		this.title = themeDB.getTitle();
		this.tokens = new ArrayList<>();
		for(TokenElement tdb : tokensDB){
			tokens.add(tdb.getWord());
		}
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public List<String> getTokens() {
		return tokens;
	}

	@Override
	public String toString() {
		return "Theme [id=" + id + ", title=" + title + ", tokens=" + tokens + "]";
	}
}
