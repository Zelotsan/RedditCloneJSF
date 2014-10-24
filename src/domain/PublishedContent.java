package domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public abstract class PublishedContent implements Serializable {
	private static final long serialVersionUID = 948111657344209682L;
	
	protected static Feed contentChangedListener;
	
	protected int votes;
	protected Calendar date;
	
	public int getVotes() {
		return votes;
	}
	public void vote(int i) {
		votes += i;
		contentChangedListener.contentChanged();
	}

	public static void setContentChangedListener(Feed contentChangedListener) {
		PublishedContent.contentChangedListener = contentChangedListener;
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
