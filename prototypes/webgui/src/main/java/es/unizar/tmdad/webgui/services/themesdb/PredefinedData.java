package es.unizar.tmdad.webgui.services.themesdb;

public class PredefinedData {
	public static void fillDataBase(MockupThemesDB db){
		db.createTheme("Accion", 
			"cuchillo", "lanza", "matar");
		db.createTheme("Amor", 
			"amor", "beso", "boda");
		db.createTheme("Humor", 
			"risa", "bromea");
		db.createTheme("Animal", 
			"gato", "conejo", "perro");
		
	}
}
