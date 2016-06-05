package mockupdb;

import java.util.List;
import java.util.Map;

import mockupdb.BookAnalysisDAO;
import mockupdb.BookDAO;
import mockupdb.ThemeDAO;

public interface AnalysisDB {

	/*
	 * User Data
	 */
	public void createUser (String username, String password);
	
	public Map<Long, ThemeDAO> findAllThemeByUsername(String username);
	public List<ThemeDAO> findThemeByUsernameLikeTitle(String username, String like);
	
	public ThemeDAO findThemeByUsernameAndId(String username, long themeId);
	ThemeDAO findThemeByUsernameAndTitle(String username, String themeTitle);
	
	public long createThemeOfUser(String username, ThemeDAO theme);
	public long updateThemeOfUser(String username, ThemeDAO theme);
	
	
	/*
	 * Book Data
	 */
	public void createBook(BookDAO book);
	
	public BookDAO findBookById(long bookId);
	
	/*
	 * Analysis Data
	 */
	public void createAnalysis(long bookId, long chapterNum, String token, long count);
	
	public long getAnalysisOfToken(long bookId, long chapterNum, String token);
	public BookAnalysisDAO findAnalysisByBookAndTokens(long bookId, List<String> tokens);
	
	/*
	 * Resource Data
	 */
	public long createResource(ResourceDAO resource);
	public ResourceDAO findResourceById(long id);
	public void updateResourceStatusById(long id, ResourceStatus status);
	public ResourceStatus findResourceStatusById(long id);

}