package es.unizar.tmdad.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Tokenizer {
	private String bookContent;
	
	private Map<String, String> chapterMap;
	
	private String chapterDelimiter; 
	private Pattern chapterPattern; 
	private static final String titleDelimiter = "Title:";	
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
		setChapterType();

		String line;
		StringBuilder sb = null;
		String chapterTitle = "";
		//Process line by line
	    while ((line = br.readLine()) != null) {
	    	if(titlePattern.matcher(line).find()){
	    		chapterMap.put("Title", line.split(titleDelimiter)[1].substring(1));
	    	} else if (chapterPattern.matcher(line).find()) {
	    		if(sb != null){
	    			// We have detected a new chapter, so we have save previous results
	    			chapterMap.put(chapterTitle, sb.toString());
	    		}
	    		sb = new StringBuilder();
	    		if(line.split(getChapterDelimiter()).length > 0 && line.split(getChapterDelimiter())[1] != null) {
	    			chapterTitle = line.split(getChapterDelimiter())[1];
	    		}else{
	    			chapterTitle = line;
	    		}
	    	} else if (sb != null) {
	    		sb.append(line);
	    	}
	    } //TODO Migrate to Java 8 streams interface
	    br.close();
	    return chapterMap;
	}
	
	private void setChapterType(){
		if(regexMatches("CHAPTER\\s[IVXLCM]+\\.\\n") > 1){
			this.chapterDelimiter = "(?i)^CHAPTER\\s[IVXLCM]+\\.";
		}else if(regexMatches("CHAPTER\\s(([IVXLCM]+)|([0-9]+))\\n") > 1){
			this.chapterDelimiter = "(?i)^CHAPTER\\s(([IVXLCM]+)|([0-9]+))";
//		}else if(regexMatches("CHAPTER\\s[0-9]+\\n") > 1){
//			this.chapterDelimiter = "(?i)^CHAPTER\\s[0-9]+";
		//TODO Group similar expressions...
		}else if(regexMatches("CHAPTER\\s[0-9]\\.\\s") > 1){
			this.chapterDelimiter = "(?i)^CHAPTER\\s[0-9]\\.\\s";
		}else if(regexMatches("CHAPTER\\s[0-9]\\s") > 1){
			this.chapterDelimiter = "(?i)^CHAPTER\\s[0-9]\\s";
		}else if(regexMatches("CHAPTER\\s[IVXLCM]+\\.\\s") > 1){
			this.chapterDelimiter = "(?i)^CHAPTER\\s[IVXLCM]+\\.\\s";
		}else if(regexMatches("CHAPTER\\s[IVXLCM]+\\s") > 1){
			this.chapterDelimiter = "(?i)^CHAPTER\\s[IVXLCM]+\\s";
		}else if(regexMatches("CHAPTER\\n") > 1){
			this.chapterDelimiter = "(?i)^CHAPTER\\n";
		}
		this.chapterPattern = Pattern.compile(chapterDelimiter, Pattern.CASE_INSENSITIVE);
	}

	private int regexMatches(String regex){
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		List<String> list = new ArrayList<String>();
		Matcher m = p.matcher(bookContent);
		while (m.find()) {
		    list.add(m.group());
		}
		return list.size();
	}
	public String getChapterDelimiter() {
		return chapterDelimiter;
	}
	public Pattern getChapterPattern() {
		return chapterPattern;
	}
}
