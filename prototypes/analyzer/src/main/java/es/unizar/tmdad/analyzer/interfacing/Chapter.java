package es.unizar.tmdad.analyzer.interfacing;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private int id;
	private String title;
	private List<Token> tokens;
	
	public Chapter(){
		// Default ctor, required for jackson
	}
	
	public Chapter(int id, String title) {
		this.id = id;
		this.title = title;
		this.tokens = new ArrayList<>();		
	}
	
	public Chapter(int id, String title, List<Token> tokens) {
		this.id = id;
		this.title = title;
		this.tokens = tokens;		
	}
	
	public void createToken(String word, int count){
		tokens.add(new Token(word, count));
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

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
	
}
