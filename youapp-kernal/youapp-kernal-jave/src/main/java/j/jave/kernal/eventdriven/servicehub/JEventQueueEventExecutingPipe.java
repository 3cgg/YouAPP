package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestEndNotifyEvent;

/**
 * retrieve the {@link JAPPEvent} driven by the {@link JServiceEventProcessor},
 * default the event pipe is the first element in the {@link JEventQueuePipeChain}.
 * the pipe does not support {@link #addEventExecution(JEventExecution)}.
 * @author J
 */
public class JEventQueueEventExecutingPipe extends JEventQueuePipe{

	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueEventExecutingPipe.class.getName());
		return config;
	}
	
	@Override
	protected JAbstractEventExecutionHandler getHandler() {
		return new JAbstractEventExecutionHandler() {

			@Override
			public boolean isLaterProcess(JEventExecution execution) {
				return false;
			}

			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution) {
				final JPersistenceTask persitenceTask=new JPersistenceExecuteEventOnListenerTask(execution);
				return persitenceTask;
			}
			
			@Override
			public void postProcess(JEventExecution execution) {
			}
		};
	}
	
	void addAPPEvent(JAPPEvent<? > appEvent){
		JEventExecution eventInfo=new JEventExecution();
		eventInfo.setEvent(appEvent);
		eventInfo.addAsyncCallbacks(appEvent.getAttachedAsyncCallbackChain());
		eventInfo.setProcessed(false);
		eventInfo.setPhase(Phase.EVENT_CONSUME_READY);
		addEventExecution(eventInfo);
	}

	@SuppressWarnings("serial")
	public static class JPersistenceExecuteEventOnListenerTask extends JPersistenceTask{
		
		public JPersistenceExecuteEventOnListenerTask(JEventExecution eventExecution){
			super(eventExecution);
		}
		
		public Object execute() {
			Object object= JServiceHub.get().executeEventOnListener(eventExecution.getEvent());
			// notify the event is processed completely.
			JServiceHubDelegate.get().addDelayEvent(new JEventRequestEndNotifyEvent(this,eventExecution.getEvent()));
			return object;
		}
		
		@Override
		public boolean isVoid() {
			return false;
		}
		
	}
	
}
