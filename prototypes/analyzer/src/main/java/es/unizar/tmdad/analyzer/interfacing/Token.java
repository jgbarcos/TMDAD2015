package es.unizar.tmdad.analyzer.interfacing;

public class Token {
	private String word;
	private int count;
	
	public Token(){
		// Default ctor, required for jackson
	}
	
	public Token(String word, int count) {
		this.word = word;
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
