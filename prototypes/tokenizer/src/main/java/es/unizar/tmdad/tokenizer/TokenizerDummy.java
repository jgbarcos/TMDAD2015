package es.unizar.tmdad.tokenizer;

import java.util.ArrayList;
import java.util.List;

import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.service.Chapter;
import es.unizar.tmdad.service.Token;

public class TokenizerDummy {
	private String bookContent;
	private List<String> terms;
	private BookTokenized bookTok;
	
	public TokenizerDummy(String bookContent, List<String> terms, BookTokenized bookTok) {
		super();
		this.bookContent = bookContent;
		this.terms = terms;
		this.bookTok = bookTok;
	}
	
	public BookTokenized tokenize(){
		//I've call this function tokenize but firtstly it fill create a structure of chapters
		//and then it will count tokens on each chapter.
		System.out.println("TOKENIZE HAS BEEN CALLED!!! " + this.bookContent);
		bookTok.addChapter(new Chapter(1, "Down the Rabbit-Hole"));
		bookTok.addChapter(new Chapter(2, "The Pool of Tears"));
		
		System.out.println(bookTok.getChapters().size());
		
		bookTok.getChapters().get(0).setTokens(new ArrayList<Token>());
		bookTok.getChapters().get(0).addToken(new Token("rabbit", 7));
		bookTok.getChapters().get(0).addToken(new Token("cat", 2));
		bookTok.getChapters().get(0).addToken(new Token("cry", 3));
		bookTok.getChapters().get(1).setTokens(new ArrayList<Token>());
		bookTok.getChapters().get(1).addToken(new Token("rabbit", 1));
		bookTok.getChapters().get(1).addToken(new Token("bat", 2));
		
		return bookTok;
	}
}
