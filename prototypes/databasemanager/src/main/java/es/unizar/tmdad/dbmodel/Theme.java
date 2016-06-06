package es.unizar.tmdad.dbmodel;

import java.util.Set;

public class Theme {
	private long id;
	private String name;
	private Set<String> terms;
	
	public Theme(long id, String name, Set<String> terms) {
		this.id = id;
		this.name = name;
		this.terms = terms;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<String> getTerms() {
		return terms;
	}
	public void setTerms(Set<String> terms) {
		this.terms = terms;
	}
	
}
