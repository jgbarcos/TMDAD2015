package es.unizar.tmdad.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	private String bookContent;
	
	private Map<String, String> chapterMap;
	
	private static final String chapterDelimiter = "^CHAPTER"; //|^[IVXLCM]*$
	private static final Pattern chapterPattern = Pattern.compile(chapterDelimiter);
	private static final String titleDelimiter = "Title";	
	private static final Pattern titlePattern = Pattern.compile(titleDelimiter);
	
	public Tokenizer(String bookContent) {
		super();
		this.bookContent = bookContent;
		this.chapterMap = new HashMap<String, String>();
	}
	
	public Map<String, String> tokenize() throws IOException{
		//I've call this function tokenize but firtstly it fill create a structure of chapters
		//and then it will count tokens on each chapter.
		BufferedReader br = new BufferedReader(new StringReader(bookContent));

		String line;
		StringBuilder sb = null;
		String chapterTitle = "";
		//Process line by line
	    while ((line = br.readLine()) != null) {
	    	if(titlePattern.matcher(line).find()){
//	    	if(line.contains(titleDelimiter)){
	    		chapterMap.put("Title", line.split(":")[1].substring(1));
//	    	} else if (line.contains(chapterDelimiter)){
	    	} else if (chapterPattern.matcher(line).find()) {
	    		if(sb != null){
	    			// We have detected a new chapter, so we have save previous results
	    			chapterMap.put(chapterTitle, sb.toString());
	    		}
	    		sb = new StringBuilder();
	    		if(line.contains(".") && line.split("\\.")[1] != null){ //I'll changed it...
	    			chapterTitle = line.split("\\.")[1].substring(1);
	    		} else {
	    			chapterTitle = line;
	    		}
	    	} else if (sb != null) {
	    		sb.append(line);
	    	}
	    } //TODO Migrate to Java 8 streams interface
	    br.close();
	    return chapterMap;
	}
	
}
