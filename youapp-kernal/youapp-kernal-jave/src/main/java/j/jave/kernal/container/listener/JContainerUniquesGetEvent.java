/**
 * 
 */
package j.jave.kernal.container.listener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

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
