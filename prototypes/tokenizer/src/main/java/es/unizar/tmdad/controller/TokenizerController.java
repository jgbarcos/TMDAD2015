package es.unizar.tmdad.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unizar.tmdad.service.BookRaw;
import es.unizar.tmdad.service.BookTokenized;

@RestController
@RequestMapping("/tokenize")
public class TokenizerController {

	@RequestMapping(method = RequestMethod.POST)
	public BookTokenized tokenize(@RequestBody BookRaw bookRaw, @RequestParam("terms") String terms){ //
		
		//TODO BookRaw -> BookTokenized
		System.out.println("BOOK CONTENT: " + bookRaw.getContent());
		String[] termsList = terms.split(",");
		System.out.println(terms);
		//
		
		BookTokenized bookTok = new BookTokenized();
		bookTok.setId(0);
		bookTok.setTitle("Alice's Adventures in Wonderland");
		return bookTok;
	}
}
