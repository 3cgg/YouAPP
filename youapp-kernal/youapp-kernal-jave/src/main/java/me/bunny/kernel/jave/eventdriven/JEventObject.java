package me.bunny.kernel.jave.eventdriven;

import java.util.EventObject;

/**
 * basic event object
 * @author J
 */
public class JEventObject extends EventObject{

	public JEventObject(Object source) {
		super(source);
	}

}
