package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.ManagedBean;

@ManagedBean
public class Comment extends PublishedContent implements Serializable {
	private static final long serialVersionUID = -4246896220795069000L;
	
	private String text;
	private String creator;
	
	public Comment (String text, String creator) {
		this.text = text;
		this.creator = creator;
		this.date = Calendar.getInstance();		
		this.date.setTime(new Date());
	}
 	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
}
