package es.unizar.tmdad.tokenizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.service.Chapter;
import es.unizar.tmdad.service.Token;

public class Tokenizer {
	private String bookContent;
	private List<String> terms;
	private BookTokenized bookTok;
	
	private Map<String, String> chapterMap;
	
	private static final String chapterDelimiter = "^CHAPTER|^Chapter|^[IVXLCM]*$";
	private static final Pattern chapterPattern = Pattern.compile(chapterDelimiter);
	private static final String titleDelimiter = "Title";	
	private static final Pattern titlePattern = Pattern.compile(titleDelimiter);
	
	public Tokenizer(String bookContent, List<String> terms, BookTokenized bookTok) {
		super();
		this.bookContent = bookContent;
		this.terms = terms;
		this.bookTok = bookTok;
		this.chapterMap = new HashMap<String, String>();
	}
	
	public BookTokenized tokenize() throws IOException{
		//I've call this function tokenize but firtstly it fill create a structure of chapters
		//and then it will count tokens on each chapter.

//		BufferedReader br = new BufferedReader(new StringReader(bookContent));
		//Just to test
		FileInputStream fstream = new FileInputStream("src\\main\\resources\\Metamorphosis"); 
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		//
		String line;
		StringBuilder sb = null;
		String chapterTitle = "";
		//Process line by line
	    while ((line = br.readLine()) != null) {
	    	if(titlePattern.matcher(line).find()){
//	    	if(line.contains(titleDelimiter)){
	    		bookTok.setTitle(line.split(":")[1].substring(1)); 
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
	    
	    //Just to test
	    int i = 0;
	    for (Map.Entry<String, String> entry : chapterMap.entrySet()) {
	        String cTitle = entry.getKey();
	        String cContent = entry.getValue();
	        Chapter chapter = new Chapter(i, cTitle);
	        bookTok.addChapter(chapter);
	        countChapterTokens(chapter, cContent, terms);
	        i++;
	    } //TODO Migrate to Java 8 streams interface

		return bookTok;
	}

	private void countChapterTokens(Chapter chapter, String cContent, List<String> terms) {
		for (String term: terms){
			int occurance = StringUtils.countMatches(cContent, term);
			if(occurance != 0){
				chapter.addToken(new Token(term, occurance));
			}
		}
	}
	
}
