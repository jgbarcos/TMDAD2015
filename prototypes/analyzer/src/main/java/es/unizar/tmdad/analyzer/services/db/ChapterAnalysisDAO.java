package es.unizar.tmdad.analyzer.services.db;

import java.util.HashMap;
import java.util.Map;

public class ChapterAnalysisDAO {
	private long num;
	private String title;
	private Map<String, Long> counts;
	
	public ChapterAnalysisDAO(){}
	
	public ChapterAnalysisDAO(long numChapter, String title){
		this.num = numChapter;
		this.title = title;
		this.counts = new HashMap<String, Long>();
	}

	public long getNumChapter() {
		return num;
	}

	public void setNumChapter(long numChapter) {
		this.num = numChapter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, Long> getCounts() {
		return counts;
	}

	public void setCounts(Map<String, Long> counts) {
		this.counts = counts;
	}
}
