/**
 * 
 */
package j.jave.platform.webcomp.web.cache.resource.coderef;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=CodeRefCacheRefreshListener.class)
public class CodeRefCacheRefreshEvent extends JYouAPPEvent<CodeRefCacheRefreshEvent> {

	public CodeRefCacheRefreshEvent(Object source) {
		super(source);
	}

	public CodeRefCacheRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
