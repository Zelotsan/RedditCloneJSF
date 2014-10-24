package domain;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import domain.exception.PostException;

public class Post extends PublishedContent implements Serializable {
	private static final long serialVersionUID = -4181480191577090382L;
	
	private String title;
	private URL link;
	private String username;
	private List<Comment> comments;
	
	private transient boolean commentsShown = false;
	private transient String comment;
	
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
		return Collections.unmodifiableList(comments);
	}
	public void addComment() throws PostException {
		String creator = UserManager.globalUsername;
		if (checkEntries(this.comment, creator)) {
			Comment comment = new Comment(this.comment, UserManager.globalUsername);
			comments.add(comment);
			contentChangedListener.contentChanged();
		}
	}
	public boolean hasComments() {
		return comments.size() != 0;
	}
	public int getCommentCount() {
		return comments.size();
	}
	public boolean getCommentsShown() {
		return commentsShown;
	}
	public void setCommentsShown(boolean commentsShown) {
		this.commentsShown = commentsShown;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public void toggleCommentsShown() {
		commentsShown = !commentsShown;
	}

	public boolean checkEntries(String comment, String creator) {
		if (comment == null || comment.equals(""))
			new PostException("Please write something").handleExcpetion();
		if (creator == null || creator.equals(""))
			new PostException("Creator could not be assigned").handleExcpetion();
		return true;
	}
	
	
}
