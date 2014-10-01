package domain;

import java.net.URL;
import java.util.Date;

public class Post {
	
	private String title;
	private URL link;
	private int vote;
	private Date date;
	
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
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public boolean checkEntries() throws PostException {
		if (title == null || title.equals(""))
			throw new PostException();
		if (link == null)
			throw new PostException();
		return true;
	}
}
