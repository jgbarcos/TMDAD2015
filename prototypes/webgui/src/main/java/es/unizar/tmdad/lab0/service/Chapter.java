package es.unizar.tmdad.lab0.service;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private int id;
	private String title;
	private List<Theme> themes;
	
	public Chapter(int id, String title, List<Theme> themes) {
		this.id = id;
		this.title = title;
		this.themes = themes;
		themes.add(new Theme(1, "Animals", new ArrayList<Token>()));
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}
}
