package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * event processing context.
 * @author J
 */
public class JEventExecution implements Comparable<JEventExecution> {

	/**
	 * callback chain
	 */
	private List<JAsyncCallback> asyncCallbackChain=new ArrayList<JAsyncCallback>();
	
	/**
	 * the source event.
	 */
	private JAPPEvent<?> event;
	
	/**
	 * the result, generally the result is represented as Object Array.
	 */
	private Object result;
	
	/**
	 * the asynchronous task is created according to the {@link #persitenceTask},
	 * if the {@link JPersistenceTask#isVoid()} returns false,the future {@link FutureTask} is created,else {@link Runnable} is created,for example
	 * <pre>{@code
	 * final JPersitenceTask persitenceTask=new JPersitenceExecuteEventOnListenerTask(execution);
	 * 
	 * FutureTask<Object> futureTask=new FutureTask<Object>(new Callable<Object>() {
	 *		public Object call() throws Exception {
	 *			return persitenceTask.execute();
	 *		}
	 *	});
	 *} 
	 *</pre>
	 *@see JPersistenceTask#getRunnable()
	 */
	private transient Runnable futureTask;
	
	/**
	 * has chance to do the event again.
	 */
	private JPersistenceTask persitenceTask;
	
	private boolean processed;
	
	public static class Phase{
		
		public static final String EVENT_CONSUME_PERSITENCE="EVENT_CONSUME_PERSITENCE";
		public static final String EVENT_CONSUME_READY="EVENT_CONSUME_READY";
		public static final String EVENT_CONSUME_DONE="EVENT_CONSUME_DONE";
		
		public static final String EVENT_RESULT_GET_PERSITENCE="EVENT_RESULT_GET_PERSITENCE";
		public static final String EVENT_RESULT_GET_READY="EVENT_RESULT_GET_READY";
		public static final String EVENT_RESULT_GET_ING="EVENT_RESULT_GET_ING";
		public static final String EVENT_RESULT_GET_DONE="EVENT_RESULT_GET_DONE";
		
		public static final String EVENT_CALLBACK_PERSITENCE="EVENT_CALLBACK_PERSITENCE";
		public static final String EVENT_CALLBACK_READY="EVENT_CALLBACK_READY";
		public static final String EVENT_CALLBACK_ING="EVENT_CALLBACK_ING";
		public static final String EVENT_CALLBACK_DONE="EVENT_CALLBACK_DONE";
		
		public static final String EVENT_PROCESS_COMPLETED="EVENT_PROCESS_COMPLETED";
	}
	
	/**
	 * what the event is located at.  the phase contains , see {@link Phase}
	 */
	String phase;
	
	/**
	 * point to the current index in the callback chain. which callback is triggering,
	 * from zero (0~)
	 */
	private int currentCallbackIndex=-1;
	
	public int getCurrentCallbackIndex() {
		return currentCallbackIndex;
	}

	public void setCurrentCallbackIndex(int currentCallbackIndex) {
		this.currentCallbackIndex = currentCallbackIndex;
	}

	public List<JAsyncCallback> getAsyncCallbackChain() {
		return asyncCallbackChain;
	}

	public void addAsyncCallbacks(List<JAsyncCallback> asyncCallbacks) {
		this.asyncCallbackChain.addAll(asyncCallbacks);
	}
	
	public void addAsyncCallback(JAsyncCallback  asyncCallback) {
		this.asyncCallbackChain.add(asyncCallback);
	}
	
	public JAPPEvent<?> getEvent() {
		return event;
	}
	public void setEvent(JAPPEvent<?> event) {
		this.event = event;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	public void setFutureTask(Runnable futureTask) {
		this.futureTask = futureTask;
	}
	
	public Runnable getFutureTask() {
		return futureTask;
	}
	
	@Override
	public int compareTo(JEventExecution o) {
		return this.event.getPriority()-o.getEvent().getPriority();
	}

	public JPersistenceTask getPersitenceTask() {
		return persitenceTask;
	}

	public void setPersitenceTask(JPersistenceTask persitenceTask) {
		this.persitenceTask = persitenceTask;
	}

	public String getPhase() {
		return phase;
	}

	void setPhase(String phase) {
		this.phase = phase;
	}
	
	

}
