package j.jave.framework.listener;

import j.jave.framework.utils.JUniqueUtils;

import java.util.EventObject;

/**
 * basic event object. 
 * when you want to know the event how to execute, what listener process or the result the event get, you can use unique.
 * the unique can be found how to be use in the <code>JServiceEventProcessor</code>
 * @author J
 */
public class JAPPEvent<T extends JAPPEvent<T>> extends EventObject implements Comparable<T>{

	private static final long serialVersionUID = -7815237822847470246L;

	public static int NORMAL=5;

	public static int HIGEST=1;
	
	public static int LOWEST=9;

	/**
	 * only support 1~9.
	 */
	private final int priority;
	
	/**
	 * UNIQUE IDENTIFICATION , see {@link #JUniqueUtils}
	 */
	private final String unique;
	
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

}
