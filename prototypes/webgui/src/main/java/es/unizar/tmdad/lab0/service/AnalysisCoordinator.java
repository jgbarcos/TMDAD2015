package es.unizar.tmdad.lab0.service;
import es.unizar.tmdad.lab0.service.BookResult;
import es.unizar.tmdad.lab0.service.JSONReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;


import org.json.JSONException;
import org.json.JSONObject;

 
public class AnalysisCoordinator {
	
	private String Gateway_Ip;
	private int Gateway_Port;
	private String Tokenizer_Ip;
	private int Tokenizer_Port;
	
	public BookRaw Gateway(int ID) throws IOException, JSONException{
		//Connect to the gateway to get the book
		JSONReader Reader = new JSONReader();
		String URL = "http://"+Gateway_Ip+":"+Gateway_Port+"/searchBook?id="+ID;
		JSONObject json = Reader.readJsonFromUrl(URL);
	    System.out.println(json.toString());
	    
	    BookRaw bookRaw = new BookRaw(ID,json.toString());
	    
	    return bookRaw;
	}
	
	public BookResult StartAnalysis (int ID , String Terms) throws IOException, JSONException{
		
		BookRaw bookRaw = this.Gateway(ID);
		JSONObject result = this.Tokenizer(ID,bookRaw,Terms);
		return null;
		
	}
	
	public JSONObject Tokenizer (int ID, BookRaw bookRaw, String Terms) throws IOException, JSONException{
		
		String URL = "http://"+Tokenizer_Ip+":"+Tokenizer_Port+"/tokenizer";
		Map<String, String> vars = new HashMap<String, String>();
        vars.put("terms", Terms);

        RestTemplate restTemplate = new RestTemplate();
        
        BookTokenized bookTokenized = restTemplate.postForObject(URL, bookRaw, BookTokenized.class,vars);		
        //BookTokenized to JSON
        return null;
	}
}
