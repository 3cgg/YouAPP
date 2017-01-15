/**
 * 
 */
package me.bunny.kernel.jave.async;

import java.util.List;

import me.bunny.kernel.eventdriven.servicehub.JAsyncCallback;
import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JAsyncTaskExecutingListener.class)
public class JAsyncTaskExecutingEvent extends JYouAPPEvent<JAsyncTaskExecutingEvent> {

	private Object data;
	
	private JAsyncExecutor<?> executor;
	
	
	@Override
	protected List<JAsyncCallback> getBackendAsyncCallbacks() {
		return super.getBackendAsyncCallbacks();
	}
	
	
	public JAsyncTaskExecutingEvent(Object source, int priority, String unique, Object data) {
		super(source, priority, unique);
		this.data=data;
	}
	
	public JAsyncTaskExecutingEvent(Object source,Object data) {
		super(source);
		this.data=data;
	}
	
	public JAsyncTaskExecutingEvent(Object source, int priority,Object data) {
		super(source, priority);
		this.data=data;
	}
	
	public Object getData() {
		return data;
	}


	public JAsyncExecutor<?> getExecutor() {
		return executor;
	}


	public void setExecutor(JAsyncExecutor<?> executor) {
		this.executor = executor;
	}
	
}
