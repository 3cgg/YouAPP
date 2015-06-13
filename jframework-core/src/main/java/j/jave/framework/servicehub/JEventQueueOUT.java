package j.jave.framework.servicehub;

import j.jave.framework.servicehub.JEventExecution.Phase;
import j.jave.framework.servicehub.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.framework.utils.JCollectionUtils;

import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class JEventQueueOUT extends JEventQueuePipe {
	
	JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
	{
		config.setName("EventQueueOUT");
	}
	private final JQueueDistributeProcessor<JEventExecution> queueDistributeProcessor
	=new JQueueDistributeProcessor<JEventExecution>(new LinkedBlockingQueue<JEventExecution>(),config);
	{
		queueDistributeProcessor.setHandler(new JQueueDistributeProcessor.Handler<JEventExecution>() {

			@Override
			public boolean isLaterProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				return false;
			}

			@Override
			public Runnable taskProvided(final JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				final JPersitenceEventAsyncCallbackTask eventAsyncCallbackTask=new JPersitenceEventAsyncCallbackTask(execution);
				execution.setPersitenceTask(eventAsyncCallbackTask);
				Runnable runnable=eventAsyncCallbackTask.getRunnable();
				execution.setFutureTask(runnable);
				execution.setPhase(Phase.EVENT_CALLBACK_ING);
				return runnable;
			}

			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				execution.setPhase(Phase.EVENT_CALLBACK_DONE);
				next().addEventExecution(execution);
			}
			
		});
	}
	
	protected void addEventExecution(JEventExecution eventExecution){
		//if need event callback.
		if(JCollectionUtils.hasInCollect(eventExecution.getAsyncCallbackChain())){
			eventExecution.setPhase(Phase.EVENT_CALLBACK_READY);
			queueDistributeProcessor.addExecution(eventExecution);
		}
		else{
			waitForGets.put(eventExecution.getEvent().getUnique(), eventExecution);
		}
	}
	
	/**
	 * KEY: UNIQUE VALUE. it's the value of property <code>unique</code> of {@code JAPPEvent}
	 * <p>VALUE:  the event response that means the event is done completely.
	 * <p>about the collection we can consider to store all data in the file system whose active time after executed by thread is long, 
	 * void the OutOfMemory.. 
	 */
	final ConcurrentHashMap<String, JEventExecution> waitForGets=new ConcurrentHashMap<String, JEventExecution>();

	
	Object getEventResult(String eventUnique) throws JEventExecutionException{
		if(waitForGets.containsKey(eventUnique)){
			JEventExecution eventExecution= waitForGets.get(eventUnique);
			waitForGets.remove(eventUnique);
			return eventExecution.getResult();
		}
		throw new JEventExecutionException(JEventExecutionException.EVENT_NOT_COMPLETE,
				"event is still not executed completely! please wait.");
	}
}
