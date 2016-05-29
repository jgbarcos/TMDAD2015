package es.unizar.tmdad.analyzer.services.db;

import java.util.HashMap;
import java.util.Map;

public class UserElement {
	public String username;
	public String password;
	public Map<Long, ThemeDAO> themes = new HashMap<Long, ThemeDAO>();
	
	public UserElement(String username, String password){
		this.username = username;
		this.password = password;
	}
}
