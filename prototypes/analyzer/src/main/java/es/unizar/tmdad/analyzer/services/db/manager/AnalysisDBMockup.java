package es.unizar.tmdad.analyzer.services.db.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.unizar.tmdad.analyzer.services.db.AnalysisElement;
import es.unizar.tmdad.analyzer.services.db.UserElement;
import es.unizar.tmdad.analyzer.services.db.model.AnalysisResource;
import es.unizar.tmdad.analyzer.services.db.model.Book;
import es.unizar.tmdad.analyzer.services.db.model.BookAnalysis;
import es.unizar.tmdad.analyzer.services.db.model.ChapterAnalysis;
import es.unizar.tmdad.analyzer.services.db.model.ResourceStatus;
import es.unizar.tmdad.analyzer.services.db.model.Theme;

public class AnalysisDBMockup implements AnalysisDB {
	private Map<String, UserElement> userData = new HashMap<String, UserElement>();
	private List<AnalysisElement> analysisData = new ArrayList<AnalysisElement>();
	private Map<Long, Book> bookData = new HashMap<Long, Book>();
	private Map<Long, AnalysisResource> resourceData = new HashMap<Long, AnalysisResource>();
	
	private long themeCount = 0;
	private long resourceCount = 0;
	
	public AnalysisDBMockup(){
		PredefinedData.fillDatabase(this);
	}

	@Override
	public void createUser(String username, String password) {
		userData.put(username, new UserElement(username, password));
	}
	
	@Override
	public boolean validateUser(String username, String password) {
		return true;
	}

	@Override
	public Map<Long, Theme> findAllThemeByUsername(String username){
		UserElement user = userData.get(username);
		if(user == null){
			return null;
		}
		
		return user.themes;
	}
	
	@Override
	public Theme findThemeByUsernameAndThemeId(String username, long themeId){
		UserElement user = userData.get(username);
		if(user == null){
			return null;
		}
		
		return user.themes.get(themeId);
	}
	
	@Override
	public Theme findThemeByUsernameAndThemeName(String username, String themeName){
		UserElement user = userData.get(username);
		
		if(user == null){
			return null;
		}
		
		return user.themes.values().stream()
				.filter(t -> t.getName().toLowerCase().equals(themeName.toLowerCase()))
				.findFirst().orElse(null);
				
	}
	

	@Override
	public List<Theme> findThemeByUsernameLikeThemeName(String username, String like) {
		return userData.get(username).themes.values().stream()
				.filter(t -> t.getName().toLowerCase().contains(like.toLowerCase()))
				.collect(Collectors.toList());	
	}
	
	@Override
	public long createThemeOfUser(String username, Theme theme) {
		long id = themeCount; themeCount++;
		
		theme.setId(id);
		userData.get(username).themes.put(id,  theme);
		
		return id;
	}

	@Override
	public long updateThemeOfUser(String username, Theme theme) {
		Theme resourceTheme = findThemeByUsernameAndThemeId(username, theme.getId());
		
		if(resourceTheme == null){
			return -1;
		}
		
		userData.get(username).themes.put(theme.getId(), theme);	
		return theme.getId();
	}
	
	@Override
	public void createBook(Book book){
		bookData.put(book.getId(), book);
	}

	@Override
	public Book findBookById(long bookId){
		return bookData.get(bookId);
	}
	
	@Override
	public void createAnalysis(long bookId, long chapterNum, String term, long count){
		analysisData.add(new AnalysisElement(bookId, chapterNum, term, count));
	}
	
	@Override
	public long getAnalysisOfTerm(long bookId, long chapterNum, String term) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BookAnalysis findAnalysisByBookAndTerms(long bookId, List<String> terms){
		// filter by book and tokens
		List<AnalysisElement> anElem = analysisData.stream()
			.filter(a -> a.bookId == bookId && terms.contains(a.token))
			.collect(Collectors.toList());
		
		// get book and chapters info
		Book book = findBookById(bookId);
		
		// create analysis DAO
		BookAnalysis analysis = new BookAnalysis(bookId, book.getTitle(), new ArrayList<>());
		
		// create each chapter of the analysis DAO
		for(int i = 0; i<book.getChapters().size(); i++){
			// Create analysis object
			long chapterNum = i;
			String title = book.getChapters().get(i);
			ChapterAnalysis ch = new ChapterAnalysis(chapterNum, title, null);
			
			// Get chapter i analysis elements
			List<AnalysisElement> filtered = anElem.stream()
					.filter(a -> a.chapterId == chapterNum)
					.collect(Collectors.toList());
			for(AnalysisElement a : filtered){
				ch.getCounts().put(a.token, a.count);
			}
			
			// Add chapter to analysis
			analysis.getChapters().add(ch);			
		}
		
		return analysis;
	}

	@Override
	public long createResource(AnalysisResource resource) {
		long id = resourceCount; resourceCount++;
		
		resource.setId(id);		
		resourceData.put(id, resource);		
		
		return id;
	}

	@Override
	public AnalysisResource findResourceById(long id) {
		return resourceData.get(id);
	}
	
	@Override
	public void updateResourceStatusById(long id, ResourceStatus status){
		resourceData.get(id).setStatus(status);
	}

	@Override
	public ResourceStatus findResourceStatusById(long id) {
		AnalysisResource res = resourceData.get(id);
		if(res == null){
			return ResourceStatus.NOT_FOUND;
		}
		
		return res.getStatus();
	}
	
}
