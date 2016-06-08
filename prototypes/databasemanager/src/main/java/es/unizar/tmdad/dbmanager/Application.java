package es.unizar.tmdad.dbmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.unizar.tmdad.dbmodel.AnalysisResource;
import es.unizar.tmdad.dbmodel.Book;
import es.unizar.tmdad.dbmodel.BookAnalysis;
import es.unizar.tmdad.dbmodel.ResourceStatus;
import es.unizar.tmdad.dbmodel.Theme;

public class Application {

    public static void main(String[] args) {
    	findResourceStatusByIdTest();
    }
    
    private static void createUserTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	analyzer.createUser("user", "password");
    	System.out.print("createUserTest FINISHED");
    }
    
    private static void validateUserTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	boolean validation = analyzer.validateUser("user", "password");
    	System.out.println(validation ? "Combination exists: validation CORRECT" : "Combination not exists: validation ERROR");
    	validation = analyzer.validateUser("user", "incorrectpassword");
    	System.out.println(validation ? "Combination exists: validation ERROR" : "Combination not exists: validation CORRECT");
    	System.out.print("validateUserTest FINISHED");
    }
    
    private static void findThemeByUsernameLikeThemeNameTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Map<Long,Theme> themes = analyzer.findThemeByUsernameLikeThemeName("user", "na");
    	for (Iterator<Long> iterator = themes.keySet().iterator(); iterator.hasNext();) {
    		Long themeId = (Long) iterator.next();
			Theme theme = themes.get(themeId);
			System.out.println(theme);
		}
    	System.out.print("findThemeByUsernameLikeThemeNameTest FINISHED");
    }
    
    private static void findAllThemeByUsernameTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Map<Long,Theme> themes = analyzer.findAllThemeByUsername("user");
    	for (Iterator<Long> iterator = themes.keySet().iterator(); iterator.hasNext();) {
    		Long themeId = (Long) iterator.next();
			Theme theme = themes.get(themeId);
			System.out.println(theme);
		}
    	System.out.print("findAllThemeByUsernameTest FINISHED");
    }
    
    private static void findThemeByUsernameAndThemeIdTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Theme theme = analyzer.findThemeByUsernameAndThemeId("user", 5);
    	System.out.println(theme);
    	System.out.print("findThemeByUsernameAndThemeIdTest FINISHED");
    }
    
    private static void findThemeByUsernameAndThemeNameTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Theme theme = analyzer.findThemeByUsernameAndThemeName("user", "war");
    	System.out.println(theme);
    	System.out.print("findThemeByUsernameAndThemeNameTest FINISHED");
    }
    
    private static void createThemeOfUserTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Set<String> terms = new HashSet<String>();
    	/*terms.add("animal");
    	terms.add("plant");
    	terms.add("flower");
    	terms.add("lion");
    	terms.add("forest");
    	Theme theme = new Theme(-1, "nature", terms);*/
    	terms.add("sword");
    	terms.add("war");
    	terms.add("king");
    	terms.add("empire");
    	terms.add("castle");
    	Theme theme = new Theme(-1, "war", terms);
    	long themeId = analyzer.createThemeOfUser("user", theme);
    	System.out.println("themeId=" + themeId);
    	System.out.print("createThemeOfUserTest FINISHED");
    }
    
    private static void updateThemeOfUserTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Set<String> terms = new HashSet<>();
    	terms.add("sword");
    	terms.add("war");
    	terms.add("king");
    	terms.add("castle");
    	terms.add("queen");
    	terms.add("gun");
    	terms.add("arrow");
    	Theme theme = new Theme(6, "war", terms);
    	long themeId = analyzer.updateThemeOfUser("user", theme);
    	System.out.println("themeId=" + themeId);
    	System.out.print("updateThemeOfUserTest FINISHED");
    }
    
    private static void createBookTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	List<String> chapters = new ArrayList<>();
    	chapters.add("TLK chapter 1");
    	chapters.add("TLK chapter 2");
    	chapters.add("TLK chapter 3");
    	chapters.add("TLK chapter 4");
    	Book book = new Book(354, "The lion King", chapters);
    	analyzer.createBook(book);
    	System.out.print("createBookTest FINISHED");
    }
    
    private static void findBookByIdTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Book book = analyzer.findBookById(354);
    	System.out.println(book);
    	System.out.print("findBookByIdTest FINISHED");
    }
    
    private static void createAnalysisTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	analyzer.createAnalysis(354, 2, "lion", 23);
    	System.out.print("createAnalysisTest FINISHED");
    }
    
    private static void getAnalysisOfTermTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	long count = analyzer.getAnalysisOfTerm(354, 1, "king");
    	System.out.println("count=" + count);
    	System.out.print("getAnalysisOfTermTest FINISHED");
    }
    
    private static void findAnalysisByBookAndTermsTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	List<String> terms = new ArrayList<>();
    	terms.add("sword");
    	terms.add("war");
    	terms.add("king");
    	terms.add("lion");
    	terms.add("empire");
    	terms.add("castle");
    	BookAnalysis analysis = analyzer.findAnalysisByBookAndTerms(354, terms);
    	System.out.println(analysis);
    	System.out.print("findAnalysisByBookAndTermsTest FINISHED");
    }
    
    private static void createResourceTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	Map<String, List<String>> tag = new HashMap<>();
    	List<String> terms = new ArrayList<>();
    	terms.add("sword");
    	terms.add("war");
    	terms.add("king");
    	terms.add("empire");
    	terms.add("castle");
    	tag.put("war", terms);
    	terms = new ArrayList<>();
    	terms.add("lion");
    	terms.add("animal");
    	terms.add("forest");
    	terms.add("water");
    	terms.add("wild");
    	tag.put("nature", terms);
    	AnalysisResource resource = new AnalysisResource(15, 354, "user", tag);
    	long resourceId = analyzer.createResource(resource);
    	System.out.println("resourceId=" + resourceId);
    	System.out.print("createResourceTest FINISHED");
    }
    
    private static void findResourceByIdTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	AnalysisResource resource = analyzer.findResourceById(2);
    	System.out.println(resource);
    	System.out.print("findResourceByIdTest FINISHED");
    }
    
    private static void updateResourceStatusByIdTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	analyzer.updateResourceStatusById(2, ResourceStatus.TOKENIZER);
    	System.out.print("updateResourceStatusByIdTest FINISHED");
    }
    
    private static void findResourceStatusByIdTest() {
    	AnalysisDB analyzer = new AnalysisDBImplementation();
    	ResourceStatus status = analyzer.findResourceStatusById(2);
    	System.out.println(status.name());
    	System.out.print("findResourceStatusByIdTest FINISHED");
    }
    
}
