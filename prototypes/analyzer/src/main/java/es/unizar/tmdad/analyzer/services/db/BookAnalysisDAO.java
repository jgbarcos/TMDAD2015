package es.unizar.tmdad.analyzer.services.db;

import java.util.ArrayList;
import java.util.List;

public class BookAnalysisDAO {
	private long id = -1;
	private String title;
	private List<ChapterAnalysisDAO> chapters;
	
	public BookAnalysisDAO(){}
	
	public BookAnalysisDAO(long id, String title){
		this.id = id;
		this.title = title;
		this.chapters = new ArrayList<ChapterAnalysisDAO>();
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

	public List<ChapterAnalysisDAO> getChapters() {
		return chapters;
	}

	public void setChapters(List<ChapterAnalysisDAO> chapters) {
		this.chapters = chapters;
	}
}
