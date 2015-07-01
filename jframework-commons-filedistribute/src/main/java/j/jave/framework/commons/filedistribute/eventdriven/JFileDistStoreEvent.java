/**
 * 
 */
package j.jave.framework.commons.filedistribute.eventdriven;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;
import j.jave.framework.commons.io.JFile;

/**
 * notify the file distribute system to store the file.
 * @author J
 */
@JListenerOnEvent(name=JFileDistStoreListener.class)
public class JFileDistStoreEvent extends JAPPEvent<JFileDistStoreEvent> {

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
