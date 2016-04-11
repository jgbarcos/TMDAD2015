package es.unizar.tmdad.service;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private int id;
	private String title;
	private List<Token> tokens;
	
	public Chapter(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public void addToken(Token token){
		tokens.add(token);
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
