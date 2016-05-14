package es.unizar.tmdad.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unizar.tmdad.service.BookRaw;
import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.tokenizer.Counter;
import es.unizar.tmdad.tokenizer.Tokenizer;
import es.unizar.tmdad.tokenizer.TokenizerDummy;

@RestController
@RequestMapping("/tokenize")
public class TokenizerController {

	@RequestMapping(method=RequestMethod.POST)
	public BookTokenized tokenize(@RequestBody BookRaw bookRaw, @RequestParam("terms[]") List<String> terms){ 
		
		//BookRaw -> BookTokenized
		System.out.println("BOOK CONTENT: " + bookRaw.getContent().substring(0, 200) + "...");
		System.out.println(terms);
		
		BookTokenized bookTok = new BookTokenized(bookRaw.getId());
		try{
			Tokenizer tokenizer = new Tokenizer(bookRaw.getContent());
			Map<String, String> chapters = tokenizer.tokenize();
			Counter counter = new Counter(bookTok, chapters, terms);
			return counter.countTerms();
		}catch(IOException ioex){
			//TODO It should return some standard error
			System.out.println(ioex);
		}
		return null;
	}
	
}
