package es.unizar.tmdad.controller;

import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unizar.tmdad.service.BookRaw;
import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.service.Chapter;

@RestController
public class TokenizerController {

	@RequestMapping(value="/tokenize", method={RequestMethod.POST})
	public BookTokenized tokenize(@RequestBody BookRaw bookRaw, @RequestParam("terms[]") List<String> terms){ //
		
		//TODO BookRaw -> BookTokenized
		System.out.println("BOOK CONTENT: " + bookRaw.getContent().substring(0, 200) + "...");
		System.out.println(terms);
		
		// Fill with dummy data
		BookTokenized bookTok = new BookTokenized();
		bookTok.setId(bookRaw.getId());
		bookTok.setTitle("Alice's Adventures in Wonderland");
		
		createChapter(bookTok, terms, 1, "Down the Rabbit-Hole");
		createChapter(bookTok, terms, 2, "The Pool of Tears");
		createChapter(bookTok, terms, 3, "A Caucus-Race and a long Tale");
		createChapter(bookTok, terms, 4, "The Rabbit sends in a little Bill");
		
		return bookTok;
	}
	
	private void createChapter(BookTokenized bookTok, List<String> terms, int id, String chTitle){
		Chapter ch = new Chapter(id, chTitle);
		
		// Fill with random terms
		Random rnd = new Random();
		for(String t : terms){
			if(rnd.nextFloat() < 0.6){
				ch.createToken(t, rnd.nextInt(10)+1);
			}
		}
		
		bookTok.getChapters().add(ch);
	}
}
