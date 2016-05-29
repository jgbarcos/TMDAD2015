package es.unizar.tmdad.analyzer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.unizar.tmdad.analyzer.services.coordinator.AnalysisCoordinator;
import es.unizar.tmdad.model.BookResult;
import es.unizar.tmdad.analyzer.services.db.ResourceDAO;
import es.unizar.tmdad.analyzer.services.db.ResourceStatus;
import es.unizar.tmdad.analyzer.services.db.ThemeDAO;

@Component
public class AnalysisResourceManager {
	
	@Autowired
	AnalysisCoordinator coordinator;
	
	public long createAnalysis(String userId, long bookId, List<String> themes){	
		
		List<ThemeDAO> themesDAO = new ArrayList<ThemeDAO>();
		for(String th : themes){
			themesDAO.add(coordinator.getDb().findThemeByUsernameAndTitle(userId, th));
		}
		
		ResourceDAO resource = new ResourceDAO(bookId, userId, themesDAO);
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
