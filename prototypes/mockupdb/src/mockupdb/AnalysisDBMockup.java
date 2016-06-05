package mockupdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mockupdb.BookAnalysisDAO;
import mockupdb.BookDAO;
import mockupdb.ChapterAnalysisDAO;
import mockupdb.ThemeDAO;

public class AnalysisDBMockup implements AnalysisDB {
	private Map<String, UserElement> userData = new HashMap<String, UserElement>();
	private List<AnalysisElement> analysisData = new ArrayList<AnalysisElement>();
	private Map<Long, BookDAO> bookData = new HashMap<Long, BookDAO>();
	private Map<Long, ResourceDAO> resourceData = new HashMap<Long, ResourceDAO>();
	
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
	public Map<Long, ThemeDAO> findAllThemeByUsername(String username){
		UserElement user = userData.get(username);
		if(user == null){
			return null;
		}
		
		return user.themes;
	}
	
	@Override
	public ThemeDAO findThemeByUsernameAndId(String username, long themeId){
		UserElement user = userData.get(username);
		if(user == null){
			return null;
		}
		
		return user.themes.get(themeId);
	}
	
	@Override
	public ThemeDAO findThemeByUsernameAndTitle(String username, String themeTitle){
		UserElement user = userData.get(username);
		
		if(user == null){
			return null;
		}
		
		return user.themes.values().stream()
				.filter(t -> t.getTitle().toLowerCase().equals(themeTitle.toLowerCase()))
				.findFirst().orElse(null);
				
	}
	

	@Override
	public List<ThemeDAO> findThemeByUsernameLikeTitle(String username, String like) {
		return userData.get(username).themes.values().stream()
				.filter(t -> t.getTitle().toLowerCase().contains(like.toLowerCase()))
				.collect(Collectors.toList());	
	}
	
	@Override
	public long createThemeOfUser(String username, ThemeDAO theme) {
		long id = themeCount; themeCount++;
		
		theme.setId(id);
		userData.get(username).themes.put(id,  theme);
		
		return id;
	}

	@Override
	public long updateThemeOfUser(String username, ThemeDAO theme) {
		ThemeDAO resourceTheme = findThemeByUsernameAndId(username, theme.getId());
		
		if(resourceTheme == null){
			return -1;
		}
		
		userData.get(username).themes.put(theme.getId(), theme);	
		return theme.getId();
	}
	
	@Override
	public void createBook(BookDAO book){
		bookData.put(book.getId(), book);
	}

	@Override
	public BookDAO findBookById(long bookId){
		return bookData.get(bookId);
	}
	
	@Override
	public void createAnalysis(long bookId, long chapterNum, String token, long count){
		analysisData.add(new AnalysisElement(bookId, chapterNum, token, count));
	}
	
	@Override
	public long getAnalysisOfToken(long bookId, long chapterNum, String token) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BookAnalysisDAO findAnalysisByBookAndTokens(long bookId, List<String> tokens){
		// filter by book and tokens
		List<AnalysisElement> anElem = analysisData.stream()
			.filter(a -> a.bookId == bookId && tokens.contains(a.token))
			.collect(Collectors.toList());
		
		// get book and chapters info
		BookDAO book = findBookById(bookId);
		
		// create analysis DAO
		BookAnalysisDAO analysis = new BookAnalysisDAO(bookId, book.getTitle());
		
		// create each chapter of the analysis DAO
		for(int i = 0; i<book.getChapters().size(); i++){
			// Create analysis object
			long chapterNum = i;
			String title = book.getChapters().get(i);
			ChapterAnalysisDAO ch = new ChapterAnalysisDAO(chapterNum, title);
			
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
	public long createResource(ResourceDAO resource) {
		long id = resourceCount; resourceCount++;
		
		resource.setId(id);		
		resourceData.put(id, resource);		
		
		return id;
	}

	@Override
	public ResourceDAO findResourceById(long id) {
		return resourceData.get(id);
	}
	
	@Override
	public void updateResourceStatusById(long id, ResourceStatus status){
		resourceData.get(id).setStatus(status);
	}

	@Override
	public ResourceStatus findResourceStatusById(long id) {
		ResourceDAO res = resourceData.get(id);
		if(res == null){
			return ResourceStatus.NOT_FOUND;
		}
		
		return res.getStatus();
	}
	
}
