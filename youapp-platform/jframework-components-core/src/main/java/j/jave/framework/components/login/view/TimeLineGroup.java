package j.jave.framework.components.login.view;

import java.util.ArrayList;
import java.util.List;

public class TimeLineGroup {

	private String date;
	
	private List<TimelineView> timelineViews=new ArrayList<TimelineView>();

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<TimelineView> getTimelineViews() {
		return timelineViews;
	}

	public void setTimelineViews(List<TimelineView> timelineViews) {
		this.timelineViews = timelineViews;
	}

	
	
	
	
}
