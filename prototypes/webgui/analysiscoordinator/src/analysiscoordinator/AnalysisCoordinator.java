package es.unizar.tmdad.lab0.service;
import es.unizar.tmdad.lab0.service.BookResult;
import JSONReader;

public class AnalysisCoordinator {
	
	Private string Gateway_Ip;
	Private int Gateway_Port;
	Private string Tokenizer_Ip;
	Private int Tokenizer_Port;
	
	asfj
	public JSONObject Gateway(String ID){
		//Connect to the gateway to get the book
		
		URL website = "http://"+Gateway_Ip+":"+Gateway_Port+"/searchBook?id="+ID;
		JSONObject json = readJsonFromUrl(website);
	    System.out.println(json.toString());
	    return json
	}
	
	public BookResult StartAnalysis (String ID, <ArrayList> Terms){
		
		book = this.Gateway(ID);
		result = this.Toeknizer(book)
		return null;
		
	}
	
	public JSONObject result Tokenizer (JSONObject book){
		//send the book to the tokenizer and return the results
	}
}
