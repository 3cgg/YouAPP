package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.eventdriven.JEventListener;

/**
 * basic listener which all listeners should extend, provides the boundary to other system.
 * all sub-class should can be called on the below method
 *<p><strong>public Object trigger(JAPPEvent event)</strong>;
 *<p>
 * @author J
 */
public interface JAPPListener extends JEventListener {


	
}
