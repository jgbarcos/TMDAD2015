package es.unizar.tmdad.analyzer.services.db.model;

import java.util.Iterator;
import java.util.Map;

public class ChapterAnalysis {
	private long num;
	private String title;
	private Map<String, Long> counts;
	
	public ChapterAnalysis(long num, String title, Map<String, Long> counts) {
		this.num = num;
		this.title = title;
		this.counts = counts;
	}
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
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
	
	@Override
	public String toString() {
		String st = "CHAPTER num=" + this.num + ", title=" + this.title + ", counts=[";
		if (this.counts!=null && !this.counts.isEmpty()) {
			for (Iterator<String> iterator = counts.keySet().iterator(); iterator.hasNext();) {
				String term = (String) iterator.next();
				st += term + "=" + this.counts.get(term) + ", ";
			}	
		}
		st += "]";
		return st;
	}
	
}
