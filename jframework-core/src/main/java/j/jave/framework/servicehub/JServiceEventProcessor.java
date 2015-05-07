/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.support.JPriorityBlockingQueue;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * event processor center. all event must be processes by this processor. 
 * @author J
 */
public class JServiceEventProcessor {

	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	private JServiceHub serviceHub=JServiceHub.get();
	
	/**
	 * event queue holds some events that can be sent to a certain {@link JService}.
	 */
	private final JPriorityBlockingQueue<JAPPEvent<?>> eventQueue=new JPriorityBlockingQueue<JAPPEvent<?>>();

	private static JServiceEventProcessor eventProcessor=null;
	
	private JServiceEventProcessor(){}
	
	public static JServiceEventProcessor get(){
		if(eventProcessor==null){
			synchronized (JServiceEventProcessor.class) {
				eventProcessor=new JServiceEventProcessor();
			}
		}
		return eventProcessor;
	}
	
	/**
	 * KEY: UNIQUE VALUE. it's the value of property <code>unique</code> of {@code JAPPEvent}
	 * <p>VALUE:  the future task that delegate the event by multiple threads. 
	 */
	private final static Map<String, FutureTask<Object>> eventFutureTasks=new ConcurrentHashMap<String, FutureTask<Object>>(256);
	
	Runnable findListener=new Runnable() {
		
		@Override
		public void run() {
			try{
				while(true){
					
					//sleep 1 second.
					Thread.sleep(1000);
					
					if(eventQueue.size()>0){
						
						// do with the 20 head elements in the EventQueue every 1 second.
						for(int i=0;i<20;i++){
							if(eventQueue.size()>0){
								final JAPPEvent<?> event= eventQueue.poll();
								
								FutureTask<Object> futureTask=new FutureTask<Object>(new Callable<Object>() {
									@Override
									public Object call() throws Exception {
										return serviceHub.executeEventOnListener(event);
									}
								}); 
								// put the future task in the map to make the program have a chance to get the result by the task later,
								eventFutureTasks.put(event.getUnique(), futureTask);
								// sent to thread executor.
								eventExecutor.execute(futureTask);
							}
						}
						
						
					}
				}
			}catch(Exception e){
				LOGGER.error("Event Main Thread occurs an exception by : ",e);
				// resume the thread. 
				daemon.execute(findListener);
			}
		}
	};
	
	private ExecutorService daemon=Executors.newFixedThreadPool(1);
	{
		daemon.execute(findListener);
	}
	
	private ScheduledThreadPoolExecutor eventExecutor=new ScheduledThreadPoolExecutor(10);
	
	
	/**
	 * get event result.
	 * @param eventUnique  unique property value in event object.
	 * @param wait how many time of second unit . 
	 * @return null returned if the unique not bing to a event. 
	 * @throws JEventExecutionException
	 * @throws TimeoutException
	 */
	public static Object getEventResult(String eventUnique,long wait) throws JEventExecutionException,TimeoutException{
		FutureTask<Object> futureTask=eventFutureTasks.get(eventUnique);
		if(futureTask==null){
			return null;
		}
		
		try {
			return futureTask.get(wait, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new JEventExecutionException(e);
		}
		catch ( ExecutionException e) {
			throw new JEventExecutionException(e.getCause());
		}
		catch (TimeoutException e) {
			throw e;
		}
	}
	
	/**
	 * put the event in the event queue.
	 * @param event
	 */
	public void addEvent(JAPPEvent<?> event){
		eventQueue.offer(event);
	}
	
}
