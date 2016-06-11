/**
 * 
 */
package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JSyncMonitorWakeupListener.class)
public class JSyncMonitorWakeupEvent extends JYouAPPEvent<JSyncMonitorWakeupEvent> {
	
	private String syncMonitorUnique;
	
	private Object data;
	
	/**
	 * count attempting to wake up the other threads.
	 */
	private int count;
	
	public JSyncMonitorWakeupEvent(Object source,String syncMonitorUnique) {
		super(source);
		this.syncMonitorUnique=syncMonitorUnique;
	}

	public JSyncMonitorWakeupEvent(Object source,int priority,String syncMonitorUnique) {
		super(source,priority);
		this.syncMonitorUnique=syncMonitorUnique;
	}

	public String getSyncMonitorUnique() {
		return syncMonitorUnique;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
