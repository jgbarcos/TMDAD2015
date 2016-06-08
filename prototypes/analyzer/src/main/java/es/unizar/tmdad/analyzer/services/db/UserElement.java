package es.unizar.tmdad.analyzer.services.db;

import java.util.HashMap;
import java.util.Map;

import es.unizar.tmdad.analyzer.services.db.model.Theme;

public class UserElement {
	public String username;
	public String password;
	public Map<Long, Theme> themes = new HashMap<Long, Theme>();
	
	public UserElement(String username, String password){
		this.username = username;
		this.password = password;
	}
}
