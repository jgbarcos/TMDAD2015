package es.unizar.tmdad.analyzer.services.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourceDAO {
	private long id = -1;
	private long bookId;
	private String userId;
	private ResourceStatus status;
	private Map<String, List<String>> tag;
	
	public ResourceDAO(){}
	
	public ResourceDAO(long id, long bookId, String userId, Map<String, List<String>> tag){
		this.id = id;
		this.bookId = bookId;
		this.userId = userId;
		this.setStatus(ResourceStatus.STANDBY);
		this.tag = tag;
	}
	
	public ResourceDAO(long bookId, String userId, List<ThemeDAO> themes){
		this.bookId = bookId;
		this.userId = userId;
		this.setStatus(ResourceStatus.STANDBY);
		
		this.tag = new HashMap<String, List<String>>();
		
		for(ThemeDAO th : themes){
			List<String> tokens = th.getTokens().stream()
					.map(String::toLowerCase)
					.collect(Collectors.toList());
			tag.put(th.getTitle(), tokens);
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
