package domain;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.ManagedBean;

@ManagedBean
public class Comment extends PublishedContent {
	
	private String text;
	private User creator;
	
	public Comment() {
		date = Calendar.getInstance();		
		date.setTime(new Date());
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
}
