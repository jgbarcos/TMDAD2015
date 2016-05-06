package es.unizar.tmdad.analyzer.services.themesdb;

public class ThemeElement {

	private long id;
	private String title;
	
	public ThemeElement(long id, String title){
		this.id = id;
		this.title = title;
	}

	public long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		ThemeElement other = (ThemeElement) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
