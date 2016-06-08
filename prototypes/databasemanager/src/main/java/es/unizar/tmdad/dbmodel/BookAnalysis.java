package es.unizar.tmdad.dbmodel;

import java.util.Iterator;
import java.util.List;

public class BookAnalysis {
	private long id;
	private String title;
	private List<ChapterAnalysis> chapters;
	
	public BookAnalysis(long id, String title, List<ChapterAnalysis> chapters) {
		this.id = id;
		this.title = title;
		this.chapters = chapters;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ChapterAnalysis> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterAnalysis> chapters) {
		this.chapters = chapters;
	}
	
	@Override
	public String toString() {
		String st = "BOOK id=" + this.id + ", title=" + this.title + ", chapters=(";
		if (this.chapters!=null && !this.chapters.isEmpty()) {
			for (Iterator<ChapterAnalysis> iterator = chapters.iterator(); iterator.hasNext();) {
				ChapterAnalysis chapterAnalysis = (ChapterAnalysis) iterator.next();
				st += chapterAnalysis + ", ";
			}	
		}
		st += ")";
		return st;
	}
}
