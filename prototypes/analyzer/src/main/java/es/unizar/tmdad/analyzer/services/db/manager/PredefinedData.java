package es.unizar.tmdad.analyzer.services.db.manager;

import java.util.Arrays;

import es.unizar.tmdad.analyzer.services.db.model.Theme;

public class PredefinedData {
	
	private static String MOCKUP_USERNAME = "0";
	private static String MOCKUP_PASSWROD = "unsafe_password";
	
	private static void createTheme(AnalysisDB db, String title, String... tokenNames){
		Theme theme = new Theme(-1, title, Arrays.asList(tokenNames));
		db.createThemeOfUser(MOCKUP_USERNAME, theme);
	}
	
	public static void fillDatabase(AnalysisDB db){
		fillUsers(db);
		fillThemes(db);
	}
	
	
	private static void fillUsers(AnalysisDB db){
		db.createUser(MOCKUP_USERNAME, MOCKUP_PASSWROD);
	}

	private static void fillThemes(AnalysisDB db){

		createTheme(db, "Fantasy", 
			"Queen", "King", "gryphon","mad","creature","creatures","heaven","Dwarfish","Elfin","Fey","Runes","Sorcerous","Thews","Wizardly","Gods","Magic");
		createTheme(db, "Comedy", 
			"mock","dance","joke","laugh");
		createTheme(db, "Animals", 
			"cat","turtle","rabbit","pig","fish","pigs","cats","whale","whales","leviathan","dog");
		createTheme(db, "Mistery",
			"curious","silence","afraid","curiosity","secret","Evidentiary","Improbable","Counterintelligence","Directives","Encryption","Megatonnage","Protocol");
		createTheme(db, "Action",
			"soldiers","gun","pistol","fire","murder","help","live","death","executed","captain","dead","blood","sword","knife","battle","skull");
		createTheme(db, "Nature",
			"sea","beach","tree","mushroom","ship","boat","boats","air","sun","water","sky","snow","mountain","lake");
		createTheme(db, "Cook", 
			"cook","kitchen","boil","bread");
		createTheme(db, "Kids", 
			"children","kid","game","play","school","toy");
		createTheme(db, "Love", 
			"love","sex","friend","couple","boyfriend","girlfriend","hug","heart","soul","wife","husband","Fingertips","Gasp","Gaze","Heartbeats","Loins","Sigh","Throb","Yearn");
		createTheme(db, "Horror",
			"ghost","dark","darkness","fear","enchanted","Elder","Eldritch","Hideous","Ichor","Intone","Old Ones","Soulless","Squamous","Sublimed","Tenebrous","afraid");
		createTheme(db, "Historical", 
			"Baronial","Breeches","Centurion","Codpiece","Curricle","Donjon","Egad","Garderobe","Hallowed","Majestic","Manservant","Reticule","Salver","Sirrah","Snuffbox","Varlet");
		createTheme(db, "Science Fiction", 
			"Actinic","Anomaly","Ansible","Continuum","Vortex","Enhanced","FTL","Wormhole","Nanites","Neutronium","Noosphere","Sentient","Singularity","Subspace","Terraform");
		createTheme(db, "Western", 
			"Corral","Desperado","Gunslinger","Rugged","Sagebrush","Saloon","Solitary","Wrangler");

	}

}

