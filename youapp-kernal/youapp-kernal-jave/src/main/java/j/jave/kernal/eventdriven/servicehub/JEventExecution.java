package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.json.JJSONObject;
import j.jave.kernal.jave.model.JModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * event processing context,goes through all event queue pipe.
 * @author J
 */
@SuppressWarnings("serial")
public class JEventExecution implements Comparable<JEventExecution> 
,JModel,JQueueElement,JJSONObject<Map<String,Object>> {

	/**
	 * callback chain
	 */
	private List<JAsyncCallback> asyncCallbackChain=new ArrayList<JAsyncCallback>();
	
	/**
	 * the source event.
	 */
	private JYouAPPEvent<?> event;
	
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
	private transient Runnable runnable;
	
	/**
	 * has chance to do the event again.
	 */
	private JPersistenceTask persitenceTask;
	
	/**
	 * it means the event is processed by listener.
	 */
	private boolean processed;
	
	/**
	 * what the event is located at.  the phase contains , see {@link Phase}
	 */
	private String phase;
	
	/**
	 * point to the current index in the callback chain. which callback is triggering,
	 * from zero (0~)
	 */
	private int currentCallbackIndex=-1;
	
	public static class Phase{
		
		public static final String EVENT_CONSUME_PERSITENCE="EVENT_CONSUME_PERSITENCE";
		public static final String EVENT_CONSUME_READY="EVENT_CONSUME_READY";
		public static final String EVENT_CONSUME_DONE="EVENT_CONSUME_DONE";
		
		public static final String EVENT_RESULT_GET_PERSITENCE="EVENT_RESULT_GET_PERSITENCE";
		public static final String EVENT_RESULT_GET_READY="EVENT_RESULT_GET_READY";
		public static final String EVENT_RESULT_GET_ING="EVENT_RESULT_GET_ING";
		public static final String EVENT_RESULT_GET_DONE="EVENT_RESULT_GET_DONE";
		
		public static final String EVENT_PERSITENCE_PERSITENCE="EVENT_PERSITENCE_PERSITENCE";
		public static final String EVENT_PERSITENCE_READY="EVENT_PERSITENCE_READY";
		public static final String EVENT_PERSITENCE_ING="EVENT_PERSITENCE_ING";
		public static final String EVENT_PERSITENCE_DONE="EVENT_PERSITENCE_DONE";
		
		
		
		public static final String EVENT_CALLBACK_PERSITENCE="EVENT_CALLBACK_PERSITENCE";
		public static final String EVENT_CALLBACK_READY="EVENT_CALLBACK_READY";
		public static final String EVENT_CALLBACK_ING="EVENT_CALLBACK_ING";
		public static final String EVENT_CALLBACK_DONE="EVENT_CALLBACK_DONE";
		
		public static final String EVENT_PROCESS_COMPLETED="EVENT_PROCESS_COMPLETED";
	}
	
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
	
	public JYouAPPEvent<?> getEvent() {
		return event;
	}
	public void setEvent(JYouAPPEvent<?> event) {
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
	
	/**
	 * the runnable task is from {@link #persitenceTask}
	 * @return
	 * @see JEventExecution#runnable
	 */
	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
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

	@Override
	public Map<String, Object> serializableJSONObject() {
		
		Map<String, Object> objects=new HashMap<String, Object>();
		objects.put("processed", processed);
		objects.put("event.id", event.getUnique());
		objects.put("event.type", event.getClass().getName());
		objects.put("event.priority", event.getPriority());
		objects.put("phase",phase);
		objects.put("result",result);
		objects.put("currentCallbackIndex",currentCallbackIndex);
		objects.put("asyncCallbackChainSize",asyncCallbackChain==null?0:asyncCallbackChain.size());
		return objects;
	}

	@Override
	public String desc() {
		return JJSON.get().formatJSONObject(this);
	}
	
	

}
