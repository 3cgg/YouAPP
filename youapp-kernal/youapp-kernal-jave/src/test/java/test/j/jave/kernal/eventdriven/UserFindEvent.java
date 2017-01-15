/**
 * 
 */
package test.j.jave.kernal.eventdriven;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=UserFindListener.class)
public class UserFindEvent extends JYouAPPEvent<UserFindEvent> {
	
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
