package es.unizar.tmdad.analyzer.services.db.model;

import java.util.Iterator;
import java.util.List;

public class Book {
	private long id;
	private String title;
	private List<String> chapters;
	
	public Book(long id, String title, List<String> chapters) {
		this.id = id;
		this.title = title;
		this.chapters = chapters;
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
	
	@Override
	public String toString() {
		String st = "BOOK id=" + this.id + ", title=" + this.title + ", chapters=(";
		if (this.chapters!=null && !this.chapters.isEmpty()) {
			for (Iterator<String> iterator = chapters.iterator(); iterator.hasNext();) {
				String chapter = (String) iterator.next();
				st += chapter + ", ";
			}	
		}
		st += ")";
		return st;
	}
}
