package es.unizar.tmdad.webgui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.unizar.tmdad.webgui.service.BookResult;
import es.unizar.tmdad.webgui.services.coordinator.AnalysisCoordinator;
import es.unizar.tmdad.webgui.services.themesdb.Theme;


@RestController
public class SearchController {
	
	public static AnalysisCoordinator coordinator = new AnalysisCoordinator();

	@RequestMapping(value="/themes", method={RequestMethod.GET}, params={"like"})
	public List<Map<String, String>> themeslike(@RequestParam("like") String like){
		List<Theme> themes = coordinator.getDb().likeTheme(like);
		// format example: [{value: Action}, {value: Love}]
		return themes.stream()
				 	 .map(t -> Collections.singletonMap("value", t.getTitle()))
				 	 .collect(Collectors.toList());	
	}

	@RequestMapping("/search")
	public BookResult search(@RequestParam("book") String book, @RequestParam("themes[]") List<String> themes) {
		return coordinator.performAnalysis(book, themes);
	}
}