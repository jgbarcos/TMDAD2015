package es.unizar.tmdad.webgui.services.themesdb;

public class TokenElement {
	private long themeId;
	private long id;
	private String word;
	
	public TokenElement(long themeId, long id, String word){
		this.themeId = themeId;
		this.id = id;
		this.word = word;
	}
	
	public long getThemeId(){
		return themeId;
	}
	
	public long getId(){
		return id;
	}

	public String getWord(){
		return word;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (themeId ^ (themeId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenElement other = (TokenElement) obj;
		if (id != other.id)
			return false;
		if (themeId != other.themeId)
			return false;
		return true;
	}
	
	
}
