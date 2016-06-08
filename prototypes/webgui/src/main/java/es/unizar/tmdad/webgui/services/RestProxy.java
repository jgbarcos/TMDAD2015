package es.unizar.tmdad.webgui.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import es.unizar.tmdad.model.ThemeDAO;

public class RestProxy {
	private HttpMethod method;
	private String endpoint;
	
	private String urlParams = "";
	private Object requestBody = null;
	
	private  RestProxy(HttpMethod method, String endpoint){
		this.method = method;
		this.endpoint = endpoint;
	}
	
	public static RestProxy createGet(String endpoint){
		return new RestProxy(HttpMethod.GET, endpoint);
	}
	public static RestProxy createPost(String endpoint){
		return new RestProxy(HttpMethod.POST, endpoint);
	}
	public static RestProxy createPut(String endpoint){
		return new RestProxy(HttpMethod.PUT, endpoint);
	}
	
	private String getURL(){
		String url = endpoint + urlParams;

		System.out.println("FORWARDING: " + url);
		
		return url;
	}
	
	public String expectsString(){
		String url = getURL();
		RestTemplate restTemplate = new RestTemplate();
		if(method.equals(HttpMethod.GET)){
			return restTemplate.getForObject(url, String.class);
		}
		else {
			return restTemplate.postForObject(url, requestBody , String.class);
		}
	}
	
	public ResponseEntity<ThemeDAO> expectsTheme(){
		String url = getURL();
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ThemeDAO> entity = new HttpEntity<ThemeDAO>((ThemeDAO)requestBody, headers);
		
		
		ResponseEntity<ThemeDAO> response = restTemplate.exchange(url, method, entity, ThemeDAO.class); 
		
		// Get response headers and content
		headers = new HttpHeaders();
		if(response.getHeaders() != null){
			if(response.getHeaders().getLocation() != null){
				headers.setLocation(response.getHeaders().getLocation());
			}
		}
		return new ResponseEntity<ThemeDAO>(response.getBody(), headers, response.getStatusCode());
	}
	
	public List<ThemeDAO> expectsThemeList(){
		String url = getURL();
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ThemeDAO> entity = new HttpEntity<ThemeDAO>((ThemeDAO)requestBody, headers);
		
		
		ResponseEntity<ThemeDAO[]> response = restTemplate.exchange(url, method, entity, ThemeDAO[].class); 
		
		List<ThemeDAO> themes = Arrays.asList(response.getBody());
		return themes;
	}
	
	public ResponseEntity<?> expectsResponseEntity(){
		String url = getURL();

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> response = restTemplate.exchange(url, method, null, String.class, requestBody); 

		return response;
	}
		
	public RestProxy addParam(String key, String value){
		if(!value.equals("") && value != null){
			if(urlParams.equals("")){
				urlParams = "?";
			}
			else{
				urlParams += "&";
			}
			
			urlParams += key+"="+value;
		}
		return this;
	}
	
	public RestProxy addParam(String key, List<String> values){
		for(String v : values){
			addParam(key,v);
		}
		return this;
	}
	
	public RestProxy setBody(Object body){
		requestBody = body;
		return this;
	}
}
