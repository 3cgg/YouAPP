/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.coderef;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * event generated for notifying to refresh cached.
 * @author J
 */
@JListenerOnEvent(name=CodeRefCacheRefreshListener.class)
public class CodeRefCacheRefreshEvent extends JAPPEvent<CodeRefCacheRefreshEvent> {

	public CodeRefCacheRefreshEvent(Object source) {
		super(source);
	}

	public CodeRefCacheRefreshEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
