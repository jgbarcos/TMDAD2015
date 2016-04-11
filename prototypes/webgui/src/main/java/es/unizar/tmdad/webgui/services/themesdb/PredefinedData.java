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

		db.createTheme("Fantasy", 

				"Queen", "King", "gryphon","mad","creature","creatures","heaven","Dwarfish","Elfin","Fey","Runes","Sorcerous","Thews","Wizardly","Gods","Magic");

		db.createTheme("Comedy", 

				"mock","dance","joke","laugh");

		db.createTheme("Animals", 

				"cat","turtle","rabbit","pig","fish","pigs","cats","whale","whales","leviathan","dog");

		db.createTheme("Mistery",

				"curious","silence","afraid","curiosity","secret","Evidentiary","Improbable","Counterintelligence","Directives","Encryption","Megatonnage","Protocol");

		db.createTheme("Action",

				"soldiers","gun","pistol","fire","murder","help","live","death","executed","captain","dead","blood","sword","knife","battle","skull");

		db.createTheme("Nature",

				"sea","beach","tree","mushroom","ship","boat","boats","air","sun","water","sky","snow","mountain","lake");

		db.createTheme("Cook", 

				"cook","kitchen","boil","bread");

		db.createTheme("Kids", 

				"children","kid","game","play","school","toy");

		db.createTheme("Love", 

				"love","sex","friend","couple","boyfriend","girlfriend","hug","heart","soul","wife","husband","Fingertips","Gasp","Gaze","Heartbeats","Loins","Sigh","Throb","Yearn");

		db.createTheme("Horror",

				"ghost","dark","darkness","fear","enchanted","Elder","Eldritch","Hideous","Ichor","Intone","Old Ones","Soulless","Squamous","Sublimed","Tenebrous","afraid");

		db.createTheme("Historical", 

				"Baronial","Breeches","Centurion","Codpiece","Curricle","Donjon","Egad","Garderobe","Hallowed","Majestic","Manservant","Reticule","Salver","Sirrah","Snuffbox","Varlet");

		db.createTheme("Science Fiction", 

				"Actinic","Anomaly","Ansible","Continuum","Vortex","Enhanced","FTL","Wormhole","Nanites","Neutronium","Noosphere","Sentient","Singularity","Subspace","Terraform");

		db.createTheme("Western", 

				"Corral","Desperado","Gunslinger","Rugged","Sagebrush","Saloon","Solitary","Wrangler");

	}

}

