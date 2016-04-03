package com.youappcorp.project.usermanager.vo;

import j.jave.kernal.jave.model.JModel;

public class TimelineView implements JModel{
	
	/**
	 * type to decide which icon to choose 
	 * EMAIL , USER , COMMENT, VIDEO , PICTURE 
	 */
	private String type ;
	
	/**
	 * header info 
	 */
	private String header;
	
	/**
	 * content info 
	 */
	private String content;
	
	/**
	 * foot info
	 */
	private TimelineFoot foot;
	
	/**
	 * highlight 
	 */
	private String highlightContent;
	
	/**
	 * highlight 
	 */
	private String highlightPath;

	/**
	 * 距离当前日期的时间，单位：天，小时，分钟，秒
	 */
	private String timeOffset;
	
	public String getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(String timeOffset) {
		this.timeOffset = timeOffset;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TimelineFoot getFoot() {
		return foot;
	}

	public void setFoot(TimelineFoot foot) {
		this.foot = foot;
	}

	public String getHighlightContent() {
		return highlightContent;
	}

	public void setHighlightContent(String highlightContent) {
		this.highlightContent = highlightContent;
	}

	public String getHighlightPath() {
		return highlightPath;
	}

	public void setHighlightPath(String highlightPath) {
		this.highlightPath = highlightPath;
	}

}