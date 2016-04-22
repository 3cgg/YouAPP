/**
 * 
 */
package j.jave.kernal.container.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JContainerGetListener.class)
public class JContainerGetEvent extends JAPPEvent<JContainerGetEvent> {

	private String containerUnique;
	
	public JContainerGetEvent(Object source, int priority, String unique, String containerUnique) {
		super(source, priority, unique);
		this.containerUnique=containerUnique;
	}
	
	public JContainerGetEvent(Object source,String containerUnique) {
		super(source);
		this.containerUnique=containerUnique;
	}
	
	public JContainerGetEvent(Object source, int priority,String containerUnique) {
		super(source, priority);
		this.containerUnique=containerUnique;
	}

	public String getContainerUnique() {
		return containerUnique;
	}
	
}
