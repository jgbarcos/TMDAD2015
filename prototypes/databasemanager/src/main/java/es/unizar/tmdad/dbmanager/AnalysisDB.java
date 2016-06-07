package es.unizar.tmdad.dbmanager;

import java.util.Map;
import java.util.Set;

import es.unizar.tmdad.dbmodel.BookAnalysis;
import es.unizar.tmdad.dbmodel.AnalysisResource;
import es.unizar.tmdad.dbmodel.Book;
import es.unizar.tmdad.dbmodel.ResourceStatus;
import es.unizar.tmdad.dbmodel.Theme;

public interface AnalysisDB {

	/*
	 * User Data
	 */
	public void createUser(String username, String password);
	public boolean validateUser(String username, String password);
	
	/*
	 * Theme Data
	 */
	public Map<Long, Theme> findAllThemeByUsername(String username);
	public Map<Long, Theme> findThemeByUsernameLikeThemeName(String username, String likeThemeName);
	
	public Theme findThemeByUsernameAndThemeId(String username, long themeId);
	public Theme findThemeByUsernameAndThemeName(String username, String themeName);
	
	public long createThemeOfUser(String username, Theme theme);
	public long updateThemeOfUser(String username, Theme theme);
	
	/*
	 * Book Data
	 */
	public void createBook(Book book);	
	public Book findBookById(long bookId);
	
	/*
	 * Analysis Data
	 */
	public void createAnalysis(long bookId, long chapterNum, String term, long count);
	
	public long getAnalysisOfTerm(long bookId, long chapterNum, String term);
	public BookAnalysis findAnalysisByBookAndTerms(long bookId, Set<String> terms);
	
	/*
	 * Resource Data
	 */
	public long createResource(AnalysisResource resource);
	public AnalysisResource findResourceById(long id);
	public void updateResourceStatusById(long id, ResourceStatus status);
	public ResourceStatus findResourceStatusById(long id);

}