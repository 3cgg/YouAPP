package j.jave.framework.servicehub;

import j.jave.framework.exception.JOperationNotSupportedException;
import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.JEventExecution.Phase;
import j.jave.framework.servicehub.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.framework.support.JPriorityBlockingQueue;

import java.util.AbstractQueue;

public class JEventQueueIN extends JEventQueuePipe{


	JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
	{
		config.setName("EventQueueIN");
	}
	private final JQueueDistributeProcessor<JEventExecution> queueDistributeProcessor
	=new JQueueDistributeProcessor<JEventExecution>(new JPriorityBlockingQueue<JEventExecution>(),config);
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
				final JPersitenceTask persitenceTask=new JPersitenceExecuteEventOnListenerTask(execution);
				Runnable futureTask=persitenceTask.getRunnable();
				execution.setFutureTask(futureTask);
				execution.setPersitenceTask(persitenceTask); 
				return futureTask;
			}

			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				next().addEventExecution(execution);
			}
			
		});
	}
	
	void addAPPEvent(JAPPEvent<? > appEvent){
		JEventExecution eventInfo=new JEventExecution();
		eventInfo.setEvent(appEvent);
		eventInfo.addAsyncCallbacks(appEvent.getAttachedAsyncCallbackChain());
		eventInfo.setProcessed(false);
		eventInfo.setPhase(Phase.EVENT_CONSUME_READY);
		queueDistributeProcessor.addExecution(eventInfo);
	}

	@Override
	protected void addEventExecution(JEventExecution eventExecution) {
		throw new JOperationNotSupportedException("it is the first entrance only retrieve event source.");
		
	}

}
