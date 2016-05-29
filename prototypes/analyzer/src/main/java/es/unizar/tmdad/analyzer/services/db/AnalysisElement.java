package es.unizar.tmdad.analyzer.services.db;

public class AnalysisElement {
	public long bookId;
	public long chapterId;
	public String token;
	public long count;
	
	public AnalysisElement(long bookId, long chapterId, String token, long count){
		this.bookId = bookId;
		this.chapterId = chapterId;
		this.token = token;
		this.count = count;
	}
}
