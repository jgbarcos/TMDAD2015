package es.unizar.tmdad.webgui.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unizar.tmdad.model.Theme;
import es.unizar.tmdad.webgui.services.RestProxy;


@RestController
public class ProxyController {
	
	String url = "http://52.37.164.135:8080/";
	
	
	@RequestMapping(value="users/{user_id}/analysis", method=RequestMethod.POST)
	public ResponseEntity<?> createAnalysis(
			@PathVariable(value="user_id") String userId,
			@RequestParam("book") String book, 
			@RequestParam("themes[]") List<String> themeNames)
	{
		return RestProxy.createPost(url + "users/"+userId+"/analysis")
			.addParam("book",  book)
			.addParam("themes[]", themeNames)
			.expectsResponseEntity();
	}
	
	@RequestMapping(value="users/{user_id}/analysis/{id}", method=RequestMethod.GET)
	public String getAnalysis(
			@PathVariable(value="user_id") String userId,
			@PathVariable(value="id") long id)
	{
		return RestProxy.createGet(url + "users/"+userId+"/analysis/"+id)
				.expectsString();
	}
	
	/*
	 * Theme Mappings
	 */
	@RequestMapping(value="users/{user_id}/themes/{theme_id}", method=RequestMethod.GET)
	public ResponseEntity<Theme> getTheme(
			@PathVariable(value="user_id") String userId,
			@PathVariable(value="theme_id") long themeId)
	{		
		return RestProxy.createGet(url + "users/"+userId+"/themes/"+themeId)
				.expectsTheme();
	}
	
	@RequestMapping(value="users/{user_id}/themes/{theme_id}", method=RequestMethod.PUT)
	public ResponseEntity<Theme> updateTheme(
			@PathVariable(value="user_id") String userId,
      		@PathVariable(value="theme_id") long themeId,
	        @RequestBody Theme theme)
	{
		return RestProxy.createPut(url + "users/"+userId+"/themes/"+themeId)
				.setBody(theme)
				.expectsTheme();
	}
	
	@RequestMapping(value="users/{user_id}/themes", method=RequestMethod.POST)
	public ResponseEntity<Theme> createTheme(
			@PathVariable(value="user_id") String userId,
			@RequestBody Theme theme)
	{
		return RestProxy.createPost(url + "users/"+userId+"/themes/")
				.setBody(theme)
				.expectsTheme();
	}
	
	
	@RequestMapping(value="users/{user_id}/themes", method={RequestMethod.GET}, params={"like"})
	public List<Theme> getThemesLike(
			@PathVariable(value="user_id") String userId,
			@RequestParam("like") String like)
	{
		return RestProxy.createGet(url + "users/"+userId+"/themes")
				.addParam("like",  like)
				.expectsThemeList();
	}
	
}