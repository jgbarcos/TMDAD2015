package mockupdb;

import java.util.Arrays;
import java.util.List;

public class PredefinedData {
	public static void fillDataBase(MockupThemesDB db){
		db.createTheme("Accion", 
			"cuchillo", "lanza", "matar");
		db.createTheme("Amor", 
			"amor", "beso", "boda");
		db.createTheme("Humor", 
			"risa", "bromea");
	}
}
