package es.unizar.tmdad.webgui.services;

import java.util.List;

import org.springframework.web.client.RestTemplate;

public class RestProxy {
	private boolean isGet;
	private String endpoint;
	
	private String urlParams = "";
	private Object requestBody = null;
	
	private  RestProxy(boolean isGet, String endpoint){
		this.isGet = isGet;
		this.endpoint = endpoint;
	}
	
	public static RestProxy createGet(String endpoint){
		return new RestProxy(true, endpoint);
	}
	public static RestProxy createPost(String endpoint){
		return new RestProxy(false, endpoint);
	}
	
	public String forward(){
		String url = endpoint + urlParams;
		
		System.out.println("FORWARDING: " + url);

		RestTemplate restTemplate = new RestTemplate();
		if(isGet){
			return restTemplate.getForObject(url, String.class);
		}
		else{
			return restTemplate.postForObject(url, requestBody , String.class);
		}
	}
		
	public RestProxy addParam(String key, String value){
		//key = key.replaceAll("\\[", "%5B").replaceAll("\\]", "%5D");
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
