package es.unizar.tmdad.analyzer.service;

public class TokenResult {
	private String word;
	private long count;
	
	public TokenResult(String word){
		this.word = word;
		this.count = 0;
	}
	
	public TokenResult(String word, long count){
		this.word = word;
		this.count = count;
	}
	
	public void addToken(long count){
		this.count += count;
	}

	public String getWord() {
		return word;
	}

	public long getCount() {
		return count;
	}
	
}
