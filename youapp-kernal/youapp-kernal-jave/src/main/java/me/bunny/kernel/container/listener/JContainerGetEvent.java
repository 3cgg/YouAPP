/**
 * 
 */
package me.bunny.kernel.container.listener;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JContainerGetListener.class)
public class JContainerGetEvent extends JYouAPPEvent<JContainerGetEvent> {

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
