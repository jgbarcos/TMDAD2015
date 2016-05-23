package es.unizar.tmdad.analyzer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.unizar.tmdad.analyzer.services.coordinator.AnalysisCoordinator;
import es.unizar.tmdad.model.BookResult;

@Component
public class AnalysisResourceManager {
	
	@Autowired
    AsyncAnalysis asyncAnalysis;
	
	@Autowired
	AnalysisCoordinator coordinator;
	
	private long countResource = 0;
	private Map<String, AnalysisResource> resources = new HashMap<String, AnalysisResource>();
	
	synchronized public long requestResourceId(){
		long val = countResource;
		countResource++;
		return val;
	}
	
	public String createAnalysis(String bookId, List<String> themes){
		long id = requestResourceId();
		String idstr = Long.toString(id);

		AnalysisResource res = new AnalysisResource(idstr, bookId, themes);
		resources.put(idstr, res);
		asyncAnalysis.startAnalysis(res);
		
		System.out.println("Returning analysis id");
		
		return idstr;
	}
	
	public BookResult getResult(String resourceId){
		AnalysisResource res = resources.get(resourceId);
		return res.result;
	}
	
}
