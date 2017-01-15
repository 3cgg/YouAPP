package me.bunny.kernel.eventdriven.servicehub.monitor;

import java.util.Date;

import me.bunny.kernel.eventdriven.servicehub.JQueueElement;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.model.JModel;

public class JEventProcessingStatus implements JModel,JQueueElement {

	private Date startTime;
	
	private Date endTime;
	
	/**
	 * the event identification.
	 */
	private String unique;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	@Override
	public String desc() {
		return JJSON.get().formatObject(this);
	}
	
}
