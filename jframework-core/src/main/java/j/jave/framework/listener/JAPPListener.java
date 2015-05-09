package j.jave.framework.listener;

import java.util.EventListener;

/**
 * basic listener which all listeners should extend, provides the boundary to other system.
 * all sub-class should can be called on the below method
 *<p><strong>public Object trigger(JAPPEvent event)</strong>;
 *<p>
 * @author J
 */
public interface JAPPListener extends EventListener {


	
}
