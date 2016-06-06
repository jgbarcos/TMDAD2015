package es.unizar.tmdad.dbmodel;

import java.util.Map;

public class Chapter {
	private Integer num;
	private String title;
	private Map<String,Integer> terms;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Map<String,Integer> getTerms() {
		return terms;
	}
	public void setTerms(Map<String,Integer> terms) {
		this.terms = terms;
	}
	
}
