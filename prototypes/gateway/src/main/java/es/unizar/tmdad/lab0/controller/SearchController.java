package es.unizar.tmdad.lab0.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unizar.tmdad.lab0.model.PieceOfBook;
import es.unizar.tmdad.lab0.service.SerarchService;



@RestController
public class SearchController {

    @RequestMapping("/searchBook")
    public PieceOfBook search(@RequestParam("book") String bookId) {
		
		return SerarchService.getBook(bookId, 1).get(0);
    }
}