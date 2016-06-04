package es.unizar.tmdad.analyzer.service;

import java.util.List;
/**
 * 
 * @author erremesse1
 *	Class to map from Json received from Analyzer when using RabbitMQ
 */
public class BookRawAndTerms {
	private int id;
	private String content;
	private List<String> terms;
	
	public BookRawAndTerms(){
		// Default ctor, required for jackson
	}
	
	public BookRawAndTerms(int id, String content, List<String> terms) {
		this.id=id;
		this.content=content;
		this.setTerms(terms);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getTerms() {
		return terms;
	}

	public void setTerms(List<String> terms) {
		this.terms = terms;
	}
}
