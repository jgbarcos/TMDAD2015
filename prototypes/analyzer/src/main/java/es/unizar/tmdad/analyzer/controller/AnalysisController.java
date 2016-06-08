package es.unizar.tmdad.analyzer.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.unizar.tmdad.analyzer.service.AnalysisResourceManager;
import es.unizar.tmdad.analyzer.services.coordinator.AnalysisCoordinator;
import es.unizar.tmdad.analyzer.services.db.ThemeDAO;
import es.unizar.tmdad.model.BookResult;


@RestController
public class AnalysisController {
	
	@Autowired
	public AnalysisCoordinator coordinator;

	@Autowired
	public AnalysisResourceManager resourceManager;
	
	/*
	 * Analysis Mappings
	 */
	@RequestMapping(value="users/{user_id}/analysis", method=RequestMethod.POST)
	public ResponseEntity<?> createAnalysis(
			@PathVariable(value="user_id") String userId,
			@RequestParam("book") long book, 
			@RequestParam("themes[]") List<String> themeNames)
	{
		
		long id = resourceManager.createAnalysis(userId, book, themeNames);
		
		final URI location = ServletUriComponentsBuilder
            .fromCurrentServletMapping().path("users/{user_id}/analysis/{id}").build()
            .expand(userId, id).toUri();

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		final ResponseEntity<Void> entity = new ResponseEntity<Void>(headers,
			HttpStatus.CREATED);
		return entity;
	}
	
	@RequestMapping(value="users/{user_id}/analysis/{id}", method=RequestMethod.GET)
	public BookResult getAnalysis(
			@PathVariable(value="user_id") String userId,
			@PathVariable(value="id") long id)
	{
		return resourceManager.getResult(id);
	}
	
	/*
	 * Theme Mappings
	 */
	@RequestMapping(value="users/{user_id}/themes/{theme_id}", method=RequestMethod.GET)
	public ResponseEntity<ThemeDAO> getTheme(
			@PathVariable(value="user_id") String userId,
			@PathVariable(value="theme_id") long themeId)
	{		
		ThemeDAO th = coordinator.getDb().findThemeByUsernameAndId(userId, themeId);
		
		if(th == null){
			return new ResponseEntity<ThemeDAO>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ThemeDAO>(th, HttpStatus.OK);
	}
	
	@RequestMapping(value="users/{user_id}/themes/{theme_id}", method=RequestMethod.PUT)
	public ResponseEntity<ThemeDAO> updateTheme(
			@PathVariable(value="user_id") String userId,
      		@PathVariable(value="theme_id") long themeId,
	        @RequestBody ThemeDAO theme)
	{
		theme.setId(themeId);
		long id = coordinator.getDb().updateThemeOfUser(userId, theme);
		
		
		if(id != themeId){
			return new ResponseEntity<ThemeDAO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ThemeDAO>(theme, HttpStatus.OK);
	}
	
	@RequestMapping(value="users/{user_id}/themes", method=RequestMethod.POST)
	public ResponseEntity<?> createTheme(
			@PathVariable(value="user_id") String userId,
			@RequestBody ThemeDAO theme)
	{
		long id = coordinator.getDb().createThemeOfUser(userId, theme);
		
		final URI location = ServletUriComponentsBuilder
	            .fromCurrentServletMapping().path("users/{user_id}/themes/{theme_id}").build()
	            .expand(userId, id).toUri();
		
		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		final ResponseEntity<Void> entity = new ResponseEntity<Void>(headers,
			HttpStatus.CREATED);
		return entity;
	}
	
	
	@RequestMapping(value="users/{user_id}/themes", method={RequestMethod.GET}, params={"like"})
	public ResponseEntity<List<ThemeDAO>> getThemesLike(
			@PathVariable(value="user_id") String userId,
			@RequestParam("like") String like)
	{
		List<ThemeDAO> mapping = coordinator.getDb().findThemeByUsernameLikeTitle(userId, like).stream()
			.collect(Collectors.toList());	
		
		final ResponseEntity<List<ThemeDAO>> entity = new ResponseEntity<List<ThemeDAO>>(mapping, HttpStatus.OK);
		return entity;
	}
}