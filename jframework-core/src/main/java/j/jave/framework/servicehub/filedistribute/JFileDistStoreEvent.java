/**
 * 
 */
package j.jave.framework.servicehub.filedistribute;

import j.jave.framework.io.JFile;
import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.JListenerOnEvent;

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
