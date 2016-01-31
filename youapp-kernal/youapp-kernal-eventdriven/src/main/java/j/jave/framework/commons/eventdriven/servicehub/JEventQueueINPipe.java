package j.jave.framework.commons.eventdriven.servicehub;

import j.jave.framework.commons.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.framework.commons.exception.JOperationNotSupportedException;
import j.jave.framework.commons.support.JPriorityBlockingQueue;
import j.jave.framework.commons.support.JQueueDistributeProcessor;
import j.jave.framework.commons.support.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;

import java.util.AbstractQueue;

/**
 * retrieve the {@link JAPPEvent} driven by the {@link JServiceEventProcessor},
 * default the event pipe is the first element in the {@link JEventQueuePipes}.
 * the pipe does not support {@link #addEventExecution(JEventExecution)}.
 * @author J
 */
public class JEventQueueINPipe extends JEventQueuePipe{


	JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
	{
		config.setName("JEventQueueINPipe");
	}
	private final JQueueDistributeProcessor<JEventExecution> queueDistributeProcessor
	=new JQueueDistributeProcessor<JEventExecution>(new JPriorityBlockingQueue<JEventExecution>(),config);
	{
		queueDistributeProcessor.setHandler(new JAbstractEventExecutionHandler() {

			@Override
			public boolean isLaterProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				return false;
			}

			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution,
					AbstractQueue<JEventExecution> executions) {
				final JPersistenceTask persitenceTask=new JPersistenceExecuteEventOnListenerTask(execution);
				return persitenceTask;
			}
			
			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				handoff(execution);
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

	@SuppressWarnings("serial")
	public static class JPersistenceExecuteEventOnListenerTask extends JPersistenceTask{
		
		public JPersistenceExecuteEventOnListenerTask(JEventExecution eventExecution){
			super(eventExecution);
		}
		
		public Object execute() {
			return JServiceHub.get().executeEventOnListener(eventExecution.getEvent());
		}
		
		@Override
		public boolean isVoid() {
			return false;
		}
		
	}
	
}
