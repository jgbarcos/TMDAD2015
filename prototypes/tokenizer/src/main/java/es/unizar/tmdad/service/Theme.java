package es.unizar.tmdad.service;

import java.util.List;

public class Theme {
	private int id;
	private String name;
	private List<Token> tokens;
	
	public Theme(int id, String name, List<Token> tokens) {
		super();
		this.id = id;
		this.name = name;
		this.tokens = tokens;
		tokens.add(new Token("rabbit", 7));
		tokens.add(new Token("cat", 2));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}
	
}
