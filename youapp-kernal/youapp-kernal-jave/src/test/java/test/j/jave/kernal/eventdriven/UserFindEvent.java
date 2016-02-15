/**
 * 
 */
package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=UserFindListener.class)
public class UserFindEvent extends JAPPEvent<UserFindEvent> {
	
	private String name;
	
	public UserFindEvent(Object source,String name) {
		super(source);
		this.name=name;
	}

	public UserFindEvent(Object source,int priority ,String name) {
		super(source,priority);
		this.name=name;
	}

	public String getName() {
		return name;
	}
}
