package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JQueueElement;
import j.jave.kernal.jave.model.JModel;

import java.util.Date;

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
	
}
