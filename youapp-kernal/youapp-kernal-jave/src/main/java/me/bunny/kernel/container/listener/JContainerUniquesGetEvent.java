/**
 * 
 */
package me.bunny.kernel.container.listener;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JContainerUniquesGetListener.class)
public class JContainerUniquesGetEvent extends JYouAPPEvent<JContainerUniquesGetEvent> {
	
	public JContainerUniquesGetEvent(Object source, int priority, String unique) {
		super(source, priority, unique);
	}
	
	public JContainerUniquesGetEvent(Object source) {
		super(source);
	}
	
	public JContainerUniquesGetEvent(Object source, int priority) {
		super(source, priority);
	}
	
}
