package es.unizar.tmdad.model;

import java.util.ArrayList;
import java.util.List;

public class ThemeResult {
	private String name;
	private List<TokenResult> tokens;
	private long totalcount;
	private float percentage; //TODO: fill percentage
	
	public ThemeResult(String name){
		this.name = name;
		tokens = new ArrayList<TokenResult>();
		percentage = 1;
	}
	
	public static void calculatePercentage(List<ThemeResult> themes){
		long totalcount = 0;
		for(ThemeResult t : themes){
			totalcount += t.getTotalcount();
		}
		for(ThemeResult t : themes){
			t.setPercentage(100.0f*(float)t.getTotalcount() / totalcount);
		}
	}
	
	public void addToken(String tokenName, long count){
		TokenResult token = tokens.stream()
								  .filter(t -> t.getWord().equals(tokenName))
		  						  .findFirst().orElse(null);
		if(token == null){
			token = new TokenResult(tokenName);
			tokens.add(token);
		}
		token.addToken(count);
		
		totalcount += count;
	}

	public String getName() {
		return name;
	}	
	
	public long getTotalcount() {
		return totalcount;
	}

	public float getPercentage() {
		return percentage;
	}
	
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public List<TokenResult> getTokens() {
		return tokens;
	}		
}
