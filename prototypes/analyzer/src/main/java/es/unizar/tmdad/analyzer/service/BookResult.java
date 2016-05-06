package es.unizar.tmdad.analyzer.service;

import java.util.ArrayList;
import java.util.List;

public class BookResult {
	private long id;
	private String title;
	private List<ChapterResult> chapters;
	private List<ThemeResult> themes;
	
	public BookResult(long id, String title){
		this.id = id;
		this.title = title;
		this.chapters = new ArrayList<ChapterResult>();
		this.themes = new ArrayList<ThemeResult>();
	}
	
	public void addToken(String themeName, int chapterId, String chapterName, String tokenName, long count){
		ChapterResult chapter = chapters.stream().filter(c -> c.getId() == chapterId)
									  		.findFirst().orElse(null);
		if(chapter == null){
			chapter = new ChapterResult(chapterId, chapterName);
			chapters.add(chapter);
		}
		
		chapter.addToken(themeName,  tokenName, count);
		
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
	
	public List<ChapterResult> getChapters(){
		return chapters;
	}

	
}
