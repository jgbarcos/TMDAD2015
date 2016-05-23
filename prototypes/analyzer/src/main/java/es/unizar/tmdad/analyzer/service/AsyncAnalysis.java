package es.unizar.tmdad.analyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.unizar.tmdad.analyzer.services.coordinator.AnalysisCoordinator;

@Service
public class AsyncAnalysis {
	
	@Autowired
	AnalysisCoordinator coordinator;
	
	public AsyncAnalysis() {
		
	}
	
	@Async
	public void startAnalysis(AnalysisResource res){
		coordinator.performAnalysis(res);
	}
}
