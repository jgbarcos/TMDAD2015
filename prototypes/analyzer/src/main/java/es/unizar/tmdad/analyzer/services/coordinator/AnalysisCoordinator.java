package es.unizar.tmdad.analyzer.services.coordinator;
import es.unizar.tmdad.analyzer.interfacing.BookRaw;
import es.unizar.tmdad.analyzer.interfacing.BookTokenized;
import es.unizar.tmdad.analyzer.interfacing.Chapter;
import es.unizar.tmdad.analyzer.interfacing.Token;

import es.unizar.tmdad.model.BookResult;
import es.unizar.tmdad.analyzer.services.db.AnalysisDB;
import es.unizar.tmdad.analyzer.services.db.AnalysisDBMockup;
import es.unizar.tmdad.analyzer.services.db.BookAnalysisDAO;
import es.unizar.tmdad.analyzer.services.db.BookDAO;
import es.unizar.tmdad.analyzer.services.db.ChapterAnalysisDAO;
import es.unizar.tmdad.analyzer.services.db.ResourceDAO;
import es.unizar.tmdad.analyzer.services.db.ResourceStatus;

import es.unizar.tmdad.analyzer.messaging.GatewayRPCClient;
import es.unizar.tmdad.analyzer.messaging.TokenizerRPCClient;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

 
@Component
public class AnalysisCoordinator {
	//TODO externalize to properties file
	private String Gateway_Ip = "localhost";
	private int Gateway_Port = 8091;
	private String Tokenizer_Ip = "localhost";
	private int Tokenizer_Port = 8090;
	
	private AnalysisDB db;
	
	public AnalysisCoordinator(){
		db = new AnalysisDBMockup();
	}
	
	public AnalysisDB getDb(){
		return db;
	}
	
	@Async
	public void runAnalysis(ResourceDAO resource){
		
		//Get tokens
		List<String> tokens = resource.getTag().values().stream()
			.flatMap(List::stream)
			.distinct()
			.collect(Collectors.toList());
		

		getDb().updateResourceStatusById(resource.getId(), ResourceStatus.GATEWAY);
		BookRaw bookRaw = callGatewayRPC(Long.toString(resource.getBookId()));

		// TODO: tokenizer should not return anything
		getDb().updateResourceStatusById(resource.getId(), ResourceStatus.TOKENIZER);
		BookTokenized tokenized = callTokenizerRPC(bookRaw, tokens);
		
		// TODO: this should be done by the tokenizer when book info is known
		// Add book info
		List<String> chapterNames = new ArrayList<String>();
		for(Chapter ch : tokenized.getChapters()){
			chapterNames.add(ch.getTitle());
		}
		
		BookDAO book = new BookDAO(resource.getBookId(), tokenized.getTitle(), chapterNames);
		getDb().createBook(book);	
		
		// TODO: this should be done by the tokenizer whenever a new token count is obtained
		// Add tokens as analyzed
		for(Chapter ch : tokenized.getChapters()){
			for(Token tk : ch.getTokens()){
				getDb().createAnalysis(resource.getBookId(), ch.getId(), tk.getWord(),  tk.getCount());
			}
		}

		// TODO: this should be done by the tokenizer
		getDb().updateResourceStatusById(resource.getId(), ResourceStatus.FINISHED);
		
	}
	
	public BookResult recoverResult(long resourceId) {
		ResourceDAO resource = getDb().findResourceById(resourceId);
		
		//Get tokens
		List<String> tokens = resource.getTag().values().stream()
			.flatMap(List::stream)
			.distinct()
			.collect(Collectors.toList());
		
		BookAnalysisDAO analysis = getDb().findAnalysisByBookAndTokens(resource.getBookId(), tokens);
		
		BookResult result = new BookResult(analysis.getId(), analysis.getTitle());
		for(ChapterAnalysisDAO ch : analysis.getChapters()){				
			for(String theme : resource.getTag().keySet()){
				for(String token : resource.getTag().get(theme)){
					long count = ch.getCounts().getOrDefault(token, 0L);
					result.addToken(theme, (int)ch.getNumChapter(), ch.getTitle(), token, count);
					
				}
			}
		}
		
		result.setStatus(resource.getStatus());
		
		return result;
	}	
	
	private BookRaw callGatewayREST(long l){
		
    	String url = "http://"+Gateway_Ip+":"+Gateway_Port+"/searchBook?book="+l;		
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, BookRaw.class);
	}
	
	//RabbitMQ request
	private BookRaw callGatewayRPC(String book){
		GatewayRPCClient gatewayRpc = null;
	    BookRaw response = null;
	    try {
	    	gatewayRpc = new GatewayRPCClient();
	    	System.out.println(" [x] Requesting a book via RPC...");
	    	response = gatewayRpc.call(book);
	    	System.out.println(" [.] Got '" + response + "'");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      if (gatewayRpc!= null) {
	        try {
	        	gatewayRpc.close();
	        }
	        catch (Exception ignore) {}
	      }
	    }
		return response;
	}
	
	//REST request
	private BookTokenized callTokenizerREST (BookRaw bookRaw, List<String> tokens){
		String url = "http://"+Tokenizer_Ip+":"+Tokenizer_Port+"/tokenize";
		
		// Only way to append parameter as a list, do not mix with RestTemplate uriVariables
		url += "?" + arrayParams("terms[]", tokens);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url,  bookRaw,  BookTokenized.class);
	}
	
	//RabbitMQ request
	private BookTokenized callTokenizerRPC(BookRaw bookRaw, List<String> tokens) {
		TokenizerRPCClient tokenizerRpc = null;
	    BookTokenized response = null;
	    try {
	    	tokenizerRpc = new TokenizerRPCClient();
	    	System.out.println(" [x] Requesting tokenization via RPC...");
	    	response = tokenizerRpc.call(bookRaw, tokens);
	    	System.out.println(" [.] Got '" + response + "'");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    finally {
	      if (tokenizerRpc!= null) {
	        try {
	        	tokenizerRpc.close();
	        }
	        catch (Exception ignore) {}
	      }
	    }
		return response;
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
