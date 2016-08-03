package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestEndNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestStartNotifyEvent;
import j.jave.kernal.jave.eventdriven.JEventObject;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * basic event object. 
 * when you want to know the event how to execute, what listener process or the result the event get, you can use unique.
 * the unique can be found how to be use in the <code>JServiceEventProcessor</code>
 * @author J
 */

@SuppressWarnings("serial")
public class JYouAPPEvent<T extends JYouAPPEvent<T>> extends JEventObject implements Comparable<T>,JModel{

	public static final int NORMAL=5;

	public static final int HIGEST=1;
	
	public static final int LOWEST=9;

	/**
	 * only support 1~9.
	 */
	private final int priority;
	
	/**
	 * UNIQUE IDENTIFICATION , see {@link #JUniqueUtils}
	 */
	private final String unique;
	
	/**
	 * all asynchronize callback methods.
	 */
	private final List<JAsyncCallback> asyncCallbacks=new ArrayList<JAsyncCallback>();
	
	public static final JAsyncCallback NOTHING=new JAsyncCallback() {
		@Override
		public void callback(EventExecutionResult result,
				JEventExecution eventExecution) {
		}
	};
	
	private boolean getResultLater=false;
	
	/**
	 * @see JEventRequestStartNotifyEvent
	 * @see JEventRequestEndNotifyEvent
	 */
	private boolean track;
	
	protected List<JAsyncCallback> getBackendAsyncCallbacks() {
		return asyncCallbacks;
	}
	
	/**
	 * use to find out how many callback methods in the event.
	 * @return unmodifiable collection
	 * @see Collections#unmodifiableCollection(Collection)
	 */
	public Collection<JAsyncCallback> getAsyncCallbacks() {
		return Collections.unmodifiableCollection(asyncCallbacks);
	}

	public final T addAsyncCallbacks(
			List<JAsyncCallback> asyncCallbacks) {
		return addAsyncCallbacks(asyncCallbacks, false);
	}
	
	@SuppressWarnings("unchecked")
	public final T addAsyncCallbacks(
			List<JAsyncCallback> asyncCallbacks,boolean override) {
		JAssert.isNotNull(asyncCallbacks,"event callback is null.");
		if(override){
			this.asyncCallbacks.clear();
		}
		this.asyncCallbacks.addAll(asyncCallbacks);
		return (T) this;
	}
	
	public final T addAsyncCallback(
			JAsyncCallback asyncCallback) {
		return addAsyncCallback(asyncCallback, false);
	}
	
	@SuppressWarnings("unchecked")
	public final T addAsyncCallback(
			JAsyncCallback asyncCallback,boolean override) {
		JAssert.isNotNull(asyncCallback,"event callback is null.");
		if(asyncCallback==NOTHING){
			return (T) this; 
		}
		if(override){
			this.asyncCallbacks.clear();
		}
		this.asyncCallbacks.add(asyncCallback);
		return (T) this;
	}
	
	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * @return the unique
	 */
	public String getUnique() {
		return unique;
	}
	
	public JYouAPPEvent(Object source) {
		super(source);
		this.priority=NORMAL;
		this.unique=JUniqueUtils.unique();
	}
	
	public JYouAPPEvent(Object source,int priority){
		super(source);
		if(priority>9||priority<1){
			throw new IllegalArgumentException("priority must is 1~9, arg:"+priority);
		}
		this.priority=priority;
		this.unique=JUniqueUtils.unique();
	}
	
	public JYouAPPEvent(Object source,int priority,String unique) {
		super(source);
		if(priority>9||priority<1){
			throw new IllegalArgumentException("priority must is 1~9, arg:"+priority);
		}
		this.priority=priority;
		this.unique=unique;
	}

	@Override
	public final int compareTo(T o) {
		return this.priority-o.getPriority();
	}

	public boolean isGetResultLater() {
		return getResultLater;
	}

	@SuppressWarnings("unchecked")
	public T setGetResultLater(boolean getResultLater) {
		this.getResultLater = getResultLater;
		return (T)this;
	}

	/**
	 * @see #track
	 * @return
	 */
	public boolean isTrack() {
		return track;
	}

	/**
	 * whether to do some notification, such as {@link JEventRequestStartNotifyEvent} /
	 * {@link JEventRequestEndNotifyEvent}
	 * @param track {@link #track}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T setTrack(boolean track) {
		this.track = track;
		return (T)this;
	}
	
}
