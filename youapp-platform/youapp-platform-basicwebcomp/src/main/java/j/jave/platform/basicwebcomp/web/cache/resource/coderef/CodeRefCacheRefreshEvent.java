/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.coderef;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

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
