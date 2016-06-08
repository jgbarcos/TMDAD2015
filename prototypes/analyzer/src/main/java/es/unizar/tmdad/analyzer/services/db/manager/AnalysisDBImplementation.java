package es.unizar.tmdad.analyzer.services.db.manager;

import java.util.List;
import java.util.Map;

import es.unizar.tmdad.analyzer.services.db.connecton.AnalysisDAO;
import es.unizar.tmdad.analyzer.services.db.connecton.AnalysisResourceDAO;
import es.unizar.tmdad.analyzer.services.db.connecton.BookDAO;
import es.unizar.tmdad.analyzer.services.db.connecton.ThemeDAO;
import es.unizar.tmdad.analyzer.services.db.connecton.UserDAO;
import es.unizar.tmdad.analyzer.services.db.model.AnalysisResource;
import es.unizar.tmdad.analyzer.services.db.model.Book;
import es.unizar.tmdad.analyzer.services.db.model.BookAnalysis;
import es.unizar.tmdad.analyzer.services.db.model.ResourceStatus;
import es.unizar.tmdad.analyzer.services.db.model.Theme;

public class AnalysisDBImplementation implements AnalysisDB {

	@Override
	public void createUser(String username, String password) {
		UserDAO dao = new UserDAO();
		dao.createUser(username, password);	
	}
	
	@Override
	public boolean validateUser(String username, String password) {
		UserDAO dao = new UserDAO();
		return dao.validateUser(username, password);
	}

	@Override
	public Map<Long, Theme> findAllThemeByUsername(String username) {
		ThemeDAO dao = new ThemeDAO();
		return dao.findAllThemeByUsername(username);
	}

	@Override
	public List<Theme> findThemeByUsernameLikeThemeName(String username, String likeThemeName) {
		ThemeDAO dao = new ThemeDAO();
		return dao.findThemeByUsernameLikeThemeName(username, likeThemeName);
	}

	@Override
	public Theme findThemeByUsernameAndThemeId(String username, long themeId) {
		ThemeDAO dao = new ThemeDAO();
		return dao.findThemeByUsernameAndThemeId(username, themeId);
	}

	@Override
	public Theme findThemeByUsernameAndThemeName(String username, String themeName) {
		ThemeDAO dao = new ThemeDAO();
		return dao.findThemeByUsernameAndThemeName(username, themeName);
	}

	@Override
	public long createThemeOfUser(String username, Theme theme) {
		ThemeDAO dao = new ThemeDAO();
		return dao.createThemeOfUser(username, theme);
	}

	@Override
	public long updateThemeOfUser(String username, Theme theme) {
		ThemeDAO dao = new ThemeDAO();
		return dao.updateThemeOfUser(username, theme);
	}

	@Override
	public void createBook(Book book) {
		BookDAO dao = new BookDAO();
		dao.createBook(book);
	}

	@Override
	public Book findBookById(long bookId) {
		BookDAO dao = new BookDAO();
		return dao.findBookById(bookId);
	}

	@Override
	public void createAnalysis(long bookId, long chapterNum, String term, long count) {
		AnalysisDAO dao = new AnalysisDAO();
		dao.createAnalysis(bookId, chapterNum, term, count);
	}

	@Override
	public long getAnalysisOfTerm(long bookId, long chapterNum, String term) {
		AnalysisDAO dao = new AnalysisDAO();
		return dao.getAnalysisOfTerm(bookId, chapterNum, term);
	}

	@Override
	public BookAnalysis findAnalysisByBookAndTerms(long bookId, List<String> terms) {
		AnalysisDAO dao = new AnalysisDAO();
		return dao.findAnalysisByBookAndTerms(bookId, terms);
	}

	@Override
	public long createResource(AnalysisResource resource) {
		AnalysisResourceDAO dao = new AnalysisResourceDAO();
		return dao.createResource(resource);
	}

	@Override
	public AnalysisResource findResourceById(long id) {
		AnalysisResourceDAO dao = new AnalysisResourceDAO();
		return dao.findResourceById(id);
	}

	@Override
	public void updateResourceStatusById(long id, ResourceStatus status) {
		AnalysisResourceDAO dao = new AnalysisResourceDAO();
		dao.updateResourceStatusById(id, status);
	}

	@Override
	public ResourceStatus findResourceStatusById(long id) {
		AnalysisResourceDAO dao = new AnalysisResourceDAO();
		return dao.findResourceStatusById(id);
	}
	
}
