/**
 * 
 */
package me.bunny.kernel.container.listener;

import me.bunny.kernel.container.JContainer;
import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

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
