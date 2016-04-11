package mockupdb;

import java.util.Collections;
import java.util.List;

public class Main {
	private static String createHeader(String content){
		return "\n" + content + "\n"
				+ String.join("", Collections.nCopies(content.length(), "="));
	}
	
	public static void main (String[] args){
		ThemesDB db = new MockupThemesDB();
		
		System.out.println(createHeader("Get Theme with ID=0"));
		System.out.println(db.getTheme(0));
		
		System.out.println(createHeader("Get Theme \"Humor\""));
		System.out.println(db.getTheme("Humor"));
		
		System.out.println(createHeader("Themes like \"A\""));
		List<Theme> res = db.likeTheme("A");
		for(Theme t : res){
			System.out.println(t);
		}
		
		System.out.println(createHeader("Create theme \"Tiempo=[mes,dia,hora]\""));
		db.createTheme("Tiempo", "mes", "dia", "hora");
		res = db.likeTheme("");
		for(Theme t : res){
			System.out.println(t);
		}
	}
}
