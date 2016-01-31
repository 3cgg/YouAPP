package j.jave.framework.commons.eventdriven.servicehub;

import java.util.List;

/**
 * the class is only used in the framework layer, i.e. you should not use the class to affect the {@link JAPPEvent} instance.
 * @author J
 */
public abstract class JAPPEventOperator {
	
	/**
	 * add asynchronous consist of callback
	 * @param event
	 * @param attachedAsyncCallbacks
	 */
	public static void addAttachedAsyncCallbacks(JAPPEvent<?> event,
			List<JAsyncCallback> attachedAsyncCallbacks) {
		event.addAttachedAsyncCallbacks(attachedAsyncCallbacks);
	}
	
	/**
	 * add a single asynchronous callback
	 * @param event
	 * @param attachedAsyncCallback
	 */
	public static void addAttachedAsyncCallback(JAPPEvent<?> event,
			JAsyncCallback attachedAsyncCallback) {
		event.addAttachedAsyncCallback(attachedAsyncCallback);
	}
	
	/**
	 * clear
	 * @param event
	 */
	public static void clearAttachedAsyncCallbackChain(JAPPEvent<?> event){
		event.getAttachedAsyncCallbackChain().clear();
	}
	
	
	
}
