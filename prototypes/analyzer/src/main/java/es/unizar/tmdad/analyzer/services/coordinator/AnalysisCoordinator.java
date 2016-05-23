package es.unizar.tmdad.analyzer.services.coordinator;
import es.unizar.tmdad.analyzer.interfacing.BookRaw;
import es.unizar.tmdad.analyzer.interfacing.BookTokenized;
import es.unizar.tmdad.analyzer.interfacing.Chapter;
import es.unizar.tmdad.analyzer.interfacing.Token;
import es.unizar.tmdad.analyzer.service.AnalysisResource;
import es.unizar.tmdad.analyzer.services.themesdb.MockupThemesDB;
import es.unizar.tmdad.analyzer.services.themesdb.Theme;
import es.unizar.tmdad.analyzer.services.themesdb.ThemesDB;
import es.unizar.tmdad.model.BookResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

 
@Component
public class AnalysisCoordinator {
	//TODO externalize to properties file
	private String Gateway_Ip = "localhost";
	private int Gateway_Port = 8091;
	private String Tokenizer_Ip = "localhost";
	private int Tokenizer_Port = 8090;
	
	private ThemesDB db;
	
	// id_libro | id_capitulo | id_termino | count
	
	// 
	
	public AnalysisCoordinator(){
		db = new MockupThemesDB();
	}
	
	public ThemesDB getDb(){
		return db;
	}
	
	public void fillTokens(AnalysisResource resource){
		// Get theme objects
		List<Theme> themes = resource.themes.stream()
				   .map(t -> db.getTheme(t))
				   .filter(t -> t != null)
				   .collect(Collectors.toList());
		
		// Get list of tokens
		List<String> tokens = new ArrayList<>();
		for(Theme t : themes){
			tokens.addAll(t.getTokens());
		}
		
		// Remove duplicates
		tokens = tokens.stream().distinct().collect(Collectors.toList());
		
		resource.tokens = tokens;
		resource.themeObjects = themes;
	}
	
	public void performAnalysis(AnalysisResource resource){
		fillTokens(resource);

		// Perform analysis
		BookRaw bookRaw = callGateway(Integer.parseInt(resource.bookId));
		BookTokenized tokenized = callTokenizer(bookRaw,resource.tokens);
		
		resource.result = formatResult(resource.themeObjects, tokenized);
		
		System.out.println("Analysis "+resource.resourceId+" performed!");
	}

	public BookResult formatResult(List<Theme> themes, BookTokenized tokenized) {
		// Format BookTokenized to BookResult (include theme information)
		BookResult result = new BookResult(tokenized.getId(), tokenized.getTitle());
		for(Chapter ch : tokenized.getChapters()){
			for(Token tk : ch.getTokens()){
				for(Theme th : themes){
					// Check if theme contains token
					if(th.getTokens().contains(tk.getWord())){
						result.addToken(th.getTitle(), 
							ch.getId(), ch.getTitle(), 
							tk.getWord(), tk.getCount());
					}
				}
			}
		}
		return result;
	}
	
	private BookRaw callGateway(int book){
		
    	String url = "http://"+Gateway_Ip+":"+Gateway_Port+"/searchBook?book="+book;		
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, BookRaw.class);
		
	}
	
	private BookTokenized callTokenizer (BookRaw bookRaw, List<String> tokens){
		String url = "http://"+Tokenizer_Ip+":"+Tokenizer_Port+"/tokenize";
		
		// Only way to append parameter as a list, do not mix with RestTemplate uriVariables
		url += "?" + arrayParams("terms[]", tokens);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url,  bookRaw,  BookTokenized.class);
	}
	
	private String arrayParams(String param, List<String> items){
		String url = "";
		for(int i=0; i<items.size(); i++){
			url+=param+"="+items.get(i);
			if(i < items.size() - 1){
				url += "&";
			}
		}
		return url;
	}
	
	private String readFile(String filepath){
		String content = null;
		try {
			content = new String(Files.readAllBytes(Paths.get(filepath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
