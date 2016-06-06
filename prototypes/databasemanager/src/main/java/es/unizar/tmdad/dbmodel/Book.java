package es.unizar.tmdad.dbmodel;

import java.util.List;

public class Book {
	private Integer id;
	private String title;
	private List<Chapter> chapters;
	
	public Book(Integer id, String title, List<Chapter> chapters) {
		this.id = id;
		this.title = title;
		this.setChapters(chapters);
	}
	public Integer getId() {
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
	public List<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
	
}
