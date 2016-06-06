package es.unizar.tmdad.dbmanager;

import java.util.List;
import java.util.Map;

import es.unizar.tmdad.dbconnecton.ThemeDAO;
import es.unizar.tmdad.dbconnecton.UserDAO;
import es.unizar.tmdad.dbmodel.Analysis;
import es.unizar.tmdad.dbmodel.AnalysisResource;
import es.unizar.tmdad.dbmodel.Book;
import es.unizar.tmdad.dbmodel.ResourceStatus;
import es.unizar.tmdad.dbmodel.Theme;
import es.unizar.tmdad.dbmodel.User;

public class AnalysisDBImplementation implements AnalysisDB {

	@Override
	public void createUser(String username, String password) {
		User u = new User(username, password);
		UserDAO dao = new UserDAO();
		dao.insertUser(u);	
	}
	
	@Override
	public boolean validateUser(String username, String password) {
		User u = new User(username, password);
		UserDAO dao = new UserDAO();
		return dao.validateUser(u);
	}

	@Override
	public Map<Long, Theme> findAllThemeByUsername(String username) {
		ThemeDAO dao = new ThemeDAO();
		return dao.findAllThemeByUsername(username);
	}

	@Override
	public List<Theme> findThemeByUsernameLikeTitle(String username, String like) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Theme findThemeByUsernameAndThemeId(String username, long themeId) {
		ThemeDAO dao = new ThemeDAO();
		return dao.findThemeByUsernameAndThemeId(username, themeId);
	}

	@Override
	public Theme findThemeByUsernameAndThemeName(String username, String themeTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long createThemeOfUser(String username, Theme theme) {
		ThemeDAO dao = new ThemeDAO();
		return dao.insertTheme(theme, username);
	}

	@Override
	public long updateThemeOfUser(String username, Theme theme) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void createBook(Book book) {
		
	}

	@Override
	public Book findBookById(long bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createAnalysis(long bookId, long chapterNum, String token, long count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getAnalysisOfToken(long bookId, long chapterNum, String token) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Analysis findAnalysisByBookAndTokens(long bookId, List<String> tokens) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long createResource(AnalysisResource resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AnalysisResource findResourceById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateResourceStatusById(long id, ResourceStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResourceStatus findResourceStatusById(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
