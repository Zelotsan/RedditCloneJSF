package domain;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import domain.exception.PostException;

public class Post extends PublishedContent implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private URL link;
	private String username;
	private List<Comment> comments;
	private int votes;
	private Calendar date;

	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void addComment(Comment comment) {
		comments.add(comment);
	}
	public int getCommentCount() {
		return comments.size();
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}

	public boolean checkEntries(String title, String link) throws PostException {
		if (title == null || title.equals(""))
			throw new PostException("Please enter a describing title for your link");
		if (link == null)
			throw new PostException("Please enter a link to post");
		return true;
	}
	
	
	public void vote(int i) {
		votes += i;
	}
	
	public String getTimeDifference() {
		Calendar current = Calendar.getInstance();
		current.setTime(new Date());

		if (current.get(Calendar.YEAR) > date.get(Calendar.YEAR))
			return (current.get(Calendar.YEAR) - date.get(Calendar.YEAR)) + " years ago";
		if (current.get(Calendar.MONTH) > date.get(Calendar.MONTH))
			return (current.get(Calendar.MONTH) - date.get(Calendar.MONTH)) + " months ago";
		if (current.get(Calendar.WEEK_OF_MONTH) > date.get(Calendar.WEEK_OF_MONTH))
			return (current.get(Calendar.WEEK_OF_MONTH) - date.get(Calendar.WEEK_OF_MONTH)) + " weeks ago";
		if (current.get(Calendar.DAY_OF_WEEK) > date.get(Calendar.DAY_OF_WEEK))
			return (current.get(Calendar.DAY_OF_WEEK) - date.get(Calendar.DAY_OF_WEEK)) + " days ago";
		if (current.get(Calendar.HOUR) > date.get(Calendar.HOUR))
			return (current.get(Calendar.HOUR) - date.get(Calendar.HOUR)) + " hours ago";
		if (current.get(Calendar.MINUTE) > date.get(Calendar.MINUTE))
			return (current.get(Calendar.MINUTE) - date.get(Calendar.MINUTE)) + " minutes ago";
		if (current.get(Calendar.SECOND) > date.get(Calendar.SECOND))
			return (current.get(Calendar.SECOND) - date.get(Calendar.SECOND)) + " seconds ago";
		
		return "just now";
	}
}
