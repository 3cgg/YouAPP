package j.jave.kernal.eventdriven.servicehub;

import java.util.AbstractQueue;

/**
 * the class describers a structure of a single queue on which a main thread scans , and poll a head element to a handler {@link EventExecutionHandler} 
 * to process.  generally <strong>only one main thread</strong> is going if the {@link #handler} never provide any task.
 * i.e. {@link #getTaskThreadPoolExecutor()} is called only base on the provided task ,like as not null returned from {@link EventExecutionHandler#taskProvided(Object, AbstractQueue)}
 * @author J
 *
 * @param <T>
 */
public class JEventExecutionQueueElementDistributer extends JQueueElementDistributer<JEventExecution> {
	
	public static class JQueueDistributeProcessorConfig extends JQueueElementDistributerConfig{
		
	}

	private final JEventQueuePipe eventQueuePipe;
	
	public JEventExecutionQueueElementDistributer(EventExecutionHandler handler,
			JQueueDistributeProcessorConfig config,
			JEventQueuePipe eventQueuePipe
			){
		super(handler, config);
		this.eventQueuePipe=eventQueuePipe;
	}
	
	@Override
	protected void handoff(JEventExecution element) {
		eventQueuePipe.handoff(element);
	}
	
	/**
	 * How to process element polled from the queue.
	 * any implementation should consider what need do <strong>should not block the thread</strong>.
	 * if the operation consume much time, consider setup another thread to do so.
	 * @author J
	 *
	 * @param <T>
	 */
	public interface EventExecutionHandler extends JQueueElementHandler<JEventExecution>{
		
	}
	
	
	/**
	 * any sub-class may provide JPersistenceTask that can be executed immediately in other thread or later during 
	 * getting from the persistence repository. 
	 * @author J
	 *
	 */
	public abstract static class JAbstractEventExecutionHandler implements EventExecutionHandler {
		
		@Override
		public final Runnable taskProvided(JEventExecution execution) {
			JPersistenceTask persistenceTask=persistenceTask(execution);
			if(persistenceTask==null) {
				// remove current runnable task, release resources.
				execution.setRunnable(null);
				execution.setPersitenceTask(null);
				return null;
			}
			execution.setPersitenceTask(persistenceTask); 
			Runnable runnable=persistenceTask.getRunnable();
			execution.setRunnable(runnable);
			return runnable;
		}
		
		/**
		 * providing a delayed task that need be executed later, or immediately execute the expected task.
		 * @param execution
		 * @return null means there is not later executable task .
		 */
		public abstract JPersistenceTask persistenceTask(JEventExecution execution);
		
		@Override
		public boolean isHandOff(JEventExecution execution) {
			return true;
		}
		
	}

	private class EventExecutionRunnable extends  QueueElementExecutionRunnable{
		
		public EventExecutionRunnable(JEventExecution eventExecution,EventExecutionHandler handler) {
			super(eventExecution, handler);
		}
		
	}
	
	@Override
	protected JQueueElementDistributer<JEventExecution>.QueueElementExecutionRunnable 
		getQueueElementExecutionRunnable(
			JEventExecution execution) {
		return new EventExecutionRunnable(execution, (EventExecutionHandler) getHandler());
	}
	
	
}
