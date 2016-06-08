package es.unizar.tmdad.analyzer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.unizar.tmdad.analyzer.services.coordinator.AnalysisCoordinator;
import es.unizar.tmdad.model.BookResult;
import es.unizar.tmdad.analyzer.services.db.model.AnalysisResource;
import es.unizar.tmdad.analyzer.services.db.model.ResourceStatus;
import es.unizar.tmdad.analyzer.services.db.model.Theme;

@Component
public class AnalysisResourceManager {
	
	@Autowired
	AnalysisCoordinator coordinator;
	
	public long createAnalysis(String userId, long bookId, List<String> themeNames){	
		
		List<Theme> themes = new ArrayList<Theme>();
		for(String th : themeNames){
			themes.add(coordinator.getDb().findThemeByUsernameAndThemeName(userId, th));
		}
		
		AnalysisResource resource = new AnalysisResource(bookId, userId, themes);
		long id = coordinator.getDb().createResource(resource);
		
		coordinator.runAnalysis(resource);
		
		return id;
	}
	
	public BookResult getResult(long resourceId){
		ResourceStatus status = coordinator.getDb().findResourceStatusById(resourceId);
		if(status != ResourceStatus.FINISHED){
			return new BookResult(resourceId, status);
		}
		
		return coordinator.recoverResult(resourceId);
	}
	
}
