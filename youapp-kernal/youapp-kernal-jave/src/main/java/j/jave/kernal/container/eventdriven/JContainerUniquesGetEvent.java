/**
 * 
 */
package j.jave.kernal.container.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JContainerUniquesGetListener.class)
public class JContainerUniquesGetEvent extends JAPPEvent<JContainerUniquesGetEvent> {
	
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
