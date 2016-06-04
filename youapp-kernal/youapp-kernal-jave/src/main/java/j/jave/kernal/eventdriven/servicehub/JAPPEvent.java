package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestEndNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestStartNotifyEvent;
import j.jave.kernal.jave.eventdriven.JEventObject;
import j.jave.kernal.jave.model.JModel;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * basic event object. 
 * when you want to know the event how to execute, what listener process or the result the event get, you can use unique.
 * the unique can be found how to be use in the <code>JServiceEventProcessor</code>
 * @author J
 */
public class JAPPEvent<T extends JAPPEvent<T>> extends JEventObject implements Comparable<T>,JModel{

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
	 * predefined call back 
	 */
	private JAsyncCallback asyncCallback;
	
	/**
	 * the collection is only used by the framework, the subclass should not use. 
	 * the collection can be initialized while transforming in the network.
	 */
	private final List<JAsyncCallback> attachedAsyncCallbackChain=new ArrayList<JAsyncCallback>();
	
	private boolean getResultLater=false;
	
	/**
	 * @see JEventRequestStartNotifyEvent
	 * @see JEventRequestEndNotifyEvent
	 */
	private boolean track;
	
	/**
	 * the collection is only used by the framework, the subclass should not use. 
	 */
	public final List<JAsyncCallback> getAttachedAsyncCallbackChain() {
		return attachedAsyncCallbackChain;
	}

	/**
	 * the collection is only used by the framework, the subclass should not use. 
	 */
	final void addAttachedAsyncCallbacks(
			List<JAsyncCallback> attachedAsyncCallbacks) {
		this.attachedAsyncCallbackChain.addAll(attachedAsyncCallbacks);
	}
	
	/**
	 * the collection is only used by the framework, the subclass should not use. 
	 */
	final void addAttachedAsyncCallback(
			JAsyncCallback attachedAsyncCallback) {
		this.attachedAsyncCallbackChain.add(attachedAsyncCallback);
	}

	/**
	 * predefined call back 
	 */
	public void setAsyncCallback(JAsyncCallback asyncCallback) {
		this.asyncCallback = asyncCallback;
	}
	
	/**
	 * predefined call back 
	 */
	public JAsyncCallback getAsyncCallback() {
		return asyncCallback;
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
	
	public JAPPEvent(Object source) {
		super(source);
		this.priority=NORMAL;
		this.unique=JUniqueUtils.unique();
	}
	
	public JAPPEvent(Object source,int priority){
		super(source);
		if(priority>9||priority<1){
			throw new IllegalArgumentException("priority must is 1~9, arg:"+priority);
		}
		this.priority=priority;
		this.unique=JUniqueUtils.unique();
	}
	
	public JAPPEvent(Object source,int priority,String unique) {
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

	public void setGetResultLater(boolean getResultLater) {
		this.getResultLater = getResultLater;
	}

	public boolean isTrack() {
		return track;
	}

	public void setTrack(boolean track) {
		this.track = track;
	}
	
}
