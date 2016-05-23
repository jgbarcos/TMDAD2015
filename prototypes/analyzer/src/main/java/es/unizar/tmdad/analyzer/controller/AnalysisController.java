package es.unizar.tmdad.analyzer.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.unizar.tmdad.analyzer.service.AnalysisResourceManager;
import es.unizar.tmdad.analyzer.services.coordinator.AnalysisCoordinator;
import es.unizar.tmdad.analyzer.services.themesdb.Theme;
import es.unizar.tmdad.model.BookResult;


@RestController
public class AnalysisController {
	
	@Autowired
	public AnalysisCoordinator coordinator;

	@Autowired
	public AnalysisResourceManager resourceManager;

	@RequestMapping(value="/themes", method={RequestMethod.GET}, params={"like"})
	public List<Map<String, String>> themeslike(@RequestParam("like") String like){
		List<Theme> themes = coordinator.getDb().likeTheme(like);
		// format example: [{value: Action}, {value: Love}]
		return themes.stream()
				 	 .map(t -> Collections.singletonMap("value", t.getTitle()))
				 	 .collect(Collectors.toList());	
	}

	@RequestMapping(value="/analyze", method={RequestMethod.GET})
	public BookResult search(@RequestParam("book") String book, @RequestParam("themes[]") List<String> themes) {
		String id = resourceManager.createAnalysis(book, themes);
		return resourceManager.getResult(id);
	}
	
	@RequestMapping(value="/analysis", method=RequestMethod.POST)
	public ResponseEntity<?> createAnalysis(@RequestParam("book") String book, @RequestParam("themes[]") List<String> themes) {
		String id = resourceManager.createAnalysis(book, themes);
		
		final URI location = ServletUriComponentsBuilder
            .fromCurrentServletMapping().path("/analysis/{id}").build()
            .expand(id).toUri();

		final HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		final ResponseEntity<Void> entity = new ResponseEntity<Void>(headers,
			HttpStatus.CREATED);
		return entity;
	}
	
	@RequestMapping(value="/analysis/{id}", method=RequestMethod.GET)
	public BookResult getAnalysis(@PathVariable(value="id") String id){
		return resourceManager.getResult(id);
	}
}