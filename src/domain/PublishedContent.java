package domain;

import java.util.Calendar;
import java.util.Date;

public abstract class PublishedContent {

	protected int vote;
	protected Calendar date;
	
	public int getVote() {
		return vote;
	}
	public void incrementVote() {
		vote++;
	}
	public void decrementVote() {
		vote--;
	}
	public String getTimeDifference() {
		Calendar current = Calendar.getInstance();
		current.setTime(new Date());

		if (current.get(Calendar.YEAR) > date.get(Calendar.YEAR))
			return date.get(Calendar.YEAR) + " years ago";
		if (current.get(Calendar.MONTH) > date.get(Calendar.MONTH))
			return date.get(Calendar.MONTH) + " months ago";
		if (current.get(Calendar.WEEK_OF_MONTH) > date.get(Calendar.WEEK_OF_MONTH))
			return date.get(Calendar.WEEK_OF_MONTH) + " weeks ago";
		if (current.get(Calendar.DAY_OF_WEEK) > date.get(Calendar.DAY_OF_WEEK))
			return date.get(Calendar.DAY_OF_WEEK) + " days ago";
		if (current.get(Calendar.HOUR) > date.get(Calendar.HOUR))
			return date.get(Calendar.HOUR) + " hours ago";
		if (current.get(Calendar.MINUTE) > date.get(Calendar.MINUTE))
			return date.get(Calendar.MINUTE) + " minutes ago";
		if (current.get(Calendar.SECOND) > date.get(Calendar.SECOND))
			return date.get(Calendar.SECOND) + " seconds ago";
		
		return "just now";
	}

}
