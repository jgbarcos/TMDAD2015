package es.unizar.tmdad.dbmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalysisResource {
	private long id = -1;
	private long bookId;
	private String username;
	private ResourceStatus status;
	private Map<String, List<String>> tag;
	
	public AnalysisResource(){}
	
	public AnalysisResource(long id, long bookId, String username, Map<String, List<String>> tag){
		this.id = id;
		this.bookId = bookId;
		this.username = username;
		this.setStatus(ResourceStatus.STANDBY);
		this.tag = tag;
	}
	
	public AnalysisResource(long bookId, String username, List<Theme> themes){
		this.bookId = bookId;
		this.username = username;
		this.setStatus(ResourceStatus.STANDBY);
		
		this.tag = new HashMap<String, List<String>>();
		
		for (Theme th : themes) {
			List<String> terms = th.getTerms().stream()
					.map(String::toLowerCase)
					.collect(Collectors.toList());
			tag.put(th.getName(), terms);
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBookId() {
		return bookId;
	}

	public void setBookId(long bookId) {
		this.bookId = bookId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ResourceStatus getStatus() {
		return status;
	}

	public void setStatus(ResourceStatus status) {
		this.status = status;
	}

	public Map<String, List<String>> getTag() {
		return tag;
	}

	public void setTag(Map<String, List<String>> tag) {
		this.tag = tag;
	}

}
