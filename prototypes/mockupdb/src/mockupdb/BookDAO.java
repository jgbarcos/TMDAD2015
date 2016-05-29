package mockupdb;

import java.util.List;

public class BookDAO {
	private long id;
	private String title;
	private List<String> chapters;
	
	public BookDAO (){};
	
	public BookDAO(long id, String title, List<String> chapters){
		this.id = id;
		this.setTitle(title);
		this.chapters = chapters;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getChapters() {
		return chapters;
	}

	public void setChapters(List<String> chapters) {
		this.chapters = chapters;
	}

}
