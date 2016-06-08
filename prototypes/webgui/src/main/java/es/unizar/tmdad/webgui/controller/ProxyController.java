package es.unizar.tmdad.webgui.controller;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.unizar.tmdad.model.ThemeDAO;
import es.unizar.tmdad.webgui.services.RestProxy;


@RestController
public class ProxyController {
	
	String url = "http://localhost:8081/";
	
	
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
	public ResponseEntity<ThemeDAO> getTheme(
			@PathVariable(value="user_id") String userId,
			@PathVariable(value="theme_id") long themeId)
	{		
		return RestProxy.createGet(url + "users/"+userId+"/themes/"+themeId)
				.expectsTheme();
	}
	
	@RequestMapping(value="users/{user_id}/themes/{theme_id}", method=RequestMethod.PUT)
	public ResponseEntity<ThemeDAO> updateTheme(
			@PathVariable(value="user_id") String userId,
      		@PathVariable(value="theme_id") long themeId,
	        @RequestBody ThemeDAO theme)
	{
		return RestProxy.createPut(url + "users/"+userId+"/themes/"+themeId)
				.setBody(theme)
				.expectsTheme();
	}
	
	@RequestMapping(value="users/{user_id}/themes", method=RequestMethod.POST)
	public ResponseEntity<ThemeDAO> createTheme(
			@PathVariable(value="user_id") String userId,
			@RequestBody ThemeDAO theme)
	{
		return RestProxy.createPost(url + "users/"+userId+"/themes/")
				.setBody(theme)
				.expectsTheme();
	}
	
	
	@RequestMapping(value="users/{user_id}/themes", method={RequestMethod.GET}, params={"like"})
	public List<ThemeDAO> getThemesLike(
			@PathVariable(value="user_id") String userId,
			@RequestParam("like") String like)
	{
		return RestProxy.createGet(url + "users/"+userId+"/themes")
				.addParam("like",  like)
				.expectsThemeList();
	}
	
}