package es.unizar.tmdad.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unizar.tmdad.service.BookRaw;
import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.tokenizer.Tokenizer;
import es.unizar.tmdad.tokenizer.TokenizerDummy;

@RestController
@RequestMapping("/tokenize")
public class TokenizerController {

	@RequestMapping(method = RequestMethod.POST)
	public BookTokenized tokenize(@RequestBody BookRaw bookRaw, @RequestParam("terms[]") List<String> terms){ 	
		//REMOVE: Used just to check input parameters
		System.out.println("BOOK CONTENT: " + bookRaw.getContent());
		System.out.println(terms);
		//
		
		BookTokenized bookTok = new BookTokenized(bookRaw.getId());
		bookTok.setTitle("Alice's adventures in wonderland");
		//TODO BookRaw -> BookTokenized
//		try{
			TokenizerDummy tokenizer = new TokenizerDummy(bookRaw.getContent(), terms, bookTok);
			return tokenizer.tokenize();
//		}catch(IOException ioex){
//			//Do nothing...
//			System.out.println(ioex);
//		}
//		return bookTok;
	}
}
