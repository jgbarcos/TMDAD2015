package es.unizar.tmdad.tokenizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.service.Chapter;
import es.unizar.tmdad.service.Token;

public class Tokenizer {
	private String bookContent;
	private List<String> terms;
	private BookTokenized bookTok;
	
	private Map<String, String> chapterMap;
	
	private static final String chapterDelimiter = "CHAPTER";
	private static final String titleDelimiter = "Title";	
	
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
		System.out.println("TOKENIZE HAS BEEN CALLED!!! " + this.bookContent);
//		BufferedReader br = new BufferedReader(new StringReader(bookContent));
		//Just to test
		FileInputStream fstream = new FileInputStream("src\\main\\resources\\Alice's Adventures in Wonderland");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		//
		String line;
		StringBuilder sb = null;
		String chapterTitle = "";
		//Process line by line
	    while ((line = br.readLine()) != null) {
	    	if(line.contains(titleDelimiter)){
	    		bookTok.setTitle("Alice's Adventures in Wonderland"); //TODO
	    	} else if (line.contains(chapterDelimiter)){
	    		if(sb != null){
	    			// We have detected a new chapter, so we have save previous results
	    			chapterMap.put(chapterTitle, sb.toString());
	    		}
	    		sb = new StringBuilder();
	    		chapterTitle = line.split(".")[1];
	    	} else if (sb != null) {
	    		sb.append(line);
	    	}
	    } //TODO Migrate to Java 8 streams interface
	    br.close();
	    
	    //Just to test
	    int i = 0;
	    for (String key : chapterMap.keySet()) {
	        String value = chapterMap.get(key);
	        bookTok.addChapter(new Chapter(i, value));
	        i++;
	        System.out.println("Key = " + key + ", Value = " + value);
	    }
	    //
//		bookTok.addChapter(new Chapter(1, "Down the Rabbit-Hole"));
//		bookTok.addChapter(new Chapter(2, "The Pool of Tears"));
		
		bookTok.getChapters().get(0).addToken(new Token("rabbit", 7));
		bookTok.getChapters().get(0).addToken(new Token("cat", 2));
		bookTok.getChapters().get(0).addToken(new Token("cry", 3));
		bookTok.getChapters().get(1).addToken(new Token("rabbit", 1));
		bookTok.getChapters().get(1).addToken(new Token("bat", 2));
		
		return bookTok;
	}

	private void countChapterTokens(Chapter chapter, List<String> terms) {
		// //TODO From terms(list) to token(arrayList)
		chapter.addToken(new Token("rabbit", 7));
		chapter.addToken(new Token("cat", 2));
	}
	
}
