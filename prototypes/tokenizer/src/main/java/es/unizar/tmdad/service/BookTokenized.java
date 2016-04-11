package es.unizar.tmdad.service;

import java.util.ArrayList;
import java.util.List;

public class BookTokenized {
	private int id;
	private String title;
	private List<Chapter> chapters;

	public BookTokenized() {
		this.chapters = new ArrayList<Chapter>();
	}
	
	public BookTokenized(int id) {
		this.id = id;
		this.chapters = new ArrayList<Chapter>();
	}
	
	public void addChapter(Chapter chapter){
		chapters.add(chapter);
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
	public List<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
}
