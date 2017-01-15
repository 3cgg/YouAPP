/**
 * 
 */
package me.bunny.kernel.filedistribute.eventdriven;

import me.bunny.kernel._c.io.JFile;
import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * notify the file distribute system to store the file.
 * @author J
 */
@JListenerOnEvent(name=JFileDistStoreListener.class)
public class JFileDistStoreEvent extends JYouAPPEvent<JFileDistStoreEvent> {

	private JFile file;
	
	public JFileDistStoreEvent(Object source, int priority, String unique, JFile file) {
		super(source, priority, unique);
		this.file=file;
	}
	
	public JFileDistStoreEvent(Object source,JFile file) {
		super(source);
		this.file=file;
	}
	
	public JFileDistStoreEvent(Object source, int priority,JFile file) {
		super(source, priority);
		this.file=file;
	}
	
	/**
	 * @return the file
	 */
	public JFile getFile() {
		return file;
	}

}
