/**
 * 
 */
package j.jave.kernal.container.listener;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JContainerRegisterListener.class)
public class JContainerRegisterEvent extends JYouAPPEvent<JContainerRegisterEvent> {

	private JContainer container;
	
	public JContainerRegisterEvent(Object source, int priority, String unique, JContainer container) {
		super(source, priority, unique);
		this.container=container;
	}
	
	public JContainerRegisterEvent(Object source,JContainer container) {
		super(source);
		this.container=container;
	}
	
	public JContainerRegisterEvent(Object source, int priority,JContainer container) {
		super(source, priority);
		this.container=container;
	}
	
	public JContainer getContainer() {
		return container;
	}
	
}
