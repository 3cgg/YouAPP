package com.youappcorp.project.usermanager.vo;

import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel._c.model.JModel;

public class TimeLineGroup implements JModel {

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