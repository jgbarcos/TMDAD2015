package es.unizar.tmdad.dbmodel;

import java.util.List;

public class Book {
	private long id;
	private String title;
	private List<String> chapters;
	
	public Book(long id, String title, List<String> chapters) {
		this.id = id;
		this.title = title;
		this.setChapters(chapters);
	}
	public long getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getChapters() {
		return chapters;
	}
	public void setChapters(List<String> chapters) {
		this.chapters = chapters;
	}
	
}
