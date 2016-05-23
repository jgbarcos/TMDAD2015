package es.unizar.tmdad.analyzer.service;

import java.util.List;

import es.unizar.tmdad.analyzer.services.themesdb.Theme;
import es.unizar.tmdad.model.BookResult;

public class AnalysisResource {
	public String resourceId;
	public String bookId;
	
	public List<String> themes;
	public List<String> tokens;
	
	public List<Theme> themeObjects;
	
	public BookResult result = null;
	
	public AnalysisResource(String resourceId, String bookId, List<String> themes){
		this.resourceId = resourceId;
		this.bookId = bookId;
		this.themes = themes;
	}
}
