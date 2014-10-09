package domain;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Post extends PublishedContent {
	
	private String title;
	private URL link;
	private List<Comment> comments;
	
	public Post() {
		comments = Collections.synchronizedList(new ArrayList<Comment>());
		date = Calendar.getInstance();		
		date.setTime(new Date());
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public URL getLink() {
		return link;
	}
	public void setLink(URL link) {
		this.link = link;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	public int getCommentsCount() {
		return comments.size();
	}

	public boolean checkEntries() throws PostException {
		if (title == null || title.equals(""))
			throw new PostException();
		if (link == null)
			throw new PostException();
		return true;
	}
}
